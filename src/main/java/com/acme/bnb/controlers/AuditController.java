package com.acme.bnb.controlers;

import com.acme.bnb.controlers.clases.AuditUpdateData;
import com.acme.bnb.model.Audit;
import com.acme.bnb.services.AuditService;
import com.acme.bnb.services.AuthService;
import lombok.Data;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Data
@RestController()
@RequestMapping(path = "api/v1/audit")
public class AuditController {
    
    private final AuthService authService;
    private final AuditService auditService;
    
    @GetMapping("/{auditId}")
    @Secured("actor")
    public Audit get(@PathVariable Long auditId) {
        return auditService.findById(auditId);
    }
    
    @PutMapping("/{auditId}")
    @Secured("auditor")
    public void update(@PathVariable Long auditId, @RequestBody AuditUpdateData udateData) {
        if(!auditService.findById(auditId).getAuthor().getId().equals(authService.getAuth().getId())) throw new AccessDeniedException("This audit does not belong to auditor");
        auditService.update(auditId, udateData);
    }
    
    @DeleteMapping("/{auditId}")
    @Secured("auditor")
    public void delete(@PathVariable Long auditId) {
        if(!auditService.findById(auditId).getAuthor().getId().equals(authService.getAuth().getId())) throw new AccessDeniedException("This audit does not belong to auditor");
        auditService.delete(auditId);
    }

    @PatchMapping("/{auditId}/draft")
    @Secured("auditor")
    public void publish(@PathVariable Long auditId) {
        System.out.println("Listo ya esta");
        if(!auditService.findById(auditId).getAuthor().getId().equals(authService.getAuth().getId())) throw new AccessDeniedException("This audit does not belong to auditor");
        auditService.publish(auditId);
    }
}
