package com.acme.bnb.controlers;

import com.acme.bnb.controlers.clases.ActorUpdateData;
import com.acme.bnb.controlers.clases.AuditorRegisterData;
import com.acme.bnb.controlers.clases.LongWrapper;
import com.acme.bnb.model.Audit;
import com.acme.bnb.model.Auditor;
import com.acme.bnb.services.AuditService;
import com.acme.bnb.services.AuditorService;
import com.acme.bnb.services.AuthService;
import java.util.List;
import lombok.Data;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Data
@RestController()
@RequestMapping(path = "api/v1/auditor")
public class AuditorController {
    
    private final AuthService authService;
    private final AuditorService auditorService;
    private final AuditService auditService;
    
    @PostMapping("")
    @Secured("admin")
    public LongWrapper register(@RequestBody AuditorRegisterData registerData) {
        return new LongWrapper(auditorService.register(registerData));
    }

    @GetMapping("")
    public List<Auditor> list() {
        return auditorService.listAuditors();
    }
    
    @GetMapping("/{auditorId}")
    @Secured({"auditor", "admin"})
    public Auditor get(@PathVariable Long auditorId) {
        return auditorService.getAuditor(auditorId);
    }

    @PutMapping("/{auditorId}")
    @Secured("auditor")
    public void update(@PathVariable Long auditorId, @RequestBody ActorUpdateData updateData) {
        if(!authService.checkAuthById(auditorId)) throw new AccessDeniedException("Cannot modify other auditors");
        auditorService.update(auditorId, updateData);
    }

    @GetMapping("/{auditorId}/audit")
    public List<Audit> listAudits(@PathVariable Long auditorId) {
        return auditService.findAuditsByAuthor(auditorId);
    }
}
