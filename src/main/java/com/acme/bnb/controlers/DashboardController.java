package com.acme.bnb.controlers;

import com.acme.bnb.controlers.clases.DashboardData;
import com.acme.bnb.controlers.clases.DashboardLessorData;
import com.acme.bnb.services.ActorService;
import com.acme.bnb.services.AdminService;
import lombok.Data;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Data
@RestController()
@RequestMapping(path = "api/v1/dashboard")
public class DashboardController {
    
    private final AdminService adminService;
    private final ActorService actorService;
    
    @GetMapping("")
    @Secured("admin")
    public DashboardData get() {
        return adminService.getDashboard();
    }
    
    @GetMapping("/lessor/{lessorId}")
    @Secured("admin")
    public DashboardLessorData getLessor(@PathVariable Long lessorId) {
        return adminService.getDashboardLessor(lessorId);
    }
    
    @PostMapping("/testql")
    @Secured("admin")
    public Object testQL(@RequestBody String ql) {
        return adminService.testql(ql);
    }
}
