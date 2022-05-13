package com.acme.bnb.controlers.clases;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

@Data
public class SocialIdentityUpdateData implements Validable {
    private String nick;
    private String url;
    
    public void setNick(String nick){
        this.nick = nick.trim();
    }
    
    public void setUrl(String url){
        this.url = url.trim();
    }

    @Override
    public boolean isValid() {
        return !(
                StringUtils.isBlank(nick) ||
                StringUtils.isBlank(url)
        );
    }
}
