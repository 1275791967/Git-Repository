package com.irongteng.web.management;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.irongteng.annotation.Permission;
import com.irongteng.persistence.BaseConditionVO;
import com.irongteng.service.AttributionBean;
import com.irongteng.service.AttributionService;
import com.irongteng.service.Role;
import com.irongteng.web.BaseController;

import dwz.common.util.excel.ExcelBundle;
import dwz.framework.sys.exception.ServiceException;
/**
 * 号码归属地
 * 
 * @author lvlei
 */
@Controller("management.AttributionController")
@RequestMapping(value="/management/attribution")
public class AttributionController extends BaseController{
    
    @Autowired
    private AttributionService attributionService;
    
    @RequestMapping()
    @Permission({Role.ADMIN_ROLE,Role.MANAGER_ROLE})
    public String list(BaseConditionVO vo,HttpServletRequest request,HttpServletResponse response, Model model) {
        List<AttributionBean> beans = attributionService.search(vo);
        Integer totalCount = attributionService.searchNum(vo);
        vo.setTotalCount(totalCount);
        
        model.addAttribute("beans", beans);
        model.addAttribute("vo", vo);
        String sessionid = request.getSession().getId();
        model.addAttribute("sessionid", sessionid);
        return "/management/attribution/list";
    }
    
    @RequestMapping("/add")
    @Permission({Role.ADMIN_ROLE,Role.MANAGER_ROLE})
    public String add(Model model) {
        AttributionBean bean = new AttributionBean();
        model.addAttribute("bean", bean);
        return "/management/attribution/add";
    }
    
    @RequestMapping("/edit/{id}")
    @Permission({Role.ADMIN_ROLE,Role.MANAGER_ROLE})
    public String edit(@PathVariable("id") int id, Model model) {
        AttributionBean bean = attributionService.get(id);   
        model.addAttribute("bean", bean);
        return "/management/attribution/edit";
    }
    
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @Permission({Role.ADMIN_ROLE,Role.MANAGER_ROLE})
    public ModelAndView insert(AttributionBean bean) {
        try {
            attributionService.add(bean);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }

        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Permission({Role.ADMIN_ROLE,Role.MANAGER_ROLE})
    public ModelAndView update(AttributionBean bean) {
        try {
            attributionService.update(bean);
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } catch (ServiceException e) {
            e.printStackTrace();
            return ajaxDoneError(e.getMessage());
        }
        
    }
    
    @RequestMapping("/delete/{id}")
    @Permission({Role.ADMIN_ROLE,Role.MANAGER_ROLE})
    public ModelAndView delete(@PathVariable("id") Integer id) {

        attributionService.delete(id);
        
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }
    
    @RequestMapping("/deleteBatch/{monitorIDs}")
    @Permission({Role.ADMIN_ROLE,Role.MANAGER_ROLE})
    public ModelAndView deleteBatch(HttpServletResponse response,HttpServletRequest request) {
        String [] ids = request.getParameter("ids").split(",");
        for (String id: ids) {
            attributionService.delete(Integer.parseInt(id));
        }
        
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }
    
    private static String[] headers = {"号码段","所属省","所属市","城市代码","详情"};
    
    @RequestMapping(value="/exportExcel", method=RequestMethod.GET)  
    public void exportExcel(BaseConditionVO vo, HttpServletRequest request, HttpServletResponse response) {  
        try {
            int pageSize = 30000; //每页显示数
            int pageNum = 0;
            
            Integer totalCount = attributionService.searchNum(vo);
            vo.setTotalCount(totalCount);
            vo.setPageSize(pageSize); //
            
            if (totalCount%pageSize==0) {
                pageNum = totalCount/pageSize;
            } else {
                pageNum = totalCount/pageSize + 1;
            }
            
            List<String[]> strings = new ArrayList<>();
            //标题头
			strings.add(headers);
			
            String[] cs = new String[5];
            
            for (int i=0; i<pageNum; i++) {
                vo.setPageNum(i+1);
                List<AttributionBean> beans = attributionService.search(vo);
                for (AttributionBean bean: beans) {
                    cs[0] = String.valueOf(bean.getPhoneNO());
                    cs[1] = bean.getProvince();
                    cs[2] = bean.getCity();
                    cs[3] = String.valueOf(bean.getCellNO());
                    cs[4] = bean.getRemark();
                    strings.add(Arrays.copyOf(cs,cs.length));
                }
                beans.clear();
                beans = null;
            }
            
            new ExcelBundle().downloadExcel(strings, "号码归属地信息", request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @RequestMapping(value = "/upload")  
    public String upload(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, ModelMap model) {  
        
        System.out.println("上传文件开始开始...");
        String path = request.getSession().getServletContext().getRealPath("upload");
        String fileName = file.getOriginalFilename();
        
        File targetFile = new File(path, fileName);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        
        //保存
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            e.printStackTrace();
        }  
        model.addAttribute("fileUrl", request.getContextPath()+"/upload/"+fileName);
  
        return "result";
    }
    
    @RequestMapping("/importExcelDialog")
    @Permission
    public String importExcelDialog(HttpServletRequest request,HttpServletResponse response, Model model) {
        String sessionid = request.getSession().getId();
        model.addAttribute("sessionid", sessionid);
        return "/management/attribution/uploadify";
    }
    
    /**
     * 导入excel xlsx文件操作
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
                if (file != null) {  
                    try {
                    	String fileName = file.getOriginalFilename();
                    	if ("".equals(fileName.trim())) throw new Exception(getMessage("msg.upload.isnull"));
                        //将excel文件数据读入List列表string数组
                        List<String[]> strings = new ExcelBundle().readExcel(file, 5);
                        //导入excel文件
                        attributionService.importExcel(strings);
                    } catch (Exception e) {
                        return ajaxDoneError(e.getMessage());
                    } 
                }  
            }
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        }
        return ajaxDoneError(getMessage("msg.operation.failure"));
    } 
    
    @RequestMapping(value="/queryProvinces", method=RequestMethod.POST)
    @Permission({Role.ADMIN_ROLE,Role.MANAGER_ROLE})
    public @ResponseBody List<String> queryProvinces() {
        List<String> beans = attributionService.searchProvinces();
        return beans;
    }
    
    /**
     * 获取根据省级id所有该省级的所有市级单位信息
     * @param province
     * @return
     * @throws UnsupportedEncodingException 
     */
    @RequestMapping(value="/queryCities/{province}", method=RequestMethod.POST)
    @Permission({Role.ADMIN_ROLE,Role.MANAGER_ROLE})
    public @ResponseBody List<String> queryCities(@PathVariable("province") String province) throws UnsupportedEncodingException {
        return attributionService.searchCitys(URLDecoder.decode(province, "UTF-8"));
    }
    /*
    @RequestMapping(value="/queryCities/{province}", method=RequestMethod.POST)
    @Permission({Role.ADMIN_ROLE,Role.MANAGER_ROLE})
    public @ResponseBody Map<String, String> queryCities(@PathVariable("province") String province) throws UnsupportedEncodingException {
    	List<String> strings = callMgr.searchCitys(URLDecoder.decode(province, "UTF-8"));
    	Map<String, String> maps = new LinkedHashMap<>();
    	strings.stream().forEach(str -> {
    		try {
				maps.put(URLEncoder.encode(str, "UTF-8"), str);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		});
    	return maps;
        //return callMgr.searchCitys(URLDecoder.decode(province, "UTF-8"));
    }
    */
}
