package com.irongteng.export;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvWrite {

    public static void main(String[] args) { 
        try { 
            File csv = new File("D:/writers.csv"); // CSV文件
            // 追记模式 
            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true)); 
            int index = 0;
            int lastIndex=0;
            List<User> users = new ArrayList<User>();
            
            for (int i=0; i<60000; i++) {
                index++;
                lastIndex ++;
                User user = new User();
                user.setId(i);
                user.setName("lvlei_" + i);
                user.setSex(0);
                user.setEmail("lulei2005_" + i);
                users.add(user);
                
                if(index>5000 || lastIndex>=600000) {
                    for(User u: users) {
                        // 新增一行数据 
                        bw.newLine(); 
                        bw.write(u.getId() + "," + u.getName() + "," + u.getSex() + "," + u.getEmail()); 
                    }
                    users.clear();
                    bw.flush();
                }
            }
            bw.close();
            
        } catch (FileNotFoundException e) { 
            // 捕获File对象生成时的异常 
            e.printStackTrace(); 
        } catch (IOException e) { 
            // 捕获BufferedWriter对象关闭时的异常 
            e.printStackTrace(); 
        } 
    }
    
}

class User {
    private int id;
    private String name;
    private int sex;
    private String email;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getSex() {
        return sex;
    }
    public void setSex(int sex) {
        this.sex = sex;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}