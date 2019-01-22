<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.inc.jsp"%>
<form id="pagerForm" method="post" action="/management/book/edit/${book.id}">
    <input type="hidden" name="pageNum" value="1" />
</form>

<div class="pageContent">
<form method="post" action="<c:url value='/management/imsi_phone/update?navTabId=IMSI_PhoneLiNav&callbackType=closeCurrent'/>" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
    
    <input type="hidden" name="id" value="${vo.id}"/>
    
    <div class="pageFormContent" layoutH="57">
        <dl>
            <dt>IMSI: </dt>
            <dd>
                <input type="text" readonly="readonly" name="imsi" value="${vo.imsi}" class=" digits" minlength="4" maxlength="200"/>
            </dd>
        </dl>
        <dl>
            <dt>手机号码: </dt>
            <dd>
                <input type="text" readonly="readonly" name="phone" value="${vo.phone}" class=" digits" minlength="4" maxlength="200"/>
            </dd>
        </dl>
<!--         <dl> -->
<!--             <dt>IMEI: </dt> -->
<!--             <dd> -->
<%--                 <input type="text" readonly="readonly" name="imei" value="${vo.imei}" class=" digits" minlength="4" maxlength="200"/> --%>
<!--             </dd> -->
<!--         </dl> -->
<!--         <dl> -->
<!--             <dt>MAC: </dt> -->
<!--             <dd> -->
<%--                 <input type="text" name="mac" value="${vo.mac}" class="required digits" minlength="4" maxlength="200"/> --%>
<!--             </dd> -->
<!--         </dl> -->
<!--         <dl> -->
<!--             <dt>imsi_MSISDN: </dt> -->
<!--             <dd> -->
<%--                 <input type="text" name="imsi_MSISDN" value="${vo.imsi_MSISDN}" class="required digits" minlength="4" maxlength="200"/> --%>
<!--             </dd> -->
<!--         </dl> -->
        
        
        
        <div class="divider"></div>
        <dl>
            <dt>详情：</dt>
            <dd>
                <textarea name="remark" rows="6" cols="50">${vo.remark}</textarea>
            </dd>
        </dl>
        <%-- 
        <dl>
            <dt>所属省: </dt>
            <dd>
                <input type="text" name="province" value="${vo.province}" class="required" minlength="2" maxlength="40"/>
            </dd>
        </dl>
        
        <dl>
            <dt>所属市: </dt>
            <dd>
                <input type="text" name="city" value="${vo.city}" class="required" minlength="2" maxlength="30"/>
            </dd>
        </dl>
        <div class="divider"></div>
        <dl>
            <dt>详情：</dt>
            <dd>
                <textarea name="remark" rows="6" cols="50">${vo.remark}</textarea>
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