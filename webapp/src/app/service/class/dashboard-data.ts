import { ActorWithNumber } from "./actor-with-number";
import { PropertyWithNumber } from "./property-with-number";

export class DashboardData {
    avgAcceptedRequestsPerLessor: number = -1;
    avgDeniedRequestsPerLessor: number = -1;
    avgAcceptedRequestsPerTenant: number = -1;
    avgDeniedRequestsPerTenant: number = -1;
    lessorsWithMoreApprovedRequests: ActorWithNumber[] = [];
    lessorsWithMoreDeniedRequests: ActorWithNumber[] = [];
    lessorsWithMorePendingRequests: ActorWithNumber[] = [];
    tenantsWithMoreApprovedRequests: ActorWithNumber[] = [];
    tenantsWithMoreDeniedRequests: ActorWithNumber[] = [];
    tenantsWithMorePendingRequests: ActorWithNumber[] = [];
    lessorsWithMoreApprovedRequestsRatio: ActorWithNumber[] = [];
    lessorsWithLessApprovedRequestsRatio: ActorWithNumber[] = [];
    tenantsWithMoreApprovedRequestsRatio: ActorWithNumber[] = [];
    tenantsWithLessApprovedRequestsRatio: ActorWithNumber[] = [];
    propertiesWithLessAudits: PropertyWithNumber[] = [];
    propertiesWithMoreAudits: PropertyWithNumber[] = [];
    avgAuditsPerProperty: number = -1;
    actorWithLessSocialIdentities: ActorWithNumber[] = [];
    actorWithMoreSocialIdentities: ActorWithNumber[] = [];
    avgSocialIdentitiesPerActor: number = -1;
    tenantWithLessInvoices: ActorWithNumber[] = [];
    tenantWithMoreInvoices: ActorWithNumber[] = [];
    avgInvoicesPerTenant: number = -1;
    totalMoneyDueToSystem: number = -1;
    avgRequestsPerPropertyWithAudits: number = -1;
    avgRequestsPerPropertyWithoutAudits: number = -1;
}
