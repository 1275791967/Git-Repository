package com.irongteng.example;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Section implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Map<String, String> content;
    private String key;
    private String value;
    private String commonts;

    public Map<String, String> getContent() {
        return content;
    }

    public void setContent(Map<String, String> content) {
        this.content = content;
    }

    public String getCommonts() {
        return commonts;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setCommonts(String commonts) {
        this.commonts = commonts;
    }
    

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getKey())
            .toHashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Section == false) return false;
        if(this == obj) return true;
        Section other = (Section)obj;
        return new EqualsBuilder()
            .append(getKey(),other.getKey())
            .isEquals();
    }
}