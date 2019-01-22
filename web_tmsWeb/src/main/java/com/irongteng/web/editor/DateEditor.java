package com.irongteng.web.editor;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.util.StringUtils;

public class DateEditor extends PropertyEditorSupport {
    
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || text.equals(""))
            text = "";
        if (text != null && text.trim().equals(""))
            text = "";
        text = text.trim();
        if (!StringUtils.hasText(text)) {
            setValue(null);
        } else {
            try {
                setValue(DateUtils.parseDate(text, new String[] { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd" }));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public String getAsText() {

        return getValue().toString();
    }
}
