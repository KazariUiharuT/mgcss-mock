package com.acme.bnb.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Data
@Service
public class ConstantsService {
    
    private final String TOKEN_PREFIX = "Bearer ";
    private final String HEADER_STRING = "Authorization";
    private final String B0VE_BLOB_SERVICE_ENDPOINT = "https://blobcontainer.b0ve.com/";
    private final int MAX_PICTURES_PER_PROPERTY = 5;
    private final long EXPIRATION_TIME;
    private final String APP_SECRET;
    private final String B0VE_BLOB_SERVICE_API_KEY;
    private final Resource invoiceTemplate;
    
    public ConstantsService(
            @Value(value = "classpath:installation-constants.properties") Resource properties,
            @Value(value = "classpath:invoice-template.pdf") Resource invoiceTemplate
    ){
        this.invoiceTemplate = invoiceTemplate;
        try ( InputStream input = properties.getInputStream()) {
            Properties prop = new Properties();
            prop.load(input);

            EXPIRATION_TIME = Long.parseLong(prop.getProperty("session.expiration-time"));
            APP_SECRET = prop.getProperty("security.app-secret");
            B0VE_BLOB_SERVICE_API_KEY = prop.getProperty("b0ve-blob-service.api-key");
        } catch (IOException ex) {
            throw new IllegalStateException("Could not read installation constants config file: "+ex.getMessage());
        }
    }
}
