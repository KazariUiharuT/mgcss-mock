package com.acme.bnb.controlers;

import com.acme.bnb.controlers.clases.ActorRegisterData;
import com.acme.bnb.controlers.clases.BooleanWrapper;
import com.acme.bnb.controlers.clases.CommentCreationData;
import com.acme.bnb.controlers.clases.LongWrapper;
import com.acme.bnb.model.Comment;
import com.acme.bnb.model.Request;
import com.acme.bnb.model.Tenant;
import com.acme.bnb.model.datatype.CreditCard;
import com.acme.bnb.services.AuthService;
import com.acme.bnb.services.CommentService;
import com.acme.bnb.services.RequestService;
import com.acme.bnb.services.TenantService;
import java.util.List;
import lombok.Data;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Data
@RestController()
@RequestMapping(path = "api/v1/tenant")
public class TenantController {
    
    private final AuthService authService;
    private final TenantService tenantService;
    private final RequestService requestService;
    private final CommentService commentService;
    
    @PostMapping("")
    public LongWrapper register(@RequestBody ActorRegisterData registerData) {
        return new LongWrapper(tenantService.register(registerData));
    }
    
    @GetMapping("/{tenantId}/credit-card")
    public CreditCard getCreditCard(@PathVariable Long tenantId) {
        CreditCard creditCard = tenantService.getTenant(tenantId).getCreditCard();
        if(creditCard != null) creditCard.hide();
        return creditCard;
    }
    
    @GetMapping("/{tenantId}/smoker")
    public BooleanWrapper getSmoker(@PathVariable Long tenantId) {
        Boolean smoker = tenantService.getTenant(tenantId).getSmoker();
        return smoker == null ? null : new BooleanWrapper(smoker);
    }

    @PatchMapping("/{tenantId}/credit-card")
    @Secured("tenant")
    public void updateCreditCard(@PathVariable Long tenantId, @RequestBody CreditCard creditCard) {
        if(!authService.checkAuthById(tenantId)) throw new AccessDeniedException("Cannot modify other users");
        tenantService.updateCreditCard(tenantId, creditCard);
    }

    @PatchMapping("/{tenantId}/smoker")
    @Secured("tenant")
    public void updateSmoker(@PathVariable Long tenantId, @RequestBody boolean smoker) {
        if(!authService.checkAuthById(tenantId)) throw new AccessDeniedException("Cannot modify other users");
        tenantService.updateSmoker(tenantId, smoker);
    }

    @GetMapping("/{tenantId}/request")
    public List<Request> listRequests(@PathVariable Long tenantId) {
        tenantService.getTenant(tenantId);
        return requestService.findRequestsByTenant(tenantId);
    }

    @GetMapping("/{tenantId}/comment")
    public List<Comment> listComments(@PathVariable Long tenantId) {
        Tenant tenant = tenantService.getTenant(tenantId);
        return commentService.findCommentsByTenant(tenant);
    }

    @PostMapping("/{tenantId}/comment")
    @Secured({"lessor", "tenant"})
    public LongWrapper createComment(@PathVariable Long tenantId, @RequestBody CommentCreationData comment) {
        Tenant tenant = tenantService.getTenant(tenantId);
        return new LongWrapper(commentService.create(tenant.getCommentable(), comment));
    }
}
