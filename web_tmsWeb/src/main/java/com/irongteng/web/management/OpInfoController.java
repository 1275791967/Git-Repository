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
import com.irongteng.persistence.OpInfoVO;
import com.irongteng.service.OpInfoBean;
import com.irongteng.service.OpInfoService;
import com.irongteng.service.Role;
import com.irongteng.web.BaseController;

import dwz.framework.sys.exception.ServiceException;

@Controller("management.OpInfoController")
@RequestMapping(value = "/management/opInfo")
public class OpInfoController extends BaseController {
    @Autowired
    private OpInfoService opInfoService;

    @RequestMapping()
    @Permission({ Role.ADMIN_ROLE, Role.MANAGER_ROLE })
    public String list(OpInfoVO vo, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<OpInfoBean> beans = opInfoService.search(vo);
        Integer totalCount = opInfoService.searchNum(vo);
        vo.setTotalCount(totalCount);

        model.addAttribute("beans", beans);
        model.addAttribute("vo", vo);
        String sessionid = request.getSession().getId();
        model.addAttribute("sessionid", sessionid);
        return "/management/opInfo/list";
    }

    @RequestMapping("/add")
    @Permission({ Role.ADMIN_ROLE, Role.MANAGER_ROLE })
    public String add(Model model) {
        OpInfoBean bean = new OpInfoBean();
        model.addAttribute("bean", bean);
        return "/management/opInfo/add";
    }

    @RequestMapping("/edit/{id}")
    @Permission({ Role.ADMIN_ROLE, Role.MANAGER_ROLE })
    public String edit(@PathVariable("id") int id, Model model) {
        OpInfoBean bean = opInfoService.get(id);
        model.addAttribute("bean", bean);
        return "/management/opInfo/edit";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @Permission({ Role.ADMIN_ROLE, Role.MANAGER_ROLE })
    public ModelAndView insert(OpInfoBean bean) {
        try {
            opInfoService.add(bean);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }

        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Permission({ Role.ADMIN_ROLE, Role.MANAGER_ROLE })
    public ModelAndView update(OpInfoBean bean) {
        try {
            opInfoService.update(bean);
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } catch (ServiceException e) {
            e.printStackTrace();
            return ajaxDoneError(e.getMessage());
        }

    }

    @RequestMapping("/delete/{id}")
    @Permission({ Role.ADMIN_ROLE, Role.MANAGER_ROLE })
    public ModelAndView delete(@PathVariable("id") Integer id) {
        opInfoService.delete(id);
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }

    @RequestMapping("/deleteBatch/{IDs}")
    @Permission({ Role.ADMIN_ROLE, Role.MANAGER_ROLE })
    public ModelAndView deleteBatch(HttpServletResponse response, HttpServletRequest request) {
        String[] ids = request.getParameter("ids").split(",");
        for (String id : ids) {
            opInfoService.delete(Integer.parseInt(id));
        }

        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }

    /**
     * 查找带回信息列表
     * 
     * @param vo
     * @param model
     * @return
     */
    @RequestMapping("/lookup")
    @Permission({ Role.ADMIN_ROLE, Role.MANAGER_ROLE })
    public String lookup(OpInfoVO vo, Model model) {
        List<OpInfoBean> beans = opInfoService.search(vo);
        Integer totalCount = opInfoService.searchNum(vo);
        vo.setTotalCount(totalCount);
        model.addAttribute("beans", beans);
        model.addAttribute("vo", vo);
        return "/management/opInfo/lookup";
    }

    /**
     * 查找返回信息
     *
     * @param vo
     * @param model
     * @return
     */
    @RequestMapping(value = "/lookupSuggest", method = RequestMethod.POST)
    @Permission({ Role.ADMIN_ROLE, Role.MANAGER_ROLE })
    @ResponseBody
    public List<OpInfoBean> lookupSuggest(OpInfoVO vo, Model model) {
        List<OpInfoBean> beans = new ArrayList<>();
        Integer totalCount = opInfoService.searchNum(vo);
        vo.setPageSize(totalCount);
        if (totalCount <= 0 && vo.getKeywords() != null) {
            OpInfoBean bean = new OpInfoBean();
            beans.add(bean);
        } else {
            beans.addAll(opInfoService.search(vo));
        }
        return beans;
    }

}
