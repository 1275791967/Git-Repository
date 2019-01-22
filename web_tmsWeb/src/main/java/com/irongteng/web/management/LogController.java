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
import org.springframework.web.servlet.ModelAndView;

import com.irongteng.persistence.CommLogVO;
import com.irongteng.service.CommLogBean;
import com.irongteng.service.CommLogService;
import com.irongteng.web.BaseController;

import dwz.common.util.excel.ExcelBundle;
import dwz.framework.sys.exception.ServiceException;
/**
 * 通讯日志管理
 * 
 * @author tdx
 */
@Controller("management.LogController")
@RequestMapping(value="/management/log")
public class LogController extends BaseController{
    
    @Autowired
    private CommLogService commLogService;
    
    @RequestMapping()
    public String list(CommLogVO vo,HttpServletRequest request,HttpServletResponse response, Model model) {
        List<CommLogBean> beans = commLogService.search(vo);
        Integer totalCount = commLogService.searchNum(vo);
        vo.setTotalCount(totalCount);
        model.addAttribute("beans", beans);
        model.addAttribute("vo", vo);
        String sessionid = request.getSession().getId();
        model.addAttribute("sessionid", sessionid);
        return "/management/log/list";
    }
    
    @RequestMapping("/add")
    public String add(Model model) {
        CommLogBean bean = new CommLogBean();
        model.addAttribute("bean", bean);
        return "/management/log/add";
    }
    
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        CommLogBean bean = commLogService.get(id);
        
        model.addAttribute("bean", bean);
        
        return "/management/log/edit";
    }
    
    @RequestMapping("/detail/{id}")
    public String detail(@PathVariable("id") int id, Model model) {
        CommLogBean bean = commLogService.get(id);
        
        model.addAttribute("bean", bean);
        
        return "/management/log/detail";
    }
    
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
   public ModelAndView insert(CommLogBean bean) {
        try {
            commLogService.add(bean);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(CommLogBean bean) {
        try {
            commLogService.update(bean);
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } catch (ServiceException e) {
            e.printStackTrace();
            return ajaxDoneError(e.getMessage());
        }
        
    }
    
    @RequestMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Integer id) {
        commLogService.delete(id);
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }
    
    @RequestMapping("/deleteBatch/{logIDs}")
    public ModelAndView deleteBatch(HttpServletResponse response,HttpServletRequest request) {
        String [] ids = request.getParameter("ids").split(",");
        for (String id: ids) {
            commLogService.delete(Integer.parseInt(id));
        }
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }
    
    
    private static String[] headers = {"设备名称","日志类型","内容","说明"};
    /**
     * 导出Excel
     * 
     * @param vo
     * @param request
     * @param response 
     */
    @RequestMapping(value="/exportExcel", method=RequestMethod.GET)  
    public void exportExcel(CommLogVO vo, HttpServletRequest request, HttpServletResponse response) {  
        try {
            int pageSize = 30000; //每页显示数
            int pageNum = 0;
            
            Integer totalCount = commLogService.searchNum(vo);
            vo.setTotalCount(totalCount);
            vo.setPageSize(pageSize); 
            
            if (totalCount%pageSize==0) {
                pageNum = totalCount/pageSize;
            } else {
                pageNum = totalCount/pageSize + 1;
            }
            
            List<String[]> strings = new ArrayList<>();
            //标题头
            strings.add(headers);
            
            String[] cs = new String[4];
            
            for (int i=0; i<pageNum; i++) {
                vo.setPageNum(i+1);
                List<CommLogBean> beans = commLogService.search(vo);
                for (CommLogBean bean: beans) {
                    cs[0] = String.valueOf(bean.getDeviceName());
                    cs[1] =  String.valueOf(bean.getLogTypeID());
                    cs[2] = bean.getContent();
                    cs[3] = bean.getRemark();
                }
                beans.clear();
                beans = null;
            }
            
            new ExcelBundle().downloadExcel(strings, "日志信息表", request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
