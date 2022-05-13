package com.acme.bnb.controlers.clases;

import java.util.List;
import lombok.Data;

@Data
public class DashboardLessorData {
    List<PropertyWithNumber> propertiesWithAuditCount;
    List<PropertyWithNumber> propertiesWithRequestCount, propertiesWithApprovedRequestCount, propertiesWithDeniedRequestCount, propertiesWithPendingRequestCount;
}
