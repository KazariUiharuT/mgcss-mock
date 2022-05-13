package com.acme.bnb.repositories;

import com.acme.bnb.model.AuditAttachment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditAttachmentRepository extends CrudRepository<AuditAttachment, Long>{
    
}
