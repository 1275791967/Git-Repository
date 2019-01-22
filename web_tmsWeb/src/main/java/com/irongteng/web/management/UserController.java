package com.irongteng.web.management;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.irongteng.annotation.Permission;
import com.irongteng.persistence.BaseConditionVO;
import com.irongteng.service.Role;
import com.irongteng.service.UserBean;
import com.irongteng.service.UserService;
import com.irongteng.service.UserStatus;
import com.irongteng.web.BaseController;

import dwz.common.util.encrypt.SpringEncryptUtil;
import dwz.framework.config.Constants;
import dwz.framework.enums.Gender;
import dwz.framework.sys.exception.ServiceException;

@Controller("management.userController")
@RequestMapping(value="/management/user")
public class UserController extends BaseController{
    @Autowired
    private UserService userMgr;

    @RequestMapping("")
    @Permission(Role.ADMIN_ROLE)
    public String list(BaseConditionVO vo, Model model) {
        List<UserBean> userList = userMgr.search(vo);
        Integer totalCount = userMgr.searchNum(vo);
        vo.setTotalCount(totalCount);
        
        model.addAttribute("userList", userList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("userStatusList", UserStatus.values());
        model.addAttribute("pageSize", vo.getPageSize());
        model.addAttribute("vo", vo);
        
        return "/management/user/list";
    }
    
    /**
     * 退出登录
     * @param request
     * @return
     */
    @RequestMapping("/logout")
    @Permission
    public ModelAndView logout(HttpServletRequest request) {

        request.getSession().removeAttribute(Constants.AUTHENTICATION_KEY);
        request.getSession().invalidate();
        return  new ModelAndView("login");
    }
    
    /**
     * 打开添加用户窗口
     * @param model
     * @return
     */
    @RequestMapping("/add")
    @Permission(Role.ADMIN_ROLE)
    public String add(Model model) {
        model.addAttribute("genderList", Gender.values());
        return "/management/user/add";
    }
    
    /**
     * 打开编辑用户窗口
     * @param model
     * @return
     */
    @RequestMapping("/edit/{userId}")
    @Permission(Role.ADMIN_ROLE)
    public String edit(@PathVariable("userId") int userId, Model model) {
        UserBean user = userMgr.get(userId);

        model.addAttribute("vo", user);
        model.addAttribute("genderList", Gender.values());
        
        return "/management/user/edit";
    }
    
    /**
     * 打开修改密码窗口
     * @param userID
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/editPwdDialog/{userID}")
    @Permission
    public String editPasswordDialog(@PathVariable("userID") Integer userID, Model model, HttpServletRequest request) {
        
        UserBean user = (UserBean) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
        
        model.addAttribute("userID", user.getId());
        
        return "/management/editPassword";
    }
    
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @Permission(Role.ADMIN_ROLE)
    public ModelAndView insert(UserBean user) {
        try {
            String encPassword = SpringEncryptUtil.encrypt(user.getUsername().trim(), user.getPassword().trim());
            user.setPassword(encPassword);
            userMgr.add(user);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }
        
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Permission(Role.ADMIN_ROLE)
    public ModelAndView update(UserBean userBean, HttpServletRequest request) {
        
        try {
            if (userBean.getPassword()!=null && !"".equals(userBean.getPassword().trim())) {
                userBean.setPassword(SpringEncryptUtil.encrypt(userBean.getUsername().trim(), userBean.getPassword().trim()));
            }
            userMgr.update(userBean);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }
    
    /**
     * 提交密码修改请求
     * @param userBean
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/updPwd", method = RequestMethod.POST)
    @Permission
    public ModelAndView updatePassword(UserBean userBean, Model model, HttpServletRequest request) {
        try {
            UserBean user = (UserBean) request.getSession().getAttribute(Constants.AUTHENTICATION_KEY);
            if (!SpringEncryptUtil.match(user.getUsername(), userBean.getPassword(), user.getPassword())) {
                return ajaxDoneError(getMessage("msg.oldpassword.invalid"));
            }
            //密钥加密
            String encPassword = SpringEncryptUtil.encrypt(user.getUsername(), userBean.getNewPassword());
            user.setPassword(encPassword);
            //更新session密钥信息
            request.getSession().setAttribute(Constants.AUTHENTICATION_KEY, user);
            
            userBean.setPassword(encPassword);
            userMgr.update(userBean);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }
    
    @RequestMapping("/delete/{userId}")
    @Permission(Role.ADMIN_ROLE)
    public ModelAndView delete(@PathVariable("userId") int userId) {

        userMgr.delete(userId);

        return ajaxDoneSuccess(getMessage("msg.operation.success"));
    }
}
