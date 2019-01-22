package com.irongteng.web.management;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.irongteng.annotation.Permission;
import com.irongteng.persistence.BaseConditionVO;
import com.irongteng.service.ImsiPhoneBean;
import com.irongteng.service.ImsiPhoneService;
import com.irongteng.service.Role;
import com.irongteng.web.BaseController;

import dwz.common.util.excel.ExcelBundle;
import dwz.framework.sys.exception.ServiceException;

/**
 * IMSI与手机号码映射控制器
 * @author lvlei
 *
 */
@Controller("management.ImsiPhoneController")
@RequestMapping(value="/management/imsi_phone")
public class ImsiPhoneController extends BaseController{
    
    @Autowired
    private ImsiPhoneService imsiPhoneService;
    
    @RequestMapping()
    @Permission({Role.ADMIN_ROLE,Role.MANAGER_ROLE})
    public String list(BaseConditionVO vo,HttpServletRequest request,HttpServletResponse response, Model model) {
        List<ImsiPhoneBean> callList = imsiPhoneService.search(vo);
        Integer totalCount = imsiPhoneService.searchNum(vo);
        vo.setTotalCount(totalCount);
        
        model.addAttribute("callList", callList);
        model.addAttribute("vo", vo);
        String sessionid = request.getSession().getId();
        model.addAttribute("sessionid", sessionid);
        return "/management/imsi_phone/list";
    }
    /**
     * 获取设备详情
     * 
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/detail/{id}")
    @Permission({Role.ADMIN_ROLE,Role.MANAGER_ROLE})
    public String detail(@PathVariable("id") int id, Model model) {
        ImsiPhoneBean bean = imsiPhoneService.get(id);
        
        model.addAttribute("vo", bean);
        
        return "/management/imsi_phone/list";
    }
    @RequestMapping("/add")
    @Permission({Role.ADMIN_ROLE,Role.MANAGER_ROLE})
    public String add(Model model) {
        ImsiPhoneBean bean = new ImsiPhoneBean();
        model.addAttribute("vo", bean);
        return "/management/imsi_phone/add";
    }
    

    @RequestMapping("/edit/{imsiCallId}")
    @Permission({Role.ADMIN_ROLE,Role.MANAGER_ROLE})
    public String edit(@PathVariable("imsiCallId") int imsiCallId, Model model) {
        ImsiPhoneBean call = imsiPhoneService.get(imsiCallId);
        
        model.addAttribute("vo", call);
        
        return "/management/imsi_phone/edit";
    }
    
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @Permission({Role.ADMIN_ROLE,Role.MANAGER_ROLE})
    public ModelAndView insert(ImsiPhoneBean call) {
        try {
            imsiPhoneService.add(call);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }

        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Permission({Role.ADMIN_ROLE,Role.MANAGER_ROLE})
    public ModelAndView update(ImsiPhoneBean call) {
        try {
            imsiPhoneService.update(call);
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }
        
    }
    
    @RequestMapping("/delete/{id}")
    @Permission({Role.ADMIN_ROLE,Role.MANAGER_ROLE})
    public ModelAndView delete(@PathVariable("id") Integer id) {
        imsiPhoneService.delete(id);
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }
    
    @RequestMapping("/deleteBatch/{monitorIDs}")
    @Permission({Role.ADMIN_ROLE,Role.MANAGER_ROLE})
    public ModelAndView deleteBatch(HttpServletResponse response,HttpServletRequest request) {
        String [] ids = request.getParameter("ids").split(",");
        for (String id: ids) {
            imsiPhoneService.delete(Integer.parseInt(id));
        }
        
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }
    
    private static final String[] HEADER = {"IMSI", "电话号码"};
    
    /**
     * 导出excel文件
     * 
     * @param vo
     * @param request
     * @param response
     */
    @RequestMapping(value="/exportExcel", method=RequestMethod.GET)  
    public void exportExcel(BaseConditionVO vo, HttpServletRequest request, HttpServletResponse response) {  
        try {
            int pageSize = 60000; //每页显示数
            int pageNum = 0;
            
            Integer totalCount = imsiPhoneService.searchNum(vo);
            vo.setTotalCount(totalCount);
            vo.setPageSize(pageSize); //
            
            if (totalCount%pageSize==0) {
                pageNum = totalCount/pageSize;
            } else {
                pageNum = totalCount/pageSize + 1;
            }
            
            List<String[]> strings = new ArrayList<>();
            //标题头
	    strings.add(HEADER);
            
            String[] cs = new String[2];
            for (int i=0; i<pageNum; i++) {
                vo.setPageNum(i+1);
                List<ImsiPhoneBean> beans = imsiPhoneService.search(vo);
                
                for (ImsiPhoneBean bean: beans) {
                    cs[0] = String.valueOf(bean.getImsi());
                    cs[1] = String.valueOf(bean.getPhone());
                    strings.add(Arrays.copyOf(cs,cs.length));
                }
                beans.clear();
                beans = null;
            }
            //导入excel操作
            new ExcelBundle().downloadExcel(strings, "IMSI与号码信息", request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 打开导入excel控件窗口
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/importExcelDialog")
    @Permission
    public String importExcelDialog(HttpServletRequest request,HttpServletResponse response, Model model) {
        String sessionid = request.getSession().getId();
        model.addAttribute("sessionid", sessionid);
        return "/management/imsi_phone/uploadify";
    }
    
    /**
     * 提交excel导入文件，进行excel操作
     * 
     * @param request
     * @param response
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    @RequestMapping("/importExcel"  )  
    public ModelAndView importExcel(HttpServletRequest request,HttpServletResponse response) throws IllegalStateException, IOException {  
        //创建一个通用的多部分解析器  
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
        //判断 request 是否有文件上传,即多部分请求  
        if(multipartResolver.isMultipart(request)){  
            //转换成多部分request    
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
            //取得request中的所有文件名  
            Iterator<String> iter = multiRequest.getFileNames();  
            while(iter.hasNext()){
                
                MultipartFile file = multiRequest.getFile(iter.next());  
                
                if(file != null){  
                    try {
                    	String fileName = file.getOriginalFilename();
                    	if ("".equals(fileName.trim())) throw new Exception(getMessage("msg.upload.isnull"));
                        List<String[]> strings = new ExcelBundle().readExcel(file, 5);
                        // 导入excel操作
                        imsiPhoneService.importExcel(strings);
                    } catch (Exception e) {
                        return ajaxDoneError(e.getMessage());
                    } 
                }  
            }
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        }
        return ajaxDoneError(getMessage("msg.operation.failure"));
    } 
    
}
