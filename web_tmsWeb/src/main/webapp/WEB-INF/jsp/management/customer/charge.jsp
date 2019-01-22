<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="<c:url value='/management/customer/insert?navTabId=customerLiNav&callbackType=closeCurrent'/>" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
    <div class="pageFormContent" layoutH="57">
        <dl>
            <dt>姓名: </dt>
            <dd>
                <input type="text" name="CustomerName" value="${bean.customerName}" class="required" minlength="2" maxlength="20"/>
            </dd>
        </dl>
        <dl>
            <dt>联系方式: </dt>
            <dd>
                <input type="text" name="contact" value="${bean.contact}" class="required digits" minlength="8" maxlength="20"/>
            </dd>
        </dl>
        <dl>
            <dt>地址: </dt>
            <dd>
                <input type="text" name="address" value="${bean.address}" class="required" minlength="8" maxlength="20"/>
            </dd>
        </dl>
         <dl>
            <dt>创建时间: </dt>
            <dd>
                <fmt:formatDate value="${bean.createTime}" pattern="yyyy-mm-dd HH:mm:ss"/>
<%--                 <input type="text" name="createTime" value="${bean.createTime}"  minlength="8" maxlength="20"/> --%>
            </dd>
        </dl>
        <dl>
            <dt>更新时间: </dt>
            <dd>
                <fmt:formatDate value="${bean.updateTime}" pattern="yyyy-mm-dd HH:mm:ss"/>
<%--                 <input type="text" name="updateTime" value="${bean.updateTime}"  minlength="8" maxlength="20"/> --%>
            </dd>
        </dl>
         
        <div class="divider"></div>
        <dl>
            <dt>详情：</dt>
            <dd>
                <textarea name="remark" rows="6" cols="50">${bean.remark}</textarea>
            </dd>
        </dl>
    </div>
    <div class="formBar">
        <ul>
            <li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
            <li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
        </ul>
    </div>
</form>
</div>
