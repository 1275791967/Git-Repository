<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="<c:url value='/management/device/insert?navTabId=DeviceLiNav&callbackType=closeCurrent'/>" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
    <div class="pageFormContent" layoutH="57">
<%--     <input type="hidden" name="id" value="${bean.id}"/>   --%>
<%--     <input type="hidden" name="customerID" value="${bean.customerID}"/>  --%>
        <dl>
            <dt>设备名称: </dt>
            <dd>
                <input type="text" name="deviceName" value="${bean.deviceName}" class="required" minlength="2" maxlength="20"/>
            </dd>
        </dl>  
        <dl>
            <dt>客户名: </dt>
                <dd>
                    <input type="hidden" name="CustomerID" bringbackname="org.id" value="${vo.customerID}" /> 
                    <input type="text" readonly="readonly" name="CustomerName" bringbackname="org.customerName" value="${vo.customerName}" autocomplete="off" lookupgroup="org" suggesturl="<c:url value='/management/customer/lookupSuggest'/>" suggestfields="customerName" postfield="keywords" lookuppk="id" class="required textInput"  />
                    <a class="btnLook" href="<c:url value='/management/customer/lookup'/>" lookupGroup="org" suggestfields="customerName" autocomplete="off" postfield="keywords" lookuppk="id" title="查找带回">查找带回</a>
                </dd>            
       </dl>      
        <dl>
            <dt>设备类型: </dt>
            <dd>
<%--            <input type="text" name="deviceType" value="${bean.deviceType}" class="required digits" minlength="1" maxlength="20"/> --%>
                <%-- <select class="combox" name="deviceType">
                    <option value="" ${empty bean.deviceType ? 'selected="selected"' : ''}>全部</option>
                    <option value="0" ${bean.deviceType == 0 ? 'selected="selected"' : ''}>翻译前端</option>
                    <option value="1" ${bean.deviceType == 1 ? 'selected="selected"' : ''}>翻译服务器</option>
                    <option value="2" ${bean.deviceType == 2 ? 'selected="selected"' : ''}>手机客户端</option>
                </select> --%>
                <select class="combox" name="deviceType">
                    <option value="">全部</option>
                    <c:forEach var="item" items="${deviceType}">
                        <option value="${item.key}" ${'item.key' eq bean.deviceType ? 'selected="selected"' : ''}>${item.value}</option>
                    </c:forEach>
                </select>
            </dd>
        </dl>
        <dl>
            <dt>特征码/IMEI: </dt>
            <dd>
                <input type="text" name="featureCode" value="${bean.featureCode}" class="required" minlength="2" maxlength="20"/>
            </dd>
        </dl>
        <dl>
            <dt>经度: </dt>
            <dd>
                <input type="text" name="longitude" value="${bean.longitude}"  minlength="2" maxlength="20"/>
            </dd>
        </dl>
        <dl>
            <dt>纬度: </dt>
            <dd>
                <input type="text" name="latitude" value="${bean.latitude}"  minlength="2" maxlength="20"/>
            </dd>
        </dl>
         <dl>
            <dt>地址: </dt>
            <dd>
                <input type="text" name="address" value="${bean.address}"  minlength="2" maxlength="20"/>
            </dd>
        </dl>
<!--         <dl> -->
<!--             <dt>更新时间: </dt> -->
<!--             <dd> -->
<%--                 <input type="text" name="createTime" value="${bean.createTime}"  minlength="8" maxlength="20"/> --%>
<!--             </dd> -->
<!--         </dl> -->         
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
