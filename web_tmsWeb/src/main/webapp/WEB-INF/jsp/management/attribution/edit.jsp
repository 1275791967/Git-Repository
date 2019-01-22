<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/management/book/edit/${book.id}">
    <input type="hidden" name="pageNum" value="1" />
</form>

<div class="pageContent">
<form method="post" action="<c:url value='/management/attribution/update?navTabId=AttributionLiNav&callbackType=closeCurrent'/>" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">    
    <input type="hidden" name="id" value="${bean.id}"/>
    
    <div class="pageFormContent" layoutH="57">
        <dl>
            <dt>号码片段(前7位): </dt>
            <dd>
                <input type="text" name="phoneNO" value="${bean.phoneNO}" class="required digits" minlength="4" maxlength="200"/>
            </dd>
        </dl>
        <dl>
            <dt>所属省: </dt>
            <dd>
                <input type="text" name="province" value="${bean.province}" class="required" minlength="2" maxlength="40"/>
            </dd>
        </dl>
        
        <dl>
            <dt>所属市: </dt>
            <dd>
                <input type="text" name="city" value="${bean.city}" class="required" minlength="2" maxlength="30"/>
            </dd>
        </dl>
        <dl>
            <dt>号码前缀: </dt>
            <dd>
                <input type="text" name="cellNO" value="${bean.cellNO}" class="digits" maxlength="30"/>
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