package com.irongteng.example;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParse{  
     
  
    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {  
  
        //列表/array 数据  
        String str="[{\"id\": 1,\"code\": \"bj\",\"name\": \"北京\",\"map\": \"39.90403, 116.40752599999996\"}, {\"id\": 2,\"code\": \"sz\",\"name\": \"深圳\",\"map\": \"22.543099, 114.05786799999998\"}]";  
  
        List<City> rs = new ArrayList<City>();
        
        ObjectMapper mapper = new ObjectMapper();
        rs = mapper.readValue(str, new TypeReference<List<City>>(){});
        
        for (City o : rs) {

            System.out.println(o.name);

        }
        // map数据

        String jsonStr = "{\"1\": {\"id\": \"1\",\"code\": \"bj\",\"name\": \"北京\",\"map\": \"39.90403, 116.40752599999996\"},\"2\": {\"id\": \"2\",\"code\": \"sz\",\"name\": \"深圳\",\"map\": \"22.543099, 114.05786799999998\"},\"9\": {\"id\": \"9\",\"code\": \"sh\",\"name\": \"上海\",\"map\": \"31.230393,121.473704\"},\"10\": {\"id\": \"10\",\"code\": \"gz\",\"name\": \"广州\",\"map\": \"23.129163,113.26443500000005\"}}";

        Map<String, City> citys = mapper.readValue(jsonStr, new TypeReference<Map<String, City>>() {});
        
        System.out.println(citys.get("1").name + "----------" + citys.get("2").code);
    }
}  

class City implements Serializable{
    
    private static final long serialVersionUID = 1L;
    Integer id;
    String name;
    String code;
    String map;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMap() {
        return map;
    }
    public void setMap(String map) {
        this.map = map;
    }
}  