<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.inc.jsp"%>
<%-- <form id="pagerForm" method="post" action="/management/book/edit/${book.id}"> --%>
<!--     <input type="hidden" name="pageNum" value="1" /> -->
<!-- </form> -->
<div class="pageContent">
<form method="post" action="<c:url value='/management/opInfo/update?navTabId=OpInfoLiNav&callbackType=closeCurrent'/>" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
    <div class="pageFormContent" layoutH="57">
    <input type="hidden" name="id" value="${bean.id}"/>    
        <dl>
            <dt>运营商编号: </dt>
            <dd>
                <input type="text" name="opNo" value="${bean.opNo}" class="required " readonly="readonly" minlength="1" maxlength="20"/>
            </dd>
        </dl> 
        <dl>
            <dt>运营商名称: </dt>
            <dd>
                <input type="text" name="opName" value="${bean.opName}" class="required" minlength="1" maxlength="20"/>
            </dd>
        </dl>  
       <%--  <dl>
            <dt>创建时间: </dt>
            <dd>
                <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${bean.createTime}"/>
                <input type="text" name="createTime" value="${bean.createTime}"  minlength="8" maxlength="20"/>
            </dd>
        </dl>  
         <dl>         
            <dt>更新时间: </dt>
            <dd>
                <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${bean.updateTime}"/>
                <input type="text" name="updateTime" value="${bean.updateTime}"  minlength="8" maxlength="20"/>
            </dd>
        </dl>          --%>
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