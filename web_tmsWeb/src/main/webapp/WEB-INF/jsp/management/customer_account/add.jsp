<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="<c:url value='/management/customer_account/insert?navTabId=CustomerAccountLiNav&callbackType=closeCurrent'/>" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
    <div class="pageFormContent" layoutH="57">
        <dl>
            <dt>账户名: </dt>
            <dd>
                <input type="text" name="Name" value="${bean.name}" class="required" minlength="1" maxlength="20"/>
            </dd>
        </dl>
        
        <dl>
            <dt>密码: </dt>
            <dd>
                <input type="text" name="Psw" value="${bean.psw}" class="required" minlength="6" maxlength="20"/>
            </dd>
        </dl>
        <dl>
            <dt>设备名称: </dt>
            <dd>
<%--            <input type="text" name="DeviceName" value="${bean.deviceName}" class="required" minlength="1" maxlength="20"/> --%>

                <input type="hidden" name="DeviceID" bringbackname="org.id" value="${beans.deviceID}" >
                <input type="text" readonly="readonly"  name="DeviceName" bringbackname="org.deviceName" value="${beans.deviceName}" autocomplete="off" lookupgroup="org" suggesturl="<c:url value='/management/device/lookupSuggest'/>" suggestfields="deviceName" postfield="keywords" lookuppk="id" class="required textInput" />
                <a class="btnLook" href="<c:url value='/management/device/lookup'/>" lookupGroup="org" suggestfields="deviceName" autocomplete="off" postfield="keywords" lookuppk="id">查找带回</a>
            </dd>
        </dl>
        
<!--         <li>   -->
<!--                 <label>设备名称：</label> -->
<%--                 <input type="hidden" name="DeviceID" bringbackname="org.id" value="${vo.deviceID}" > --%>
<%--                 <input type="text"  name="DeviceName" bringbackname="org.deviceName" value="${vo.deviceName}" autocomplete="off" lookupgroup="org" suggesturl="<c:url value='/management/device/lookupSuggest'/>" suggestfields="deviceName" postfield="keywords" lookuppk="id" /> --%>
<%--                 <a class="btnLook" href="<c:url value='/management/device/lookup'/>" lookupGroup="org" suggestfields="deviceName" autocomplete="off" postfield="keywords" lookuppk="id">查找带回</a> --%>
<!--             </li>  -->
        
        <dl>
            <dt>联系方式: </dt>
            <dd>
                <input type="text" name="phone" value="${bean.phone}" class="digits" minlength="8" maxlength="20"/>
            </dd>
        </dl>
        <%--新增内容 --%>
      <dl>
            <dt>本地余额: </dt>
            <dd>
                <input type="text"  name="localBalance" value="0"  minlength="1" maxlength="200"/>
            </dd>
        </dl>
        <dl>
            <dt>远程余额: </dt>
            <dd>
            <input type="text"  name="thirdBalance" value="0"  minlength="1" maxlength="40"/>
<%--                 <input type="text"  disabled="disabled"name="thirdBalance" value="${bean.thirdBalance}"  minlength="1" maxlength="40"/> --%>
            </dd>
        </dl>
        <%--新增部分 --%>
        <dl>
            <dt>状态: </dt>
            <dd>
                <select class="combox" name="Status">
                    <option value="">全部</option>
                    <option value="ACTIVE" ${'ACTIVE' eq bean.status ? 'selected="selected"' : ''}>激活</option>
                    <option value="DISABLE" ${'DISABLE' eq bean.status ? 'selected="selected"' : ''}>未激活</option>
                    
                    <%-- <c:forEach var="item" items="${devBeans}">
                         <option value="${item.id}" ${item.id eq bean.deviceID ? 'selected="selected"' : ''}>${item.deviceName}</option>
                        <option value="active"></option>
                        <option value="inactive"></option>
                    </c:forEach>
                    --%>
                </select>
                <%-- <input type="text" name="city" value="${bean.status}" class="required" maxlength="30"/>--%>
            </dd>
        </dl>
        
        
       
        
        <%--新增内容 --%>
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
