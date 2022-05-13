package com.acme.bnb.controlers;

import com.acme.bnb.controlers.clases.ActorRegisterData;
import com.acme.bnb.controlers.clases.CommentCreationData;
import com.acme.bnb.controlers.clases.LongWrapper;
import com.acme.bnb.model.Comment;
import com.acme.bnb.model.Lessor;
import com.acme.bnb.model.Property;
import com.acme.bnb.model.Request;
import com.acme.bnb.model.datatype.CreditCard;
import com.acme.bnb.services.AuthService;
import com.acme.bnb.services.CommentService;
import com.acme.bnb.services.LessorService;
import com.acme.bnb.services.PropertyService;
import com.acme.bnb.services.RequestService;
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
@RequestMapping(path = "api/v1/lessor")
public class LessorController {
    
    private final AuthService authService;
    private final LessorService lessorService;
    private final PropertyService propertyService;
    private final RequestService requestService;
    private final CommentService commentService;
    
    @PostMapping("")
    public LongWrapper register(@RequestBody ActorRegisterData registerData) {
        return new LongWrapper(lessorService.register(registerData));
    }
    
    @GetMapping("")
    @Secured("admin")
    public List<Lessor> list() {
        return lessorService.findAll();
    }
    
    @GetMapping("/{lessorId}/credit-card")
    public CreditCard getCreditCard(@PathVariable Long lessorId) {
        CreditCard creditCard = lessorService.getLessor(lessorId).getCreditCard();
        if(creditCard != null) creditCard.hide();
        return creditCard;
    }

    @PatchMapping("/{lessorId}/credit-card")
    @Secured("lessor")
    public void updateCreditCard(@PathVariable Long lessorId, @RequestBody CreditCard creditCard) {
        if(!authService.checkAuthById(lessorId)) throw new AccessDeniedException("Cannot modify other users");
        lessorService.updateCreditCard(lessorId, creditCard);
    }

    @GetMapping("/{lessorId}/property")
    public List<Property> listProperties(@PathVariable Long lessorId) {
        lessorService.getLessor(lessorId);
        return propertyService.findPropertiesByPropietary(lessorId);
    }

    @GetMapping("/{lessorId}/request")
    public List<Request> listRequests(@PathVariable Long lessorId) {
        if(!authService.checkAuthById(lessorId)) throw new AccessDeniedException("Cannot access other lessors requests");
        return requestService.findRequestsByPropietary(lessorId);
    }

    @GetMapping("/{lessorId}/comment")
    public List<Comment> listComments(@PathVariable Long lessorId) {
        Lessor lessor = lessorService.getLessor(lessorId);
        return commentService.findCommentsByLessor(lessor);
    }

    @PostMapping("/{lessorId}/comment")
    @Secured({"lessor", "tenant"})
    public LongWrapper createComment(@PathVariable Long lessorId, @RequestBody CommentCreationData comment) {
        Lessor lessor = lessorService.getLessor(lessorId);
        return new LongWrapper(commentService.create(lessor.getCommentable(), comment));
    }
}
