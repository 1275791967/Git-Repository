<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="<c:url value='/management/customer_account/update?navTabId=CustomerAccountLiNav&callbackType=closeCurrent'/>" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
    <input type="hidden" name="id" value="${bean.id}"/>
    <div class="pageFormContent" layoutH="57">
        <dl>
            <dt>账户名: </dt>
            <dd>
                <input type="text" name="name" value="${bean.name}"  readonly="readonly" minlength="1" maxlength="20"/>
            </dd>
        </dl>
         <dl>
            <dt>本地余额: </dt>
            <dd>
                <input type="text" name="localBalance" value="${bean.localBalance}" class="digits" minlength="1" maxlength="20"/>
            </dd>
        </dl>
        <dl>
            <dt>远程余额: </dt>
            <dd>
                <input type="text" name="thirdBalance" value="${bean.thirdBalance}" class="digits" minlength="1" maxlength="20"/>
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
