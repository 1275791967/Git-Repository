<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="<c:url value='/management/user/update?navTabId=userLiNav&callbackType=closeCurrent'/>" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
    <input type="hidden" name="id" value="${bean.id}"/>
    <div class="pageFormContent" layoutH="57">
        <p>
            <label>用户名: </label>
            <input type="text" name="username" value="${vo.username}" readonly="readonly"/>
        </p>
        <p>
            <label>密码: </label>
            <input type="text" name="password" value="${vo.password}" class="required alphanumeric" minlength="6" maxlength="20"/>
        </p>
        <p>
            <label>用户权限: </label>
            <select name="role" class="combox">
                <option value="ADMIN_ROLE" ${'ADMIN_ROLE' eq vo.role ? 'selected="selected"' : ''}>管理员</option>
                <option value="MANAGER_ROLE" ${'MANAGER_ROLE' eq vo.role ? 'selected="selected"' : ''}>管理者</option>
                <option value="USER_ROLE" ${'USER_ROLE' eq vo.role ? 'selected="selected"' : ''}>一般用户</option>
            </select>
        </p>
        <p>
            <label>Email: </label>
            <input type="text" name="email" value="${vo.email}" class="required email" maxlength="100"/>
        </p>
        <p>
            <label>电话: </label>
            <input type="text" name="phone" value="${vo.phone}" maxlength="30"/>
        </p>
        <p>
            <label>地址: </label>
            <input type="text" name="address" value="${vo.address}" maxlength="30"/>
        </p>
        <p>
            <label>创建时间: </label>
            <fmt:formatDate value="${vo.insertDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
        </p>
        <p>
            <label>修改时间: </label>
            <fmt:formatDate value="${vo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
        </p>
    </div>

    <div class="formBar">
        <ul>
            <li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
            <li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
        </ul>
    </div>
</form>
</div>