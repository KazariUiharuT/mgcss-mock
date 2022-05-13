package com.acme.bnb.controlers.clases;

import java.util.List;
import lombok.Data;

@Data
public class DashboardData {
    private double avgAcceptedRequestsPerLessor, avgDeniedRequestsPerLessor;
    private double avgAcceptedRequestsPerTenant, avgDeniedRequestsPerTenant;
    private List<ActorWithNumber> lessorsWithMoreApprovedRequests,  lessorsWithMoreDeniedRequests, lessorsWithMorePendingRequests;
    private List<ActorWithNumber> tenantsWithMoreApprovedRequests,  tenantsWithMoreDeniedRequests, tenantsWithMorePendingRequests;
    private List<ActorWithNumber> lessorsWithMoreApprovedRequestsRatio, lessorsWithLessApprovedRequestsRatio;
    private List<ActorWithNumber> tenantsWithMoreApprovedRequestsRatio, tenantsWithLessApprovedRequestsRatio;
    private List<PropertyWithNumber> propertiesWithLessAudits, propertiesWithMoreAudits;
    private double avgAuditsPerProperty;
    private List<ActorWithNumber> actorWithLessSocialIdentities,  actorWithMoreSocialIdentities;
    private double avgSocialIdentitiesPerActor;
    private List<ActorWithNumber> tenantWithLessInvoices,  tenantWithMoreInvoices;
    private double avgInvoicesPerTenant;
    private double totalMoneyDueToSystem;
    private double avgRequestsPerPropertyWithAudits, avgRequestsPerPropertyWithoutAudits;
}
