package com.acme.bnb.controlers;

import com.acme.bnb.controlers.clases.ActorUpdateData;
import com.acme.bnb.model.Administrator;
import com.acme.bnb.services.AdminService;
import com.acme.bnb.services.AuthService;
import lombok.Data;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Data
@RestController()
@RequestMapping(path = "api/v1/admin")
public class AdminController {
    
    private final AuthService authService;
    private final AdminService adminService;
    
    @GetMapping("/{adminId}")
    @Secured("admin")
    public Administrator get(@PathVariable Long adminId) {
        return adminService.getAdmin(adminId);
    }
    
    @PutMapping("/{adminId}")
    @Secured("admin")
    public void update(@PathVariable Long adminId, @RequestBody ActorUpdateData updateData) {
        if(!authService.checkAuthById(adminId)) throw new AccessDeniedException("Cannot modify other users");
        adminService.update(adminId, updateData);
    }
}
