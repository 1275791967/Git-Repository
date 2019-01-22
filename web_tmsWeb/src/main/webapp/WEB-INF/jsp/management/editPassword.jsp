<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<div class="pageContent">
<form method="post" action="<c:url value='/management/user/updPwd?callbackType=closeCurrent'/>" class="required-validate pageForm" onsubmit="return validateCallback(this, dialogAjaxDone);">
    
    <input type="hidden" name="id" value="${userID}" />
    
    <div class="pageFormContent" layoutH="57">
        <p>
            <label>
                <%-- <s:text name="form.pwd"/> --%>
                <fmt:message key="form.pwd"/>
            </label>
            <input type="password" name="password" class="required"/>
        </p>
        <p>
            <label>
                <%-- <s:text name="form.newPwd"/> --%>
                <fmt:message key="form.newPwd"/>
            </label><input type="password" name="newPassword" id="newPassword" class="required"/>
        </p>
        <p>
            <label>
                <%-- <s:text name="form.confirmPwd"/> --%>
                <fmt:message key="form.confirmPwd"/>
            </label><input type="password" name="rPassword" class="required" equalTo="#newPassword"/>
        </p>
    </div>
    <div class="formBar">
        <ul>
            <li><div class="buttonActive"><div class="buttonContent"><button type="submit"><fmt:message key="button.save"/></button></div></div></li>
            <li><div class="button"><div class="buttonContent"><button type="button" class="close" ><fmt:message key="button.close"/></button></div></div></li>
        </ul>
    </div>
</form>
</div>