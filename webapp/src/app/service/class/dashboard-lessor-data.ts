import { PropertyWithNumber } from "./property-with-number";

export class DashboardLessorData {
    propertiesWithAuditCount: PropertyWithNumber[] = [];
    propertiesWithRequestCount: PropertyWithNumber[] = [];
    propertiesWithApprovedRequestCount: PropertyWithNumber[] = [];
    propertiesWithDeniedRequestCount: PropertyWithNumber[] = [];
    propertiesWithPendingRequestCount: PropertyWithNumber[] = [];
}
