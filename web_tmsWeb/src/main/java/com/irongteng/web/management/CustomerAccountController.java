package com.irongteng.web.management;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.irongteng.persistence.CustomerAccountVO;
import com.irongteng.service.CustomerAccountBean;
import com.irongteng.service.CustomerAccountService;
import com.irongteng.service.CustomerAccountStatus;
import com.irongteng.web.BaseController;

import dwz.framework.sys.exception.ServiceException;
/**
 * 客户帐号管理
 * 
 * @author tdx
 */
@Controller("management.customerAccountController")
@RequestMapping(value="/management/customer_account")
public class CustomerAccountController extends BaseController{
    
    @Autowired
    private CustomerAccountService customerAccountService;
    
    @RequestMapping()
    public String list(CustomerAccountVO vo,HttpServletRequest request,HttpServletResponse response, Model model) {
        List<CustomerAccountBean> beans = customerAccountService.search(vo);
        Integer totalCount = customerAccountService.searchNum(vo);
        vo.setTotalCount(totalCount);
        
        model.addAttribute("beans", beans);
        model.addAttribute("vo", vo);
        String sessionid = request.getSession().getId();
        model.addAttribute("sessionid", sessionid);
        model.addAttribute("status", CustomerAccountStatus.values());
        return "/management/customer_account/list";
    }
    
    @RequestMapping("/add")
    public String add(Model model) {
        CustomerAccountBean bean = new CustomerAccountBean();
        model.addAttribute("bean", bean);
        return "/management/customer_account/add";
    }
    
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        CustomerAccountBean bean = customerAccountService.get(id);
        model.addAttribute("bean", bean);
        return "/management/customer_account/edit";
    }
    
    @RequestMapping("/pay/{id}")
    public String pay(@PathVariable("id") int id, Model model) {
        CustomerAccountBean bean = customerAccountService.get(id);
        model.addAttribute("bean", bean);
        return "/management/customer_account/pay";
    }
    
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insert(CustomerAccountBean bean) {
        try {
            customerAccountService.add(bean);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(CustomerAccountBean bean) {
        try {
            customerAccountService.update(bean);
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } catch (ServiceException e) {
            e.printStackTrace();
            return ajaxDoneError(e.getMessage());
        }
    }
    
    @RequestMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Integer id) {
        customerAccountService.delete(id);
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }
    
    @RequestMapping("/deleteBatch/{IDs}")
    public ModelAndView deleteBatch(HttpServletResponse response,HttpServletRequest request) {
        String [] ids = request.getParameter("ids").split(",");
        for (String id: ids) {
            customerAccountService.delete(Integer.parseInt(id));
        }
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }
   
    /**
     * 启用
     * 
     * @param id
     * @return
     */
    @RequestMapping("/active/{id}")
    public ModelAndView active(@PathVariable("id") int id) {
        try {
            customerAccountService.active(id);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }

    /**
     * 禁用
     * 
     * @param id
     * @return
     */
    @RequestMapping("/inActive/{id}")
    public ModelAndView inActive(@PathVariable("id") int id) {
        try {
            customerAccountService.inActive(id);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }

    
}
