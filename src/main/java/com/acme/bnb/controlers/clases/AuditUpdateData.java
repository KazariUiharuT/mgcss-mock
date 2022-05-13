package com.acme.bnb.controlers.clases;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

@Data
public class AuditUpdateData implements Validable {
    private String text;
    private String[] newAttachments;
    private Long[] deletedAttachments;

    @Override
    public boolean isValid() {
        return !(
                StringUtils.isBlank(text)
        );
    }
}
