package com.acme.bnb.controlers.clases;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

@Data
public class CommentCreationData implements Validable {
    private String title;
    private String text;
    private int stars;

    public void setTitle(String title) {
        this.title = title.trim();
    }

    public void setText(String text) {
        this.text = text.trim();
    }

    @Override
    public boolean isValid() {
        return !(
                StringUtils.isBlank(title) ||
                StringUtils.isBlank(text) ||
                stars < 0 || stars > 5
        );
    }
}
