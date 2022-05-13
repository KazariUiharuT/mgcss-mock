package com.acme.bnb.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiError {

    private static final List<String> ERRORS = List.of(
            "Cannot modify other users",
            "This audit does not belong to auditor",
            "Cannot access other lessors requests",
            "Cannot modify properties of other lessors",
            "Cannot delete properties of other lessors",
            "Cannot access requests of other lessors",
            "Cannot accept requests of other lessors",
            "Cannot access requests of other tenants",
            "This identity does not belong to user",
            "Auth not found",
            "User not found",
            "Empty parameters are not allowed",
            "Phone is not valid",
            "Lessor not found",
            "Could not upload image to blob service",
            "Admin not found",
            "You already have an audit for this property",
            "Audit does not exist",
            "This audit is not a draft",
            "Invalid parameters",
            "Attachment not found",
            "Can not delete attachments from another audit",
            "Email already in use",
            "Auditor not found",
            "Cannot access this auditor",
            "Comment data is not valid",
            "Cannot post a comment on another lessor",
            "Cannot post a comment on another tenant",
            "Cannot post a comment on a tennant that has not requested any of your properties",
            "Cannot post a comment on a lessor that you have not requested any properties of",
            "Could not read installation constants config file",
            "This request does not have an invoice",
            "Invalid creditcard",
            "Property Attribute not found",
            "Can not delete system default attribute",
            "Property not found",
            "Invalid request parameters",
            "Exceded maximun number of pictures per property",
            "Missing required parameters",
            "Check out date cannot be sooner than check in",
            "Check in and check out cannot be the same day",
            "This request is not pending",
            "Request not found",
            "Social entity not found",
            "SysConfig parameter not found",
            "Tenant not found"
    );
    
    @JsonIgnore
    private HttpStatus status;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    
    private String message;
    
    private int code;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = ex.getLocalizedMessage();
        this.code = ERRORS.indexOf(this.message);
    }
}
