package com.acme.bnb.controlers.clases;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

@Data
public class SocialIdentityCreationData extends SocialIdentityUpdateData {
    private String socialNetwork;
    
    public void setSocialNetwork(String socialNetwork){
        this.socialNetwork = socialNetwork.trim();
    }

    @Override
    public boolean isValid() {
        return super.isValid() && !(
                StringUtils.isBlank(socialNetwork)
        );
    }
}
