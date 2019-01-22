package com.irongteng.web.management;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.irongteng.annotation.Permission;
import com.irongteng.conf.CommonConst;
import com.irongteng.persistence.DeviceVO;
import com.irongteng.service.DeviceBean;
import com.irongteng.service.DeviceService;
import com.irongteng.service.Role;
import com.irongteng.web.BaseController;

import dwz.common.util.excel.ExcelBundle;
import dwz.framework.sys.exception.ServiceException;

/**
 * 设备信息管理
 * 
 * @author tdx
 */
@Controller("management.deviceController")
@RequestMapping(value = "/management/device")
public class DeviceController extends BaseController {
 
    @Autowired
    private DeviceService deviceService;

    @RequestMapping("")
    public String list(DeviceVO vo, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<DeviceBean> beans = deviceService.search(vo);
        Integer totalCount = deviceService.searchNum(vo);
        vo.setTotalCount(totalCount);
        model.addAttribute("beans", beans);
        model.addAttribute("vo", vo);
        String sessionid = request.getSession().getId();
        model.addAttribute("sessionid", sessionid);
        model.addAttribute("deviceType", CommonConst.getDeviceType());
        return "/management/device/list";
    }

    
    @RequestMapping("/add")
    @Permission({ Role.ADMIN_ROLE, Role.MANAGER_ROLE })
    public String add(Model model) { 
        DeviceBean bean = new DeviceBean();
        model.addAttribute("bean", bean);
        model.addAttribute("deviceType", CommonConst.getDeviceType());
        return "/management/device/add";
    }

    @RequestMapping("/edit/{id}")
    @Permission({ Role.ADMIN_ROLE, Role.MANAGER_ROLE })
    public String edit(@PathVariable("id") int id, Model model) {
        DeviceBean bean = deviceService.get(id);
        model.addAttribute("bean", bean);
        model.addAttribute("deviceType", CommonConst.getDeviceType());
        return "/management/device/edit";
    }

    /**
     * 查找带回设备信息列表
     *
     * @param vo
     * @param model
     * @return
     */
    @RequestMapping("/lookup")
    public String lookup(DeviceVO vo, Model model) {
        List<DeviceBean> beans = deviceService.search(vo);
        Integer totalCount = deviceService.searchNum(vo);
        vo.setTotalCount(totalCount);
        model.addAttribute("beans", beans);
        model.addAttribute("vo", vo);
        model.addAttribute("deviceType", CommonConst.getDeviceType());
        return "/management/device/lookup";
    }

    /**
     * 查找返回设备信息
     *
     * @param vo
     * @param model
     * @return
     */
    @RequestMapping(value = "/lookupSuggest", method = RequestMethod.POST)
    @ResponseBody
    public List<DeviceBean> lookupSuggest(DeviceVO vo, Model model) {
        List<DeviceBean> beans = new ArrayList<>();
        Integer totalCount = deviceService.searchNum(vo);
        vo.setPageSize(totalCount);
        if (totalCount <= 0 && vo.getKeywords() != null) {
            DeviceBean bean = new DeviceBean();
            beans.add(bean);
        } else {
            beans.addAll(deviceService.search(vo));
        }
        return beans;
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @Permission({ Role.ADMIN_ROLE, Role.MANAGER_ROLE })
    public ModelAndView insert(DeviceBean bean) {
        try {
            deviceService.add(bean);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Permission({ Role.ADMIN_ROLE, Role.MANAGER_ROLE })
    public ModelAndView update(DeviceBean bean) {
        try {
            deviceService.update(bean);
            return ajaxDoneSuccess(getMessage("msg.operation.success"));
        } catch (ServiceException e) {
            e.printStackTrace();
            return ajaxDoneError(e.getMessage());
        }

    }

    @RequestMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Integer id) {

        deviceService.delete(id);

        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }

    @RequestMapping("/deleteBatch/{IDs}")
    public ModelAndView deleteBatch(HttpServletResponse response, HttpServletRequest request) {
        String[] ids = request.getParameter("ids").split(",");
        for (String id : ids) {
            deviceService.delete(Integer.parseInt(id));
        }

        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }

//    private static String[] headers = { "设备名字", "客户名字", "设备类型", "特征码", "地址", "经度", "纬度", "说明", "设备id" };
//
//    /**
//     * 导出Excel
//     * 
//     * @param vo
//     * @param request
//     * @param response
//     */
//    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
//    public void exportExcel(DeviceVO vo, HttpServletRequest request, HttpServletResponse response) {
//        try {
//            int pageSize = 30000; // 每页显示数
//            int pageNum = 0;
//
//            Integer totalCount = deviceService.searchNum(vo);
//            vo.setTotalCount(totalCount);
//            vo.setPageSize(pageSize);
//
//            if (totalCount % pageSize == 0) {
//                pageNum = totalCount / pageSize;
//            } else {
//                pageNum = totalCount / pageSize + 1;
//            }
//
//            List<String[]> strings = new ArrayList<>();
//            // 标题头
//            strings.add(headers);
//
//            String[] cs = new String[9];
//
//            for (int i = 0; i < pageNum; i++) {
//                vo.setPageNum(i + 1);
//                List<DeviceBean> beans = deviceService.search(vo);
//                for (DeviceBean bean : beans) {
//                    cs[0] = String.valueOf(bean.getDeviceName());
//                    cs[1] = bean.getCustomerName();
//                    cs[2] = String.valueOf(bean.getDeviceType());
//                    cs[3] = bean.getFeatureCode();
//                    cs[4] = bean.getAddress();
//                    cs[5] = bean.getLongitude();
//                    cs[6] = bean.getLatitude();
//                    cs[7] = bean.getRemark();
//                    cs[8] = String.valueOf(bean.getCustomerID());
//                    strings.add(Arrays.copyOf(cs, cs.length));
//                }
//                beans.clear();
//                beans = null;
//            }
//
//            new ExcelBundle().downloadExcel(strings, "设备信息表", request, response);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
    public void exportExcel(DeviceVO vo, HttpServletRequest request, HttpServletResponse response) {
        try {
            int pageSize = 60000; // 每页显示数
            int pageNum;
            Integer totalCount = deviceService.searchNum(vo);
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
                List<DeviceBean> beans = deviceService.search(vo);
                beans.stream().map((bean) -> {
                    // 重置内容
                    Map<Integer, Object> data = new HashMap<>();
                    data.put(1, bean.getDeviceName());
                    data.put(2, bean.getCustomerName());
                    data.put(3, bean.getDeviceType());
                    data.put(4, bean.getFeatureCode());
                    data.put(5, bean.getAddress());
                    data.put(6, bean.getLongitude());
                    data.put(7, bean.getLatitude());
                    data.put(8, bean.getRemark());
                    data.put(9, bean.getCustomerID());
                    return data;
                }).forEachOrdered((data) -> {
                    datalist.add(data);
                });
                beans.clear();
            }
            String tempFilePath = TranstFlowController.class.getResource("/template/device.xlsx").toURI().getPath();
            System.out.println(tempFilePath);
            // 必须为列表头部所有位置集合， 输出数据单元格样式和头部单元格样式保持一致
            String[] heads = new String[] { "A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1", "I1" };
            new ExcelBundle().downloadExcel(tempFilePath, "设备信息表", heads, datalist, request, response);
        } catch (URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 打开导入excel控件窗口
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/importExcelDialog")
    @Permission
    public String importExcelDialog(HttpServletRequest request, HttpServletResponse response, Model model) {
        String sessionid = request.getSession().getId();
        model.addAttribute("sessionid", sessionid);
        return "/management/device/uploadify";
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
    @RequestMapping("/importExcel")
    public ModelAndView importExcel(HttpServletRequest request, HttpServletResponse response)
            throws IllegalStateException, IOException {
        // 创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        // 判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            // 转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {

                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    try {
                        String fileName = file.getOriginalFilename();
                        if ("".equals(fileName.trim()))
                            throw new Exception(getMessage("msg.upload.isnull"));
                        // 将excel文件数据读入List列表string数组
                        List<String[]> strings = new ExcelBundle().readExcel(file, 9);
                        // 导入excel文件
                        deviceService.importExcel(strings);
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
