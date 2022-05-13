package com.acme.bnb.repositories;

import com.acme.bnb.model.Audit;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends CrudRepository<Audit, Long>{

    public List<Audit> findAuditsByAuthorIdOrderByDateDesc(Long auditorId);
    
    public List<Audit> findAuditsByAuthorIdAndDraftFalseOrderByDateDesc(Long auditorId);

    public List<Audit> findAuditsByPropertyIdOrderByDateDesc(Long propertyId);

    public List<Audit> findAuditsByPropertyIdAndDraftFalseOrderByDateDesc(Long propertyId);
    
}
