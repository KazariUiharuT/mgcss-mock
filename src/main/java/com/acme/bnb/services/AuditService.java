package com.acme.bnb.services;

import com.acme.bnb.controlers.clases.AuditUpdateData;
import com.acme.bnb.exceptions.ConflictException;
import com.acme.bnb.exceptions.InvalidParametersException;
import com.acme.bnb.model.Audit;
import com.acme.bnb.model.AuditAttachment;
import com.acme.bnb.model.Auditor;
import com.acme.bnb.model.Property;
import com.acme.bnb.repositories.AuditRepository;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
@Transactional
public class AuditService {

    private final AuditRepository auditRepo;
    private final AuthService authService;
    private final AuditAttachmentService auditAttachmentService;
    private final PropertyService propertyService;
    private final ActorService actorService;
    private final B0vEBlobService b0vEBlobService;

    public List<Audit> findAuditsByAuthor(Long auditorId) {
        if(authService.checkAuthById(auditorId))
            return auditRepo.findAuditsByAuthorIdOrderByDateDesc(auditorId);
        else
            return auditRepo.findAuditsByAuthorIdAndDraftFalseOrderByDateDesc(auditorId);
    }

    public List<Audit> findAuditsByProperty(Long propertyId) {
        return auditRepo.findAuditsByPropertyIdAndDraftFalseOrderByDateDesc(propertyId);
    }

    public Long create(Long propertyId) {
        Auditor author = (Auditor) authService.getAuth();
        if (findAuditsByAuthor(author.getId()).stream().anyMatch(a -> propertyId.equals(a.getProperty().getId()))) {
            throw new ConflictException("You already have an audit for this property");
        }
        Property property = propertyService.getProperty(propertyId);

        Audit audit = new Audit();
        audit.setText("");
        audit.setAuthor(author);
        audit.setProperty(property);
        audit.setDraft(true);

        auditRepo.save(audit);
        return audit.getId();
    }

    public Audit findById(Long auditId) {
        return auditRepo.findById(auditId).orElseThrow(() -> new InvalidParametersException("Audit does not exist"));
    }

    public void publish(Long auditId) {
        Audit audit = findById(auditId);
        if (!audit.isDraft()) {
            throw new ConflictException("This audit is not a draft");
        }
        audit.setDraft(false);
        propertyService.incrementAudits(audit.getProperty());
        auditRepo.save(audit);
    }

    public void update(Long auditId, AuditUpdateData updateData) {
        if (!updateData.isValid()) {
            throw new InvalidParametersException("Invalid parameters");
        }
        Audit audit = findById(auditId);
        if (!audit.isDraft()) {
            throw new ConflictException("This audit is not a draft");
        }

        List<Long> toDelete = List.of(updateData.getDeletedAttachments());
        List<String> toCreate = List.of(updateData.getNewAttachments());

        audit.setText(updateData.getText());
        toDelete.stream().map(id -> auditAttachmentService.findById(id).orElseThrow(() -> new EntityNotFoundException("Attachment not found"))).forEach(a -> {
            if (!a.getAudit().getId().equals(audit.getId())) {
                throw new ConflictException("Can not delete attachments from another audit");
            }
            auditAttachmentService.delete(a);
        });
        toCreate.stream().map(b -> new AuditAttachment(b0vEBlobService.storePic(b), audit)).forEach(a -> auditAttachmentService.save(a) );

        auditRepo.save(audit);
    }

    public void delete(Long auditId) {
        auditRepo.deleteById(auditId);
    }

}
