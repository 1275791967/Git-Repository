package com.irongteng.web.editor;

import java.beans.PropertyEditorSupport;

import org.springframework.util.StringUtils;

public class StringEditor extends PropertyEditorSupport {
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
            setValue(text);// 这句话是最重要的，他的目的是通过传入参数的类型来匹配相应的字符串，去掉字符串前后的空格
        }
    }

    @Override
    public String getAsText() {

        return getValue().toString();
    }
}
