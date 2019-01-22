package com.irongteng.web;

import com.irongteng.conf.VersionConfig;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.irongteng.license.ReadLicense;
import org.springframework.ui.Model;

@Controller
public class IndexController extends BaseController{
    
    @RequestMapping("")
    public String index(Model model) {
        // 授权判断，如果没有授权，将跳转到授权页面
        if (ReadLicense.isVerify()) {
            return "/login";
        }
        if (ReadLicense.isOverTime()) {
            return "/utils/licenseOverTime";
        }
        return "/utils/licenseError";
    }
    
    @RequestMapping("/help")
    public String help() {
        return "/utils/help";
    }
    
    @RequestMapping("/aboutMe")
    public String aboutMe(Model model) {
        VersionConfig version = new VersionConfig();
        model.addAttribute("version", version.getVersion());
        model.addAttribute("updateDate", version.getUpdateDate());
        return "/utils/aboutMe";
    }

    @RequestMapping("/contactUs")
    public String contactMe() {
        return "/utils/contactUs";
    }
    
    @RequestMapping("/login")
    public String login() {
        // 授权判断，如果没有授权，将跳转到授权页面
        if (ReadLicense.verify()) {
            return "/login";
        }
        return "/utils/licenseError";
    }
    
    @RequestMapping("/logout")
    public String logout() {
        // 授权判断，如果没有授权，将跳转到授权页面
        if (ReadLicense.verify()) {
            return "/login";
        }
        return "/utils/licenseError";
    }
}