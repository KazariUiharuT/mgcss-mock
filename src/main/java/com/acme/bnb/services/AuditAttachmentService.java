package com.acme.bnb.services;

import com.acme.bnb.model.AuditAttachment;
import com.acme.bnb.repositories.AuditAttachmentRepository;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
@Transactional
public class AuditAttachmentService {

    private final AuditAttachmentRepository auditAttachmentRepo;

    public Optional<AuditAttachment> findById(Long id) {
        return auditAttachmentRepo.findById(id);
    }

    public void delete(AuditAttachment a) {
        auditAttachmentRepo.delete(a);
    }

    public void save(AuditAttachment a) {
        auditAttachmentRepo.save(a);
    }

}
