<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>
<div class="pageContent">
    <form method="post" action="<c:url value='/management/attribution/importExcel?navTabId=AttributionLiNav&callbackType=closeCurrent'/>" enctype="multipart/form-data" class="pageForm required-validate" onsubmit="return iframeCallback(this, dialogAjaxDone);">
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
<h2 class="contentTitle">uploadify上传</h2>

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
            uploader:'<c:url value='/management/system/callAttribution/importExcel;jsessionid=${pageContext.session.id}'/>',
            
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
    <!-- 
    <input id="importExcelFile" name="importExcelFile" type="file" multiple="true" onselect="">
     -->
    <div id="fileQueue" class="fileQueue"></div>
    
    <input type="image" alt="update file" src="styles/uploadify/img/upload.jpg" onclick="$('#importExcelFile').uploadify('upload', '*');"/>
    <input type="image" alt="update file" src="styles/uploadify/img/cancel.jpg" onclick="$('#importExcelFile').uploadify('cancel', '*');"/>
<!-- 
    <div class="divider"></div>
    <p style="margin:10px"><a href="http://www.uploadify.com/documentation/" target="_blank">Uploadify 在线文档</a></p>
    
<textarea cols="160" rows="10">
uploaderOption: http://www.uploadify.com/documentation/
    auto            : true,               // Automatically upload files when added to the queue
    buttonClass     : '',                 // A class name to add to the browse button DOM object
    buttonCursor    : 'hand',             // The cursor to use with the browse button
    buttonImage     : null,               // (String or null) The path to an image to use for the Flash browse button if not using CSS to style the button
    buttonText      : 'SELECT FILES',     // The text to use for the browse button
    checkExisting   : false,              // The path to a server-side script that checks for existing files on the server
    debug           : false,              // Turn on swfUpload debugging mode
    fileObjName     : 'Filedata',         // The name of the file object to use in your server-side script
    fileSizeLimit   : 0,                  // The maximum size of an uploadable file in KB (Accepts units B KB MB GB if string, 0 for no limit)
    fileTypeDesc    : 'All Files',        // The description for file types in the browse dialog
    fileTypeExts    : '*.*',              // Allowed extensions in the browse dialog (server-side validation should also be used)
    height          : 30,                 // The height of the browse button
    itemTemplate    : false,              // The template for the file item in the queue
    method          : 'post',             // The method to use when sending files to the server-side upload script
    multi           : true,               // Allow multiple file selection in the browse dialog
    formData        : {},                 // An object with additional data to send to the server-side upload script with every file upload
    preventCaching  : true,               // Adds a random value to the Flash URL to prevent caching of it (conflicts with existing parameters)
    progressData    : 'percentage',       // ('percentage' or 'speed') Data to show in the queue item during a file upload
    queueID         : false,              // The ID of the DOM object to use as a file queue (without the #)
    queueSizeLimit  : 999,                // The maximum number of files that can be in the queue at one time
    removeCompleted : true,               // Remove queue items from the queue when they are done uploading
    removeTimeout   : 3,                  // The delay in seconds before removing a queue item if removeCompleted is set to true
    requeueErrors   : false,              // Keep errored files in the queue and keep trying to upload them
    successTimeout  : 30,                 // The number of seconds to wait for Flash to detect the server's response after the file has finished uploading
    uploadLimit     : 0,                  // The maximum number of files you can upload
    width           : 120,                // The width of the browse button
</textarea>
 -->
</div>
<!-- 
<script type="text/javascript" src="styles/js/system/call_attr/JQueryUploadHelper.js"></script>
<script>
    JQueryUploadHelper.SESSIONID = "${sessionid}";
    $(document).ready(JQueryUploadHelper.init);
</script>
 -->
 --%>