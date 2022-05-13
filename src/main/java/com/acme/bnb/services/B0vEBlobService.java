package com.acme.bnb.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class B0vEBlobService {

    private final ConstantsService constantsService;

    public String storePic(String blob){
        return storeBlob(blob, true, true);
    }
    public String storeDoc(String blob){
        return storeBlob(blob, false, false);
    }
    
    public String storeBlob(String blob, boolean isImage, boolean isSquare) {
        URL url;
        try {
            url = new URL(constantsService.getB0VE_BLOB_SERVICE_ENDPOINT()+"api.php");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty ("Authorization", "Basic "+Base64.getEncoder().encodeToString((constantsService.getB0VE_BLOB_SERVICE_API_KEY()+":").getBytes()));
            con.setDoOutput(true);

            ObjectMapper mapper = new ObjectMapper();
            ObjectNode request = mapper.createObjectNode();
            request.put("action", "store");
            request.put("blob", blob);
            if (isImage) {
                request.put("compressImage", true);
                request.put("jpgCompression", true);
                if (isSquare) {
                    request.put("resize", true);
                    request.put("maxHeight", 512);
                    request.put("maxWidth", 512);
                }
            }
            String jsonInputString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);

            try ( OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try ( BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                
                JsonNode responseJson = mapper.readTree(response.toString());
                if(responseJson.get("status").asText().equals("success")){
                    String id = responseJson.get("msg").asText();
                    return constantsService.getB0VE_BLOB_SERVICE_ENDPOINT()+"deliver.php?q="+id;
                }
            }

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return null;
    }
}
