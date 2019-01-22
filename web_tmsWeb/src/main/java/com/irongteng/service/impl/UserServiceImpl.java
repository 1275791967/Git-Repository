package com.irongteng.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irongteng.persistence.BaseConditionVO;
import com.irongteng.persistence.beans.User;
import com.irongteng.persistence.mapper.UserMapper;
import com.irongteng.service.UserBean;
import com.irongteng.service.UserService;
import com.irongteng.service.UserStatus;

import dwz.common.util.encrypt.DesEncryptUtil;
import dwz.framework.sys.business.AbstractBusinessObjectServiceMgr;
import dwz.framework.sys.exception.ServiceException;

@Transactional(rollbackFor = Exception.class)
@Service(UserService.SERVICE_NAME)
public class UserServiceImpl extends AbstractBusinessObjectServiceMgr
        implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    private boolean isUniqueUsername(UserBean user) {
        Integer id = user.getId() != null ? user.getId() : 0;
        return userMapper.isUniqueUsername(id, user.getUsername()) < 1;
    }

    @Override
    public UserBean get(int id) {
        User user = userMapper.load(id);
        if (user == null) return null;
        
        //user.setPassword(DesEncryptUtil.decrypt(user.getPassword()));
        return new UserBean(user);
    }

    @Override
    public UserBean getByUsername(String username) {
        User user = userMapper.findByUsername(username);
        if (user == null) return null;
        
        String password = DesEncryptUtil.decrypt(user.getPassword());
        user.setPassword(password);
        return new UserBean(user);
    }
    
    @Override
    public Integer add(UserBean userBean) throws ServiceException{
        if (!isUniqueUsername(userBean)) {
            throw new ServiceException(getMessage("msg.username.unique"));
        }
        
        Date now = new Date();
        User user = userBean.getUser();
        user.setPassword(DesEncryptUtil.encrypt(userBean.getPassword()));
        user.setInsertDate(now);
        user.setUpdateDate(now);
        user.setStatus(UserStatus.ACTIVE.toString());
        
        userMapper.insert(user);
        return user.getId();
    }
    
    @Override
    public void update(UserBean userBean) throws ServiceException {
        if (!isUniqueUsername(userBean)) {
            throw new ServiceException(getMessage("msg.username.unique"));
        }

        if(userBean.getPassword()!=null && !"".equals(userBean.getPassword().trim())) {
            userBean.setPassword(DesEncryptUtil.encrypt(userBean.getPassword()));
        }
        Date now = new Date();
        User user = userBean.getUser();
        user.setUpdateDate(now);
        
        userMapper.updateSelective(user);
    }

    @Override
    public void delete(int id) {
        userMapper.updateStatus(id, UserStatus.DELETED.toString(), new Date());
    }

    @Override
    public List<UserBean> search(BaseConditionVO vo) {
        List<UserBean> beans = new ArrayList<>();
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<User> models = userMapper.findPageBreakByCondition(vo, rb);
        models.stream().forEach((model) -> {
            beans.add(new UserBean(model));
        });
        return beans;
    }

    @Override
    public Integer searchNum(BaseConditionVO vo) {
        Integer count = userMapper.findNumberByCondition(vo);

        return count;
    }

    @Override
    public void active(int id) {
        userMapper.updateStatus(id, UserStatus.ACTIVE.toString(), new Date());
    }

    @Override
    public void inActive(int id) {
        userMapper.updateStatus(id, UserStatus.INACTIVE.toString(), new Date());
    }
}
