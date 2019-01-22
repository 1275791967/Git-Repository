package com.irongteng.web.management;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
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

import com.irongteng.conf.CommonConst;
import com.irongteng.persistence.TranstFlowVO;
import com.irongteng.service.DeviceBean;
import com.irongteng.service.DeviceService;
import com.irongteng.service.TranstFlowBean;
import com.irongteng.service.TranstFlowService;
import com.irongteng.web.BaseController;

import dwz.common.util.excel.ExcelBundle;
import dwz.common.util.time.DateUtils;
/**
 * 翻译传输流程管理
 * 
 * @author tdx
 */
@Controller("management.transtFlowController")
@RequestMapping(value="/management/transt_flow")
public class TranstFlowController extends BaseController{
    
    @Autowired
    private TranstFlowService flowService;
    @Autowired
    private DeviceService deviceService;
    
    @RequestMapping()
    public String list(TranstFlowVO vo,HttpServletRequest request,HttpServletResponse response, Model model) {
        String startDate = vo.getStartDate()!=null ? vo.getStartDate(): DateUtils.formatStandardDate(DateUtils.getStartTimeOfDate(DateUtils.delayDay(-30)));
        String endDate = vo.getEndDate()!=null ? vo.getEndDate(): DateUtils.formatStandardDate(DateUtils.getEndTimeOfDate(new Date()));
        vo.setStartDate(startDate);
        vo.setEndDate(endDate);
        List<TranstFlowBean> beans = flowService.search(vo);
        Integer totalCount = flowService.searchNum(vo);
        vo.setTotalCount(totalCount);
        
        model.addAttribute("beans", beans);
        model.addAttribute("vo", vo);
        String sessionid = request.getSession().getId();
        model.addAttribute("sessionid", sessionid);
        model.addAttribute("dataTypes", CommonConst.getChannelType());
        return "/management/transt_flow/list";
    }
    
    
    @RequestMapping("/detail/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        TranstFlowBean bean = flowService.get(id);
        model.addAttribute("bean", bean);
        model.addAttribute("dataTypes", CommonConst.getChannelType());
        return "/management/transt_flow/detail";
    }
    
    
    
    
    /**
     * 统计，选择某一个设备，查看翻译的数量、翻译失败的数量，待翻译的数量。
     */
    @RequestMapping("/analyze/{deviceID}")
    public String analyzeData(@PathVariable("deviceID") int deviceID, Model model) {
        DeviceBean bean = deviceService.get(deviceID);
        TranstFlowBean beans = flowService.analyzeTranslateResult(deviceID);
        model.addAttribute("bean", bean);
        model.addAttribute("beans", beans);
        return "/management/transt_flow/analyze";
    }
    
    
//    private static String[] headers = {"发起设备ID","目的设备ID","日志类型","翻译通道类型","IMSI","IMEI","电话号码"};
//    /**
//     * 导出Excel
//     * 
//     * @param vo
//     * @param request
//     * @param response 
//     */
//    @RequestMapping(value="/exportExcel", method=RequestMethod.GET)  
//    public void exportExcel(TranstFlowVO vo, HttpServletRequest request, HttpServletResponse response) {  
//        try {
//            int pageSize = 30000; //每页显示数
//            int pageNum = 0;
//            
//            Integer totalCount = flowService.searchNum(vo);
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
//            String[] cs = new String[7];
//            
//            for (int i=0; i<pageNum; i++) {
//                vo.setPageNum(i+1);
//                List<TranstFlowBean> beans = flowService.search(vo);
//                for (TranstFlowBean bean: beans) {
//                    cs[0] = bean.getFromDeviceName();
//                    cs[1] = bean.getToDeviceName();
//                    cs[2] = String.valueOf(bean.getLogTypeID());
//                    cs[3] = bean.getChannelType();
//                    cs[4] = bean.getImsi();
//                    cs[5] = bean.getImei();
//                    cs[6] = String.valueOf(bean.getPhone());
//                    strings.add(Arrays.copyOf(cs,cs.length));
//                }
//                beans.clear();
//                beans = null;
//            }
//            
//            new ExcelBundle().downloadExcel(strings, "翻译传输流程", request, response);
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    
  @RequestMapping(value="/exportExcel", method=RequestMethod.GET)  
  public void exportExcel(TranstFlowVO vo, HttpServletRequest request, HttpServletResponse response) {  
    try {
        int pageSize = 60000; //每页显示数
        int pageNum;
        Integer totalCount = flowService.searchNum(vo);
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
            List<TranstFlowBean> beans = flowService.search(vo);
            beans.stream().map((bean) -> {
                //重置内容
                Map<Integer, Object> data = new HashMap<>();
                data.put(1, bean.getFromDeviceName());
                data.put(2, bean.getToDeviceName());
                data.put(3, bean.getLogTypeID());
                data.put(4, bean.getChannelType());
                data.put(5, bean.getImsi());
                data.put(6, bean.getImei());
                data.put(7, bean.getPhone());
                data.put(8,bean.getRemark());
                return data;
            }).forEachOrdered((data) -> {
                datalist.add(data);
            });
            beans.clear();
        }
        String tempFilePath = TranstFlowController.class.getResource("/template/transt_flow.xlsx").toURI().getPath();
        System.out.println(tempFilePath);
        // 必须为列表头部所有位置集合， 输出数据单元格样式和头部单元格样式保持一致
        String[] heads = new String[]{"A1", "B1", "C1", "D1", "E1", "F1", "G1", "H1"};
        new ExcelBundle().downloadExcel(tempFilePath, "翻译传输流程表", heads, datalist, request, response);
    } catch (URISyntaxException e) {
        logger.error(e.getMessage());
    }
}
    
    @RequestMapping("/deleteBatch/{IDs}")
    public ModelAndView deleteBatch(HttpServletResponse response,HttpServletRequest request) {
        String [] ids = request.getParameter("ids").split(",");
        for (String id: ids) {
            flowService.delete(Integer.parseInt(id));
        }
        
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }
}
