package com.irongteng.web.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.irongteng.annotation.Permission;
import com.irongteng.service.Role;
import com.irongteng.web.BaseController;

import dwz.business.website.Template;
import dwz.business.website.Website;
import dwz.business.website.WebsiteServiceMgr;
import dwz.framework.sys.exception.ServiceException;

/**
 * 模板控制
 * @author lvlei
 *
 */
@Controller("management.websiteController")
@RequestMapping(value = "/management/website")
public class WebsiteController extends BaseController {
    
    @Autowired
    private WebsiteServiceMgr websiteMgr;

    @RequestMapping("/edit")
    @Permission(Role.ADMIN_ROLE)
    public String edit(Model model) {
        model.addAttribute("website", websiteMgr.getWebsite());
        model.addAttribute("templates", websiteMgr.getTemplates());
        model.addAttribute("defaultTemplate", websiteMgr.getDefaultTemplate());

        return "/management/website/edit";
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Permission(Role.ADMIN_ROLE)
    public ModelAndView save(Website website) {
        try {
            websiteMgr.saveWebsite(website);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getLocalizedMessage());
        }
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }
    
    @RequestMapping("/preview/{templateName}")
    @Permission(Role.ADMIN_ROLE)
    public String preview(@PathVariable("templateName") String templateName, Model model) {
        Template template = websiteMgr.getTemplateByName(templateName);
        model.addAttribute("template", template);
        
        return "/management/website/preview";
    }
}
