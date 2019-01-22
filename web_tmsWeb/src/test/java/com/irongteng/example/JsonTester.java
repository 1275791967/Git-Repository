package com.irongteng.example;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonTester {
    public static <T> T getValueMapper(String jsonString, Class<T> cls) {
        T t = null;
        try {
            //Gson gson = new Gson();
            ObjectMapper json = new ObjectMapper();
            t = json.readValue(jsonString, cls);
        } catch (Exception e) {
        }
        return t;
    }
    
    public static void main(String[] args) {
        
        String jsonString = "{valueView:{type:\"numeric\",step:0.5}}";
        //jsonString = null;
        ValueMapper totalMap = getValueMapper(jsonString,ValueMapper.class);
        System.out.println("viewtype:" + totalMap.getValueView().getType());
        System.out.println("viewtype:" + totalMap.getValueMap());
    }
}

class ValueMapper {
    
    private ValueView valueView;
    
    private Map<String, String> valueMap;
    
    public ValueView getValueView() {
        return valueView;
    }
    public void setValueView(ValueView valueView) {
        this.valueView = valueView;
    }
    
    public Map<String, String> getValueMap() {
        return valueMap;
    }
    public void setValueMap(Map<String, String> valueMap) {
        this.valueMap = valueMap;
    }
}

class ValueView {
    
    private String type;
    private Double step;
    private String status;
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Double getStep() {
        return step;
    }
    public void setStep(Double step) {
        this.step = step;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}