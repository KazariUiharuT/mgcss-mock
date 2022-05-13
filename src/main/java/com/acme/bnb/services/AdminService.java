package com.acme.bnb.services;

import com.acme.bnb.controlers.clases.ActorUpdateData;
import com.acme.bnb.controlers.clases.ActorWithNumber;
import com.acme.bnb.controlers.clases.DashboardData;
import com.acme.bnb.controlers.clases.DashboardLessorData;
import com.acme.bnb.controlers.clases.PropertyWithNumber;
import com.acme.bnb.exceptions.InvalidParametersException;
import com.acme.bnb.model.Actor;
import com.acme.bnb.model.Administrator;
import com.acme.bnb.model.Property;
import com.acme.bnb.repositories.AdministratorRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
@Transactional
public class AdminService {

    private final AdministratorRepository adminRepo;
    private final ActorService actorService;
    private final B0vEBlobService blobService;
    
    @PersistenceContext
    private EntityManager em;

    public Optional<Administrator> findById(Long id) {
        return adminRepo.findById(id);
    }
    
    public Administrator getAdmin(Long id){
        return adminRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Admin not found"));
    }

    public void update(Long id, ActorUpdateData updateData) {
        if(!updateData.isValid()) throw new InvalidParametersException("Empty parameters are not allowed");
        if(updateData.getPhone() != null && !updateData.getPhone().isValid()) throw new InvalidParametersException("Phone is not valid");
        
        Administrator admin = getAdmin(id);
        admin.setName(updateData.getName());
        admin.setSurname(updateData.getSurname());
        admin.setPhone(updateData.getPhone());
        if(updateData.getPicture() != null){
            blobService.storeBlob(updateData.getPicture(), true, true);
        }
    }

    public DashboardData getDashboard() {
        DashboardData dashboard = new DashboardData();
        
        Long numberOfLessors = (Long) em.createQuery("SELECT count(l) FROM Lessor l").getSingleResult(),
            numberOfTenants = (Long) em.createQuery("SELECT count(t) FROM Tenant t").getSingleResult(),
            numberOfAcceptedRequests = (Long) em.createQuery("SELECT count(r) FROM Request r WHERE r.status = 1").getSingleResult(),
            numberOfDeniedRequests = (Long) em.createQuery("SELECT count(r) FROM Request r WHERE r.status = 2").getSingleResult();
        if(numberOfLessors != 0) dashboard.setAvgAcceptedRequestsPerLessor(numberOfAcceptedRequests/(double)numberOfLessors);
        if(numberOfTenants != 0) dashboard.setAvgAcceptedRequestsPerTenant(numberOfAcceptedRequests/(double)numberOfTenants);
        if(numberOfLessors != 0) dashboard.setAvgDeniedRequestsPerLessor(numberOfDeniedRequests/(double)numberOfLessors);
        if(numberOfTenants != 0) dashboard.setAvgDeniedRequestsPerTenant(numberOfDeniedRequests/(double)numberOfTenants);
        
        dashboard.setLessorsWithMoreApprovedRequests(getTop3(em.createQuery("SELECT NEW com.acme.bnb.controlers.clases.ActorWithNumber(l, count(r)) FROM Lessor l LEFT JOIN l.properties p LEFT JOIN p.requests r ON r.property = p AND r.status = 1 GROUP BY l ORDER BY count(r) DESC", ActorWithNumber.class)));
        dashboard.setLessorsWithMoreDeniedRequests  (getTop3(em.createQuery("SELECT NEW com.acme.bnb.controlers.clases.ActorWithNumber(l, count(r)) FROM Lessor l LEFT JOIN l.properties p LEFT JOIN p.requests r ON r.property = p AND r.status = 2 GROUP BY l ORDER BY count(r) DESC", ActorWithNumber.class)));
        dashboard.setLessorsWithMorePendingRequests (getTop3(em.createQuery("SELECT NEW com.acme.bnb.controlers.clases.ActorWithNumber(l, count(r)) FROM Lessor l LEFT JOIN l.properties p LEFT JOIN p.requests r ON r.property = p AND r.status = 0 GROUP BY l ORDER BY count(r) DESC", ActorWithNumber.class)));
        dashboard.setTenantsWithMoreApprovedRequests(getTop3(em.createQuery("SELECT NEW com.acme.bnb.controlers.clases.ActorWithNumber(t, count(r)) FROM Tenant t LEFT JOIN t.requests r ON r.tenant= t AND r.status = 1 GROUP BY t ORDER BY count(r) DESC", ActorWithNumber.class)));
        dashboard.setTenantsWithMoreDeniedRequests  (getTop3(em.createQuery("SELECT NEW com.acme.bnb.controlers.clases.ActorWithNumber(t, count(r)) FROM Tenant t LEFT JOIN t.requests r ON r.tenant= t AND r.status = 2 GROUP BY t ORDER BY count(r) DESC", ActorWithNumber.class)));
        dashboard.setTenantsWithMorePendingRequests (getTop3(em.createQuery("SELECT NEW com.acme.bnb.controlers.clases.ActorWithNumber(t, count(r)) FROM Tenant t LEFT JOIN t.requests r ON r.tenant= t AND r.status = 0 GROUP BY t ORDER BY count(r) DESC", ActorWithNumber.class)));
        
        dashboard.setTenantsWithMoreApprovedRequestsRatio(getTop3actors(em.createQuery("SELECT t, COALESCE( ((SELECT COUNT(r) FROM Request r WHERE r.tenant = t AND r.status = 1)*100.0) / (SELECT COUNT(r) FROM Request r WHERE r.tenant = t), 0) as ratio FROM Tenant t ORDER BY ratio DESC", Tuple.class)));
        dashboard.setTenantsWithLessApprovedRequestsRatio(getTop3actors(em.createQuery("SELECT t, COALESCE( ((SELECT COUNT(r) FROM Request r WHERE r.tenant = t AND r.status = 1)*100.0) / (SELECT COUNT(r) FROM Request r WHERE r.tenant = t), 0) as ratio FROM Tenant t ORDER BY ratio ASC", Tuple.class)));
        dashboard.setLessorsWithMoreApprovedRequestsRatio(getTop3actors(em.createQuery("SELECT l, COALESCE( ((SELECT COUNT(r) FROM Property p JOIN p.requests r WHERE p.propietary = l AND r.status = 1)*100.0) / (SELECT COUNT(r) FROM Property p JOIN p.requests r WHERE p.propietary = l), 0) as ratio FROM Lessor l ORDER BY ratio DESC", Tuple.class)));
        dashboard.setLessorsWithLessApprovedRequestsRatio(getTop3actors(em.createQuery("SELECT l, COALESCE( ((SELECT COUNT(r) FROM Property p JOIN p.requests r WHERE p.propietary = l AND r.status = 1)*100.0) / (SELECT COUNT(r) FROM Property p JOIN p.requests r WHERE p.propietary = l), 0) as ratio FROM Lessor l ORDER BY ratio ASC", Tuple.class)));
        
        dashboard.setPropertiesWithMoreAudits(getTop3properties(em.createQuery("SELECT p, SIZE(p.audits)*1.0 FROM Property p ORDER BY SIZE(p.audits) DESC", Tuple.class)));
        dashboard.setPropertiesWithLessAudits(getTop3properties(em.createQuery("SELECT p, SIZE(p.audits)*1.0 FROM Property p ORDER BY SIZE(p.audits) ASC", Tuple.class)));
        dashboard.setAvgAuditsPerProperty(em.createQuery("SELECT CAST(AVG(SIZE(p.audits)) as double) FROM Property p", Double.class).getSingleResult());
        
        dashboard.setActorWithMoreSocialIdentities(getTop3actors(em.createQuery("SELECT a, SIZE(a.socialIdentities)*1.0 FROM Actor a ORDER BY SIZE(a.socialIdentities) DESC", Tuple.class)));
        dashboard.setActorWithLessSocialIdentities(getTop3actors(em.createQuery("SELECT a, SIZE(a.socialIdentities)*1.0 FROM Actor a ORDER BY SIZE(a.socialIdentities) ASC", Tuple.class)));
        dashboard.setAvgSocialIdentitiesPerActor(em.createQuery("SELECT CAST(AVG(SIZE(a.socialIdentities)) as double) FROM Actor a", Double.class).getSingleResult());
        
        dashboard.setTenantWithMoreInvoices(getTop3actors(em.createQuery("SELECT t, COUNT(r)*1.0 FROM Tenant t LEFT JOIN t.requests r ON r.tenant = t AND r.invoice IS NOT EMPTY GROUP BY t ORDER BY COUNT(r) DESC", Tuple.class)));
        dashboard.setTenantWithLessInvoices(getTop3actors(em.createQuery("SELECT t, COUNT(r)*1.0 FROM Tenant t LEFT JOIN t.requests r ON r.tenant = t AND r.invoice IS NOT EMPTY GROUP BY t ORDER BY COUNT(r) ASC", Tuple.class)));
        Long numberOfInvoices = (Long) em.createQuery("SELECT COUNT(i) FROM Invoice i").getSingleResult();
        dashboard.setAvgInvoicesPerTenant(numberOfInvoices/(double)numberOfTenants);
        
        dashboard.setTotalMoneyDueToSystem(em.createQuery("SELECT CAST(COALESCE(SUM(i.ammount), 0) as double) FROM Invoice i", Double.class).getSingleResult());
        
        dashboard.setAvgRequestsPerPropertyWithAudits(em.createQuery("SELECT SUM(SIZE(p.requests))/(COUNT(p)*1.0) FROM Property p WHERE p.audits IS NOT EMPTY", Double.class).getSingleResult());
        dashboard.setAvgRequestsPerPropertyWithoutAudits(em.createQuery("SELECT SUM(SIZE(p.requests))/(COUNT(p)*1.0) FROM Property p WHERE p.audits IS EMPTY", Double.class).getSingleResult());
        
        return dashboard;
    }

    public DashboardLessorData getDashboardLessor(Long lessorId) {
        DashboardLessorData dashboard = new DashboardLessorData();
        TypedQuery<PropertyWithNumber> q;
        
        q = em.createQuery("SELECT NEW com.acme.bnb.controlers.clases.PropertyWithNumber(p, SIZE(p.audits)) FROM Property p WHERE p.propietary.id = ?1 ORDER BY SIZE(p.audits) DESC", PropertyWithNumber.class);
        q.setParameter(1, lessorId);
        dashboard.setPropertiesWithAuditCount(q.getResultList());
        
        q = em.createQuery("SELECT NEW com.acme.bnb.controlers.clases.PropertyWithNumber(p, SIZE(p.requests)) FROM Property p WHERE p.propietary.id = ?1 ORDER BY SIZE(p.requests) DESC", PropertyWithNumber.class);
        q.setParameter(1, lessorId);
        dashboard.setPropertiesWithRequestCount(q.getResultList());
        
        q = em.createQuery("SELECT NEW com.acme.bnb.controlers.clases.PropertyWithNumber(p, COUNT(r)) FROM Property p LEFT JOIN p.requests r ON r.property = p AND r.status = 1 WHERE p.propietary.id = ?1 GROUP BY p ORDER BY COUNT(r) DESC", PropertyWithNumber.class);
        q.setParameter(1, lessorId);
        dashboard.setPropertiesWithApprovedRequestCount(q.getResultList());
        
        q = em.createQuery("SELECT NEW com.acme.bnb.controlers.clases.PropertyWithNumber(p, COUNT(r)) FROM Property p LEFT JOIN p.requests r ON r.property = p AND r.status = 2 WHERE p.propietary.id = ?1 GROUP BY p ORDER BY COUNT(r) DESC", PropertyWithNumber.class);
        q.setParameter(1, lessorId);
        dashboard.setPropertiesWithDeniedRequestCount(q.getResultList());
        
        q = em.createQuery("SELECT NEW com.acme.bnb.controlers.clases.PropertyWithNumber(p, COUNT(r)) FROM Property p LEFT JOIN p.requests r ON r.property = p AND r.status = 0 WHERE p.propietary.id = ?1 GROUP BY p ORDER BY COUNT(r) DESC", PropertyWithNumber.class);
        q.setParameter(1, lessorId);
        dashboard.setPropertiesWithPendingRequestCount(q.getResultList());
        
        return dashboard;
    }
    
    public Object testql(String ql){
        return em.createQuery(ql).getResultStream().collect(Collectors.toList());
    }
    
    private <T> List<T> getTop3(TypedQuery<T> q){
        q.setMaxResults(3);
        return q.getResultList();
    }
    private List getTop3(Query q){
        q.setMaxResults(3);
        return q.getResultList();
    }
    
    private List<ActorWithNumber> getTop3actors(TypedQuery<Tuple> q){
        q.setMaxResults(3);
        return q.getResultList().stream().map(t -> new ActorWithNumber(t.get(0, Actor.class), t.get(1, Double.class))).collect(Collectors.toList());
    }
    
    private List<PropertyWithNumber> getTop3properties(TypedQuery<Tuple> q){
        q.setMaxResults(3);
        return q.getResultList().stream().map(t -> new PropertyWithNumber(t.get(0, Property.class), t.get(1, Double.class))).collect(Collectors.toList());
    }
}
