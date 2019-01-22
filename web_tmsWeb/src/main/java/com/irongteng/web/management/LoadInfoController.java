package com.irongteng.web.management;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.irongteng.annotation.Permission;
import com.irongteng.persistence.LoadInfoVO;
import com.irongteng.service.LoadInfoBean;
import com.irongteng.service.LoadInfoService;
import com.irongteng.service.Role;
import com.irongteng.web.BaseController;

import dwz.common.util.excel.ExcelBundle;
import dwz.framework.sys.exception.ServiceException;
/**
 * 翻译服务器负载管理
 * 
 * @author tdx
 */
@Controller("management.loadInfoController")
@RequestMapping(value="/management/loadinfo")
public class LoadInfoController extends BaseController{
    
    @Autowired
    private LoadInfoService loadInfoService; 
    
    @RequestMapping()
    public String list(LoadInfoVO vo,HttpServletRequest request,HttpServletResponse response, Model model) {
        List<LoadInfoBean> beans = loadInfoService.search(vo);
        Integer totalCount = loadInfoService.searchNum(vo);
        vo.setTotalCount(totalCount);
        
        model.addAttribute("beans", beans);
        model.addAttribute("vo", vo);
        String sessionid = request.getSession().getId();
        model.addAttribute("sessionid", sessionid);
        return "/management/loadinfo/list";
    } 
    
    @RequestMapping("/add")
    public String add(Model model) {
        LoadInfoBean bean = new LoadInfoBean();
        model.addAttribute("bean", bean);
        return "/management/loadinfo/add";
    }
    
    @RequestMapping("/detail/{id}")
    @Permission({Role.ADMIN_ROLE,Role.MANAGER_ROLE})
    public String detail(@PathVariable("id") int id, Model model) {
        LoadInfoBean bean = loadInfoService.get(id);
        
        model.addAttribute("bean", bean);
        
        return "/management/loadinfo/detail";
    }
    
    @RequestMapping("/edit/{id}")
    @Permission({Role.ADMIN_ROLE,Role.MANAGER_ROLE})
    public String edit(@PathVariable("id") int id, Model model) {
        LoadInfoBean bean = loadInfoService.get(id);
        
        model.addAttribute("bean", bean);
        
        return "/management/loadinfo/edit";
    }
    
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ModelAndView insert(LoadInfoBean bean) {
        try {
            loadInfoService.add(bean);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }

        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(LoadInfoBean bean) {
        try {
            loadInfoService.update(bean);
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } catch (ServiceException e) {
            e.printStackTrace();
            return ajaxDoneError(e.getMessage());
        }
        
    }
    
    @RequestMapping("/delete/{id}")
    @Permission({Role.ADMIN_ROLE,Role.MANAGER_ROLE})
    public ModelAndView delete(@PathVariable("id") Integer id) {

        loadInfoService.delete(id);
        
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }
    
    @RequestMapping("/deleteBatch/{IDs}")
    @Permission({Role.ADMIN_ROLE,Role.MANAGER_ROLE})
    public ModelAndView deleteBatch(HttpServletResponse response,HttpServletRequest request) {
        String [] ids = request.getParameter("ids").split(",");
        for (String id: ids) {
            loadInfoService.delete(Integer.parseInt(id));
        }
        
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }
    
//    private static String[] headers = {"设备名字","通道1总通道数","通道1正常通道数","通道1待翻译数","通道2总通道数","通道2正常通道数","通道2待翻译数","通道3总通道数","通道3正常通道数","通道3待翻译数","通道4总通道数",
//            "通道4正常通道数","通道4待翻译数","通道5总通道数","通道5正常通道数","通道5待翻译数"};
//    /**
//     * 导出Excel
//     * 
//     * @param vo
//     * @param request
//     * @param response 
//     */
//    @RequestMapping(value="/exportExcel", method=RequestMethod.GET)  
//    public void exportExcel(LoadInfoVO vo, HttpServletRequest request, HttpServletResponse response) {  
//        try {
//            int pageSize = 30000; //每页显示数
//            int pageNum = 0;
//            
//            Integer totalCount = loadInfoService.searchNum(vo);
//            vo.setTotalCount(totalCount);
//            vo.setPageSize(pageSize); 
//            
//            if (totalCount%pageSize==0) {
//                pageNum = totalCount/pageSize;
//            } else {
//                pageNum = totalCount/pageSize + 1;
//            }
//            
//            List<String[]> strings = new ArrayList<>();
//            //标题头
//            strings.add(headers);
//            
//            String[] cs = new String[17];
//            
//            for (int i=0; i<pageNum; i++) {
//                vo.setPageNum(i+1);
//                List<LoadInfoBean> beans = loadInfoService.search(vo);
//                for (LoadInfoBean bean: beans) {
//                    cs[0] = bean.getDeviceName();
//                    cs[1] = String.valueOf(bean.getCh1Counts());
//                    cs[2] = String.valueOf(bean.getCh1NormalCounts());
//                    cs[3] = String.valueOf(bean.getCh1WaitTranslates());
//                    cs[4] = String.valueOf(bean.getCh2Counts());
//                    cs[5] = String.valueOf(bean.getCh2NormalCounts());
//                    cs[6] = String.valueOf(bean.getCh2WaitTranslates());
//                    cs[7] = String.valueOf(bean.getCh3Counts());
//                    cs[8] = String.valueOf(bean.getCh3NormalCounts());
//                    cs[9] = String.valueOf(bean.getCh3WaitTranslates());
//                    cs[10] = String.valueOf(bean.getCh4Counts());
//                    cs[11] = String.valueOf(bean.getCh4NormalCounts());
//                    cs[12] = String.valueOf(bean.getCh4WaitTranslates());
//                    cs[13] = String.valueOf(bean.getCh5Counts());
//                    cs[14] = String.valueOf(bean.getCh5NormalCounts());
//                    cs[15] = String.valueOf(bean.getCh5WaitTranslates());
//                    cs[16] = String.valueOf(bean.getAlarmStatus());
//                    strings.add(Arrays.copyOf(cs,cs.length));
//                   
//                }
//                beans.clear();
//                beans = null;
//            }
//            
//            new ExcelBundle().downloadExcel(strings, "负载信息", request, response);
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    
    
    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
    public void exportExcel(LoadInfoVO vo, HttpServletRequest request, HttpServletResponse response) {
        try {
            int pageSize = 60000; // 每页显示数
            int pageNum;
            Integer totalCount = loadInfoService.searchNum(vo);
            vo.setTotalCount(totalCount);
            vo.setPageSize(pageSize); //

            if (totalCount % pageSize == 0) {
                pageNum = totalCount / pageSize;
            } else {
                pageNum = totalCount / pageSize + 1;
            }
            List<Map<Integer, Object>> datalist = new ArrayList<>();
            for (int i = 0; i < pageNum; i++) {
                vo.setPageNum(i + 1);
                List<LoadInfoBean> beans = loadInfoService.search(vo);
                beans.stream().map((bean) -> {
                    // 重置内容
                    Map<Integer, Object> data = new HashMap<>();
                    data.put(1, bean.getDeviceName());
                    data.put(2, bean.getCh1Counts());
                    data.put(3, bean.getCh1NormalCounts());
                    data.put(4, bean.getCh1WaitTranslates());
                    data.put(5, bean.getCh2Counts());
                    data.put(6, bean.getCh2NormalCounts());
                    data.put(7, bean.getCh2WaitTranslates());
                    data.put(8, bean.getCh3Counts());
                    data.put(9, bean.getCh3NormalCounts());
                    data.put(10, bean.getCh3WaitTranslates());
                    data.put(11, bean.getCh4Counts());
                    data.put(12, bean.getCh4NormalCounts());
                    data.put(13, bean.getCh4WaitTranslates());
                    data.put(14, bean.getCh5Counts());
                    data.put(15, bean.getCh5NormalCounts());
                    data.put(16, bean.getCh5WaitTranslates());
                    data.put(17, bean.getAlarmStatus());
                    return data;
                }).forEachOrdered((data) -> {
                    datalist.add(data);
                });
                beans.clear();
            }
            String tempFilePath = TranstFlowController.class.getResource("/template/loadinfo.xlsx").toURI().getPath();
            System.out.println(tempFilePath);
            // 必须为列表头部所有位置集合， 输出数据单元格样式和头部单元格样式保持一致
            String[] heads = new String[] { "A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1", "I1", "J1", "K1", "L1", "M1", "N1",
                    "O1","P1","Q1"};
            new ExcelBundle().downloadExcel(tempFilePath, "翻译通道信息表", heads, datalist, request, response);
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }
   
}
