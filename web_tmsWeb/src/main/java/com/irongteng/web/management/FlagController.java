package com.irongteng.web.management;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.irongteng.web.BaseController;

/**
 * 标签控制
 * 
 * @author lvlei
 *
 */
@Controller("frag.fragController")
@RequestMapping(value="/management/frag")
public class FlagController extends BaseController{

    @RequestMapping("pagerForm")
    public String pageForm(Model model) {
        return "/management/_frag/pager/pagerForm";
    }

    @RequestMapping("panelBar")
    public String panelBar(HttpServletRequest request, Model model) {
    	String targetType = request.getParameter("targetType");
    	
    	if(targetType==null)  targetType = "navTab";
    	model.addAttribute("targetType", targetType);
    	
        return "/management/_frag/pager/panelBar";
    }
}
