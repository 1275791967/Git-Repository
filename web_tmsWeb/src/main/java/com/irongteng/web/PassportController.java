package com.irongteng.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.irongteng.annotation.Permission;
import com.irongteng.conf.GlobalConfig;
import com.irongteng.license.ReadLicense;
import com.irongteng.service.UserBean;
import com.irongteng.service.UserService;
import com.irongteng.service.UserStatus;
import dwz.common.util.InetUtils;

import dwz.common.util.encrypt.SpringEncryptUtil;
import dwz.framework.config.Constants;
import dwz.framework.sys.exception.ServiceException;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/passport")
public class PassportController extends BaseController{
    
    @Autowired
    private UserService userMgr;
    /**
     * 对用户登录信息进行处理，并跳转到相应页面
     * @param request
     * @param redirectAttributes
     * @return
     */
    @RequestMapping("/login")
    public String login(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            if (ReadLicense.isVerify()) {
                boolean result = checkUser(request);
                //授权设置
                if (result) {
                    String backToUrl = request.getParameter("backToUrl");
                    if (backToUrl == null || backToUrl.trim().length() == 0) {
                        backToUrl = "/management";
                    } else {
                        try {
                            backToUrl = URLDecoder.decode(backToUrl, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                    //切换到管理界面
                    return "redirect:"+backToUrl;
                }
                throw new ServiceException(getMessage("msg.login.failure")); //用户名或密码错误。
                //return new ModelAndView("login", "error", "用户名或密码错误。");
            } else {
                throw new ServiceException(getMessage("msg.verify.failure"));
                //return new ModelAndView("login", "error", "该系统没有获取授权，无法登录。");
            }
        } catch (ServiceException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            //return new ModelAndView("redirect:/", model);
            return "redirect:/";
            //return ajaxDoneError(e.getMessage());
        }
        //return new ModelAndView("login", "error", "用户名或密码错误。");
    }
    
    /**
     * 对用户登录信息进行处理，并跳转到相应页面
     * @param request
     * @return
     */
    @RequestMapping(value="/login_dialog_form", method = RequestMethod.POST)
    public ModelAndView loginDialogForm(HttpServletRequest request) {
        try {
            if (ReadLicense.isVerify()) {
                boolean result = checkUser(request);
                //授权设置
                if (result) {
                    HttpSession session = request.getSession();
                    boolean isOnline = InetUtils.isOnline();
                    session.setAttribute("isOnline", isOnline);
                    //初始化全局变量
                    //new InitGlobalParam(request).start();
                    GlobalConfig gConfig = new GlobalConfig();
                    //wifi开关
                    session.setAttribute("isOpenWiFi", gConfig.isWifiSwitch());
                    //参数查询设置开关
                    session.setAttribute("isParameterSwitch", gConfig.isParameterSwitch());
                    //参数查询设置开关
                    session.setAttribute("isCallerlocSwitch", gConfig.isCallerlocSwitch());
                    
                    return ajaxDoneSuccess(getMessage("msg.login.success"));
                }
                throw new ServiceException(getMessage("msg.login.failure")); //用户名或密码错误。
                //return new ModelAndView("login", "error", "用户名或密码错误。");
            } else {
                throw new ServiceException(getMessage("msg.verify.failure"));
                //return new ModelAndView("login", "error", "该系统没有获取授权，无法登录。");
            }
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }
        //return new ModelAndView("login", "error", "用户名或密码错误。");
    }
    
    /**
     * 打开重新登陆弹出框
     * @return
     */
    @RequestMapping("/login_dialog")
    public String loginDialog() {
        return "/management/loginDialog";
    }

    /**
     * 检查用户登录信息是否合法
     * @param request
     * @return
     */
    private boolean checkUser(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserBean user = userMgr.getByUsername(username);
        //授权设置
        if (user != null && UserStatus.ACTIVE.equals(user.getStatus())
                && password!=null && SpringEncryptUtil.match(username, password, user.getPassword())) {
            HttpSession session = request.getSession();
            
            session.setAttribute(Constants.AUTHENTICATION_KEY, user);
            
            //用户角色
            session.setAttribute("role",user.getRole());
            return true;
        }
        return false;
    }
    
    /**
     * 退出登录
     * 
     * @param request
     * @return
     */
    @RequestMapping("/logout")
    @Permission
    public ModelAndView logout(HttpServletRequest request) {

        request.getSession().removeAttribute(Constants.AUTHENTICATION_KEY);
        
        return  new ModelAndView("login");
    }
    
}
