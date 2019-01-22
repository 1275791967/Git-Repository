<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<div class="pageContent">
    
    <form method="post" action="<c:url value='/passport/login_dialog_form?callbackType=closeCurrent'/>" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">

        <div class="pageFormContent" layoutH="57">
            <p>
                <label><fmt:message key="form.username"/>：</label>
                <input type="text" name="username" size="20" class="required login_input" />
            </p>
            <p>
                <label><fmt:message key="form.pwd"/>：</label>
                <input type="password" name="password" size="20" class="required login_input" />
            </p>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit"><fmt:message key="button.login"/></button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button type="button" class="close"><fmt:message key="button.close"/></button></div></div></li>
            </ul>
        </div>
    </form>
    
</div>
