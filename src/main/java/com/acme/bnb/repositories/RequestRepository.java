package com.acme.bnb.repositories;

import com.acme.bnb.model.Request;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends CrudRepository<Request, Long>{

    public List<Request> findRequestsByTenantIdOrderByDateDesc(Long tenantId);

    public List<Request> findRequestsByPropertyIdOrderByDateDesc(Long propertyId);

    public List<Request> findAllByProperty_PropietaryId(Long lessorId);
    
}
