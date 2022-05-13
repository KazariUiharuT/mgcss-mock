package com.acme.bnb.controlers;

import com.acme.bnb.controlers.clases.LongWrapper;
import com.acme.bnb.controlers.clases.RequestAcceptData;
import com.acme.bnb.model.Invoice;
import com.acme.bnb.services.AuthService;
import com.acme.bnb.services.InvoiceService;
import com.acme.bnb.services.RequestService;
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
@RequestMapping(path = "api/v1/request")
public class RequestController {
    
    private final AuthService authService;
    private final RequestService requestService;
    private final InvoiceService invoiceService;
    
    @PatchMapping("/{requestId}/accepted")
    @Secured("lessor")
    public void accept(@PathVariable Long requestId, @RequestBody RequestAcceptData acceptData) {
        if(!requestService.findById(requestId).getProperty().getPropietary().getId().equals(authService.getAuth().getId())) throw new AccessDeniedException("Cannot accept requests of other lessors");
        requestService.accept(requestId, acceptData);
    }
    
    @GetMapping("/{requestId}/invoice")
    @Secured("tenant")
    public Invoice getInvoice(@PathVariable Long requestId) {
        if(!requestService.findById(requestId).getTenant().getId().equals(authService.getAuth().getId())) throw new AccessDeniedException("Cannot access requests of other tenants");
        return invoiceService.getForRequest(requestId);
    }

    @PostMapping("/{requestId}/invoice")
    @Secured("tenant")
    public LongWrapper createInvoice(@PathVariable Long requestId) {
        if(!requestService.findById(requestId).getTenant().getId().equals(authService.getAuth().getId())) throw new AccessDeniedException("Cannot access requests of other tenants");
        return new LongWrapper(invoiceService.create(requestId));
    }
}
