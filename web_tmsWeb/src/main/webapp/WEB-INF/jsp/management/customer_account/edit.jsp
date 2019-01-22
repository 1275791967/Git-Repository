<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/management/book/edit/${book.id}">
    <input type="hidden" name="pageNum" value="1" />
</form>

<div class="pageContent">
<form method="post" action="<c:url value='/management/customer_account/update?navTabId=CustomerAccountLiNav&callbackType=closeCurrent'/>" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
    
    <input type="hidden" name="id" value="${bean.id}"/>
    
    <div class="pageFormContent" layoutH="57">
      <%--   <dl>
            <dt>IMSI: </dt>
            <dd>
                <input type="text" name="imsi" value="${call.imsi}" class="required digits" minlength="4" maxlength="200"/>
            </dd>
        </dl>
        <dl>
        --%>
        <dl>
            <dt>账户名: </dt>
            <dd>
                <input type="text" name="name" value="${bean.name}"  readonly="readonly" minlength="1" maxlength="20"/>
            </dd>
        </dl>
       
        <%--新增内容 --%>
        
        
        <dl>
            <dt>密码: </dt>
            <dd>
                <input type="text" name="Psw" value="${bean.psw}"  minlength="6" maxlength="20"/>
            </dd>
        </dl>
        <dl>
            <dt>设备名称: </dt>
            <dd>
                <input type="hidden" name="DeviceID" bringbackname="org.id" value="${bean.deviceID}" >
                <input type="text" readonly="readonly"  name="DeviceName" bringbackname="org.deviceName" value="${bean.deviceName}" autocomplete="off" lookupgroup="org" suggesturl="<c:url value='/management/device/lookupSuggest'/>" suggestfields="deviceName" postfield="keywords" lookuppk="id" class="required textInput" />
                <a class="btnLook" href="<c:url value='/management/device/lookup'/>" lookupGroup="org" suggestfields="deviceName" autocomplete="off" postfield="keywords" lookuppk="id">查找带回</a>            
<%--            <input type="text" readonly="readonly" name="DeviceName" value="${bean.deviceName}"  minlength="1" maxlength="20"/> --%>
            </dd>
        </dl>
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
                <input type="text" readonly="readonly"  name="localBalance" value="${bean.localBalance}" class="digits" minlength="1" maxlength="200"/>
            </dd>
        </dl>
        <dl>
            <dt>远程余额: </dt>
            <dd>
                <input type="text" readonly="readonly"  name="thirdBalance" value="${bean.thirdBalance}" class="digits" minlength="1" maxlength="40"/>
            </dd>
        </dl>
        
        <dl>
            <dt>状态: </dt>
            <dd>
                <select class="combox" name="Status">
                    <option value="">全部</option>
                    <option value="ACTIVE" ${'ACTIVE' eq bean.status ? 'selected="selected"' : ''}>激活</option>
                    <option value="DISABLE" ${'DISABLE' eq bean.status ? 'selected="selected"' : ''}>未激活</option>
                    
                    <%-- <c:forEach var="item" items="${devBeans}">
                         <option value="${item.id}" ${item.id eq call.deviceID ? 'selected="selected"' : ''}>${item.deviceName}</option>
                        <option value="active"></option>
                        <option value="inactive"></option>
                    </c:forEach>
                    --%>
                </select>
               
            </dd>
            
            <%-- 
            <dd>
                <input type="text" name="city" value="${call.status}" class="required" maxlength="30"/>
            </dd>
            --%>
        </dl>
   
        <div class="divider"></div>
        <dl>
            <dt>详情：</dt>
            <dd>
                <textarea name="remark" rows="6" cols="50">${bean.remark}</textarea>
            </dd>
        </dl>
        
        <%-- 
        <dl>
            <dt>所属省: </dt>
            <dd>
                <input type="text" name="province" value="${call.province}" class="required" minlength="2" maxlength="40"/>
            </dd>
        </dl>
        
        <dl>
            <dt>所属市: </dt>
            <dd>
                <input type="text" name="city" value="${call.city}" class="required" minlength="2" maxlength="30"/>
            </dd>
        </dl>
        <div class="divider"></div>
        <dl>
            <dt>详情：</dt>
            <dd>
                <textarea name="remark" rows="6" cols="50">${call.remark}</textarea>
            </dd>
        </dl>
         --%>
    </div>

    <div class="formBar">
        <ul>
            <li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
            <li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
        </ul>
    </div>
</form>
</div>