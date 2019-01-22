<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.inc.jsp"%>
<div class="pageContent">
<form method="post" action="<c:url value='/management/imsi_phone/insert?navTabId=IMSI_PhoneLiNav&callbackType=closeCurrent'/>" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
    <div class="pageFormContent" layoutH="57">
        <dl>
            <dt>IMSI: </dt>
            <dd>
                <input type="text" name="imsi" value="${vo.imsi}" class="required digits" minlength="4" maxlength="200"/>
            </dd>
        </dl>
        <dl>
            <dt>手机号码: </dt>
            <dd>
                <input type="text" name="phone" value="${vo.phone}" class="required digits" minlength="4" maxlength="200"/>
            </dd>
        </dl>
<!--         <dl> -->
<!--             <dt>IMEI: </dt> -->
<!--             <dd> -->
<%--                 <input type="text" name="imei" value="${vo.imei}" class="required digits" minlength="4" maxlength="200"/> --%>
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

    </div>
    <div class="formBar">
        <ul>
            <li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
            <li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
        </ul>
    </div>
</form>
</div>
