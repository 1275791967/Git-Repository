<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>
<div class="pageContent">
    <form method="post" action="<c:url value='/management/customer/importExcel?navTabId=customerLiNav&callbackType=closeCurrent'/>" enctype="multipart/form-data" class="pageForm required-validate" onsubmit="return iframeCallback(this, dialogAjaxDone);">
        <div class="pageFormContent" layoutH="56">
            <div class="unit">
                <label>请选择导入文件：</label>
                <ul id="upload-preview" class="upload-preview"></ul>
                <div class="upload-wrap" rel="#upload-preview">
                    <input type="file" name="file2" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel" multiple="multiple">
                </div>
            </div>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
            </ul>
        </div>
    </form>
</div>
<%-- 
<h2 class="contentTitle">IMSI和手机号码导入</h2>

<style type="text/css" media="screen">
.my-uploadify-button {
    background:none;
    border: none;
    text-shadow: none;
    border-radius:0;
}

.uploadify:hover .my-uploadify-button {
    background:none;
    border: none;
}

.fileQueue {
    width: 400px;
    height: 150px;
    overflow: auto;
    border: 1px solid #E5E5E5;
    margin-bottom: 10px;
}
</style>

<div class="pageContent" style="margin: 0 10px" layoutH="50">
     
    <input id="importExcelFile" type="file" name="importExcelFile" 
        uploaderOption="{
            swf:'styles/uploadify/scripts/uploadify.swf',
            uploader:'<c:url value='/management/system/imsiCallMapper/importExcel;jsessionid=${pageContext.session.id}'/>',
            
            queueID:'fileQueue',
            fileSizeLimit:'200000KB',
            fileTypeDesc:'*.xls;*.xlsx;',
            fileTypeExts:'*.xls;*.xlsx;',
            buttonImage:'styles/uploadify/img/add.jpg',
            buttonClass:'my-uploadify-button',
            width:102,
            auto:false,
            onUploadSuccess:DWZ.ajaxDone,
            onQueueComplete:uploadifyQueueComplete
        }"
    />
    <div id="fileQueue" class="fileQueue"></div>
    
    <input type="image" alt="update file" src="styles/uploadify/img/upload.jpg" onclick="$('#importExcelFile').uploadify('upload', '*');"/>
    <input type="image" alt="update file" src="styles/uploadify/img/cancel.jpg" onclick="$('#importExcelFile').uploadify('cancel', '*');"/>
</div>
 --%>