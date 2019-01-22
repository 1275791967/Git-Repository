/**
 * 通过jquery uploadify插件上传文件
 * 需要 uploadify 2.0和jquery-1.8.0版本的支持
 * @author HeYing 2013-07-19
 * 
 */
function JQueryUploadHelper(){}

JQueryUploadHelper.prototype = {
    id:'JQueryUploadHelper',
    name:'JQueryUploadHelper',
    /**
     * 会话ID
     * @type String
     */
    SESSIONID:'',
    /**
     * 上传配置信息
     * @type 
     */
    uploadCfg:{},
    /**
     * 上传文件的file控件ID
     * @type String
     */
    fileInputId:'myFile',
    /**
     * 上传显示进度条的div的ID
     * @type String
     */
    fileQueueDivId:'fileQueue',
    /**
     * 显示错误消息的cmp对象ID
     * @type String
     */
    errorSpanId:'errorMsg',
    /**
     * 文件上传的默认配置
     * @type 
     */
    defaultCfg:{
        mode:'single'//选择的模式 single-单文件上传 ,multiple-批量上传 (默认为single)
        ,type:'file'//image--图片,file--文件(默认为file)
        ,fileFormat:['exe','bat','sh'] //当 type='file'时,表示 fileFormat表示不允许的文件格式,默认不允许上传exe,bat,sh文件
        ,maxFileSize:'10MB'//表示 文件最大为10MB
     },
     /**
      * 上传后的json数组，例如：[{name:'测试.txt',realName:'20130719152100.txt',path:'upload/20130719152100.txt',size:'1000'}]
      * @type 
      */
     jsonList:[{name:'',realName:'',path:'',size:'0'}],
    /**
     * //当队列中的所有文件上传成功后，弹出共有多少个文件上传成功
     * @param {Object} queueData
     */
    onQueueComplete:function(queueData){
        alert("onDisable-->"+queueData.filesQueued + 'files were successfully!');
    },
    onComplete:function (event, queueID, fileObj, response, data){
        alert('onComplete-->event='+event+'|queueID='+queueID+'|fileObj='+fileObj+'|response='+response+'|data='+data);
    },
    /**
     * 在调用disable方法时候触发
     */
    onDisable:function(){
        alert('onDisable-->uploadify is disable');
    },
    /**
     * 取消时触发
     */
    onCancel:function(){
        alert('onCancel-->你取消了文件上传')
    },
    /**
     * 文件选中时触发
     * @param {HTMLELement} file
     */
    onSelect:function(file){
        var me = this;
        me = window.JQueryUploadHelper;
        alert("onSelect -->文件"+file.name+"被选择了: "+file.size+" |file.id="+file.id+"|me.id="+me.id);
//        var settings = this.settings;
//        var errorCode = SWFUpload.QUEUE_ERROR.INVALID_FILETYPE;//
//        var errorMsg = "";
//        // Call the user-defined event handler
//        alert("onSelect-->settings="+settings+"|settings.onDialogClose="+settings.onDialogClose);
//        this.queueData.filesErrored = 1;
//        this.queueData.errorMsg = '文件 "' + file.name + '" 不是合法的文件类型 (' + settings.fileTypeDesc + ').';
//        var fileId = file.id;
//         $("#"+me.fileInputId).uploadify('cancel',fileId);//移除非法文件
//        if (settings.onDialogClose) settings.onDialogClose.apply(this, [this.queueData]);
    },
    onSelectError:function(file, errorCode, errorMsg){
        var me = this;
        this.queueData.errorMsg = "";
        var settings = this.settings;
        
        var myTag = "";
        switch(errorCode) {
            case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
                if (settings.queueSizeLimit > errorMsg) {
//                    this.queueData.errorMsg += 'The number of files selected exceeds the remaining upload limit (' + errorMsg + '). '+myTag;
                    this.queueData.errorMsg += '选择的文件数量超出了上传数量限制(' + errorMsg + '). '+myTag;
                } else {
//                    this.queueData.errorMsg += 'The number of files selected exceeds the queue size limit (' +settings.queueSizeLimit + ').'+myTag;
                    this.queueData.errorMsg += '选择的文件数量超出了上传数量限制(' +settings.queueSizeLimit + ').'+myTag;
                }
                break;
            case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
//                this.queueData.errorMsg += 'The file "' + file.name + '" exceeds the size limit (' + settings.fileSizeLimit + ').'+myTag;
                this.queueData.errorMsg += '文件"' + file.name + '" 超出文件大小限制(' + settings.fileSizeLimit + ').'+myTag;
                break;
            case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
//                this.queueData.errorMsg += 'The file "' + file.name + '" is empty.'+myTag;
                this.queueData.errorMsg += '文件"' + file.name + '" 是空文件.'+myTag;
                break;
            case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE://FILE_EXCEEDS_SIZE_LIMIT:-110 INVALID_FILETYPE=-130
//                this.queueData.errorMsg += 'The file "' + file.name + '" is not an accepted file type (' + settings.fileTypeDesc + ').'+myTag;
                this.queueData.errorMsg += '文件 "' + file.name + '" 不是合法的文件类型 (' + settings.fileTypeDesc + ').'+myTag;
                break;
        }
        
    },
    onDialogClose:function(queueData){
        var settings = this.settings;
        var me = window.JQueryUploadHelper;
        /**
         * // Update the queue information
            this.queueData.filesErrored  = filesSelected - filesQueued;
            this.queueData.filesSelected = filesSelected;
            this.queueData.filesQueued   = filesQueued - this.queueData.filesCancelled;
            this.queueData.queueLength   = queueLength;
         */
        if ($.inArray('onDialogClose', settings.overrideEvents) < 0) {
                if (this.queueData.filesErrored > 0) {
                    alert("onDialogClose-->"+this.queueData.errorMsg);
                  var errorSpan = document.getElementById(me.errorSpanId);
                  errorSpan.innerHTML = this.queueData.errorMsg;
                }
            }
        
    },
    /**
     * 在调用上传前触发
     * @param {HTMLELement} file
     */
    onUploadStart: function(file) {
//       alert('onError -->The file ' + file.name + ' is being uploaded.')
    },
    /**
     * 出错时触发
     * @param {} errorType
     * @param {} errObj
     */
    onError:function(errorType,errObj) {
        alert('onError -->The error was: ' + errObj.info)
    },
    /**
     * 当每个文件上传成功后调用
     * @param {HTMLELement} file
     * @param {String} data
     * @param {boolean} response
     */
    onUploadSuccess:function(file, data, response) {
        var me = this;
        me = window.JQueryUploadHelper;
        alert('onUploadSuccess -->The file ' + file.name + ' was successfully uploaded with a response of '
        +'me.id='+me.id
        +'|data='+data
        +'|response='+response
        );
    },
    /**
     * 获取上传文件的uploadify配置
     */
    getUploadifyCfg:function(){
        var me = this;
//        alert("getUploadifyCfg-->me.id="+me.id);
        var cfg = {
            debug : true,//是否显示调试控制台
            swf : 'styles/uploadify/uploadify.swf',//上传按钮的图片，默认是这个flash文件
            uploader : 'management/system/callAttribution/importExcel;jsessionid='+me.SESSIONID,//上传所处理的服务器
            cancelImg : 'styles/uploadfiy/uploadify-cancel.png',//取消图片
            method : 'post',
            folder : '/UploadFile',//上传后，所保存文件的路径
            queueID : me.fileQueueDivId,//上传显示进度条的那个div
            buttonText : '请选择文件',
            buttonClass:'cn_button01',//按钮样式
            progressData : 'percentage',//上传文件过程中，显示的类型，percentage进度条
            auto : false,//是否自动上传
            multi : true,//是否可多选
            fileSizeLimit:'100MB',//文件大小限制 1M
            errorMsg:'文件没有被添加到上传队列:',
            fileTypeDesc    : '合法的文件类型为：All File',        // The description for file types in the browse dialog [exe,jpeg,jpg,gif]
            fileTypeExts    : '*.*',//*.gif;*.jpeg;*.jpg;*.exe;*.bmp;!.png
            timeout:40 * 60 * 1000,//超时时间 30分钟
            onSelect:me.onSelect,
            onSelectError:me.onSelectError,
            onDialogClose:me.onDialogClose,//在onSelectError后触发
            onQueueComplete:me.onQueueComplete,//当队列中的所有文件上传成功后触发
            onDisable:me.onDisable,//在调用disable方法时候触发
            onCancel:me.onCancel,//取消了文件上传触发
            onUploadStart:me.onUploadStart,//在调用上传前触发
            onError:me.onError,//在调用上传前触发
            onUploadSuccess:me.onUploadSuccess,//当每个文件上传成功后
            onComplete:me.onComplete//所有上传完成后，服务器端的返回值
        };
//        alert("getUploadifyCfg-->cfg="+cfg);
        return cfg;
    },
    /**
     * 上传初始化
     */
   init:function(){
       var me = this;
       me = window.JQueryUploadHelper;
//       alert("initUpload-->me.id="+me.id);
       me.uploadCfg = me.getUploadifyCfg();
       $("#"+me.fileInputId).uploadify(me.uploadCfg);
   }
}
window.JQueryUploadHelper = new JQueryUploadHelper();
