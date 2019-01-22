package com.irongteng.web.management;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.irongteng.annotation.Permission;
import com.irongteng.persistence.CustomerVO;
import com.irongteng.service.CustomerBean;
import com.irongteng.service.CustomerService;
import com.irongteng.service.Role;
import com.irongteng.web.BaseController;

import dwz.framework.sys.exception.ServiceException;
/**
 * 客户信息管理
 * 
 * @author tdx
 */
@Controller("management.customerController")
@RequestMapping(value="/management/customer")
public class CustomerController extends BaseController{
    
    @Autowired
    private CustomerService customerServices;
    
    @RequestMapping()
    public String list(CustomerVO vo,HttpServletRequest request,HttpServletResponse response, Model model) {
        List<CustomerBean> beans = customerServices.search(vo);
        Integer totalCount = customerServices.searchNum(vo);
        vo.setTotalCount(totalCount);
        
        model.addAttribute("beans", beans);
        model.addAttribute("vo", vo);
        String sessionid = request.getSession().getId();
        model.addAttribute("sessionid", sessionid);
        return "/management/customer/list";
    }
    
    @RequestMapping("/add")
    @Permission({Role.ADMIN_ROLE,Role.MANAGER_ROLE})
    public String add(Model model) {
        CustomerBean bean = new CustomerBean();
        model.addAttribute("bean", bean);
        return "/management/customer/add";
    }
    
    @RequestMapping("/edit/{id}")
    @Permission({Role.ADMIN_ROLE,Role.MANAGER_ROLE})
    public String edit(@PathVariable("id") int id, Model model) {
        CustomerBean bean = customerServices.get(id);
        
        model.addAttribute("bean", bean);
        
        return "/management/customer/edit";
    }
    
    /**
     * 查找带回客户信息列表
     *
     * @param vo
     * @param model
     * @return
     */
    @RequestMapping("/lookup")
    @Permission({Role.ADMIN_ROLE,Role.MANAGER_ROLE})
    public String lookup(CustomerVO vo, Model model) {
        List<CustomerBean> beans = customerServices.search(vo);
        Integer totalCount = customerServices.searchNum(vo);
        vo.setTotalCount(totalCount);
        model.addAttribute("beans", beans);
        model.addAttribute("vo", vo);
        return "/management/customer/lookup";
    }
    
    /**
     * 查找返回客户信息
     *
     * @param vo
     * @param model
     * @return
     */
    @RequestMapping(value = "/lookupSuggest", method = RequestMethod.POST)
    @Permission({Role.ADMIN_ROLE,Role.MANAGER_ROLE})
    @ResponseBody
    public List<CustomerBean> lookupSuggest(CustomerVO vo, Model model) {
        List<CustomerBean> beans = new ArrayList<>();
        Integer totalCount = customerServices.searchNum(vo);
        vo.setPageSize(totalCount);
        if (totalCount<=0 && vo.getKeywords()!=null) {
            CustomerBean bean = new CustomerBean();
            beans.add(bean);
        } else {
            beans.addAll(customerServices.search(vo));
        }
        return beans;
    }
    
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @Permission({Role.ADMIN_ROLE,Role.MANAGER_ROLE})
    public ModelAndView insert(CustomerBean bean) {
        try {
            customerServices.add(bean);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }

        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Permission({Role.ADMIN_ROLE,Role.MANAGER_ROLE})
    public ModelAndView update(CustomerBean bean) {
        try {
            customerServices.update(bean);
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } catch (ServiceException e) {
            e.printStackTrace();
            return ajaxDoneError(e.getMessage());
        }
        
    }
    
    @RequestMapping("/delete/{id}")
    @Permission({Role.ADMIN_ROLE,Role.MANAGER_ROLE})
    public ModelAndView delete(@PathVariable("id") Integer id) {

        customerServices.delete(id);
        
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }
    
    @RequestMapping("/deleteBatch/{IDs}")
    @Permission({Role.ADMIN_ROLE,Role.MANAGER_ROLE})
    public ModelAndView deleteBatch(HttpServletResponse response,HttpServletRequest request) {
        String [] ids = request.getParameter("ids").split(",");
        for (String id: ids) {
            customerServices.delete(Integer.parseInt(id));
        }
        
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }
    
    
}
