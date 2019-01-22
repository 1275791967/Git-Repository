<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>
<c:set var="website" value="${info:contextWebsite()}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title><fmt:message key="ui.title" /></title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

        <c:choose>
            <c:when test="${website.template eq 'template1'}">
                <link rel="shortcut icon" href="<c:url value='/images/favicon.ico'/>" type="image/x-icon" />
            </c:when>
            <c:otherwise>
                <link rel="shortcut icon" href="<c:url value='/images/favicon.ico'/>" type="image/x-icon" />
            </c:otherwise>
        </c:choose>

        <link href="<c:url value='/styles/dwz/themes/default/style.css'/>" rel="stylesheet" type="text/css" />
        <link href="<c:url value='/styles/dwz/themes/css/core.css'/>" rel="stylesheet" type="text/css" />
        <link href="<c:url value='/styles/management/misc.css'/>" rel="stylesheet" type="text/css" />
        <script src="<c:url value='/styles/dwz/js/speedup.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/styles/dwz/js/jquery-1.7.2.min.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/styles/dwz/js/jquery.cookie.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/styles/dwz/js/jquery.validate.min.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/styles/dwz/js/jquery.bgiframe.js'/>" type="text/javascript"></script>

       
        <script src="<c:url value='/styles/dwz/js/dwz.min.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/styles/dwz/js/dwz.regional.zh.js'/>" type="text/javascript"></script>
        <script src="<c:url value='/styles/management/misc.js'/>" type="text/javascript"></script>

        <script src="<c:url value='/styles/js/dwz.common.js'/>" type="text/javascript"></script>
        <script type="text/javascript">

            var onlineMap = ${isOnline};

            $(function () {
                DWZ.init("<c:url value='/styles/dwz/dwz.frag.xml'/>", {
                    loginTitle: "<fmt:message key='ui.login' />", // 弹出登录对话框
                    loginUrl: "<c:url value='/passport/login_dialog'/>", // 跳到登录页面
                    pageInfo: {pageNum: "pageNum", numPerPage: "pageSize", orderField: "orderField", orderDirection: "orderDirection"},
                    debug: false, // 调试模式 【true|false】
                    callback: function () {
                        initEnv();
                        $("#themeList").theme({themeBase: "<c:url value='/styles/dwz/themes'/>"});
                    }
                });
            });

        </script>

        <style type="text/css">

            #l-map{height:300px;width:100%;}
            #r-result{width:100%;}
            .logo-title {
                color:white;
                font:normal bold 20px/50px arial,sans-serif;
            }
            .nologo-title {
                padding-left: 10px;
                color:white;
                font:normal bold 24px/50px arial,sans-serif;
            }
        </style>
    </head>
    
    <body scroll="no">
        <c:set var="contextUser" value="${info:contextUser()}"></c:set>
            <div id="layout">
                <!-- 背景音乐 -->
                <div id="bg_music" style="display:none"></div>

                <div id="header">
                    <div class="headerNav">
                    <c:choose>
                        <c:when test="${website.template eq 'template1'}">
                            <a class="logo" href="javascript:void(0)">Logo</a><span class="logo-title"><fmt:message key="ui.title" /></span>
                        </c:when>
                        <c:otherwise>
                            <span class="nologo-title"><fmt:message key="ui.title" /></span>
                        </c:otherwise>
                    </c:choose>
                    <ul class="nav">
                        <li><a href="<c:url value='/aboutMe'/>" target="dialog" width="350"  height="200">版本信息</a></li>
                        <li><a href="#">${contextUser.username}</a></li>
                        <li><a href="<c:url value='/management/user/editPwdDialog/${contextUser.id}'/>" target="dialog" width="600">修改密码</a></li>
                        <li><a href="<c:url value='/passport/logout'/>">退出登录</a></li>
                    </ul>
                    <ul class="themeList" id="themeList">
                        <li theme="default"><div class="selected">blue</div></li>
                        <li theme="green"><div>green</div></li>
                        <li theme="purple"><div>purple</div></li>
                        <li theme="silver"><div>silver</div></li>
                        <li theme="azure"><div>azure</div></li>
                    </ul>
                </div>
            </div>

            <div id="leftside">
                <div id="sidebar_s">
                    <div class="collapse">
                        <div class="toggleCollapse"><div></div></div>
                    </div>
                </div>
                <div id="sidebar">
                    <div class="toggleCollapse"><h2>菜单</h2><div>collapse</div></div>

                    <div class="accordion" fillSpace="sideBar">
                    <c:if test="${fn:toUpperCase(role) eq 'ADMIN_ROLE'}">
                    <div class="accordionHeader">
                        <h2><span>Folder</span>业务管理</h2>
                    </div>
                    <div class="accordionContent">
                        <ul class="tree treeFolder">                                
                            <li><a href="<c:url value='/management/device'/>" target="navTab" rel="DeviceLiNav">设备管理</a></li>
                            <li><a href="<c:url value='/management/loadinfo'/>" target="navTab" rel="LoadInfoLiNav">翻译服务器负载管理</a></li>
                            <li><a href="<c:url value='/management/opInfo'/>" target="navTab" rel="OpInfoLiNav">翻译通道类型管理</a></li> 
                            <li><a href="<c:url value='/management/transt_flow'/>" target="navTab" rel="TranstFlowLiNav">翻译传输流程管理</a></li> 
                            <li><a href="<c:url value='/management/customer_account'/>" target="navTab" rel="CustomerAccountLiNav">账户管理</a></li>
                            <li><a href="<c:url value='/management/customer'/>" target="navTab" rel="CustomerLiNav">手机客户管理</a></li> 
                                                                                                 
                        </ul>
                    </div>
                    </c:if>   
                    
                    
	                 <!-- 只有管理员权限的用户才能操作系统管理 -->
                    <c:if test="${fn:toUpperCase(role) eq 'ADMIN_ROLE'}">
                    <div class="accordionHeader">
                        <h2><span>Folder</span>电话管理</h2>
                    </div>
                    <div class="accordionContent">
                        <ul class="tree treeFolder">                                                        
                            <li><a href="<c:url value='/management/attribution'/>" target="navTab" rel="AttributionLiNav">号码归属地管理</a></li>                         
                            <li><a href="<c:url value='/management/imsi_phone'/>" target="navTab" rel="IMSI_PhoneLiNav">电话号码管理</a></li>                       
                        </ul>
                    </div>
                    </c:if>   
                    <c:if test="${fn:toUpperCase(role) eq 'ADMIN_ROLE'}">
                    <div class="accordionHeader">
                        <h2><span>Folder</span>系统管理</h2>
                    </div>
                    <div class="accordionContent">
                        <ul class="tree treeFolder">                                                        
                            <li><a href="<c:url value='/management/system'/>" target="navTab" rel="SystemLiNav">系统管理</a></li>
                            <li><a href="<c:url value='/management/log'/>" target="navTab" rel="LogLiNav">日志管理</a></li>                       
                        </ul>
                    </div>
                    </c:if>                           
                   </div>
                </div>
            </div>
            <div id="container">
                <div id="navTab" class="tabsPage">
                    <div class="tabsPageHeader">
                        <div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
                            <ul class="navTab-tab">
                                <li tabid="main" class="main"><a href="javascript:void(0)"><span><span class="home_icon">主页</span></span></a></li> 
                            </ul>                            
                        </div>
                        <div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
                        <div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
                        <div class="tabsMore">more</div>
                    </div>
                                   
                    <ul class="tabsMoreList">
                         <li><a href="javascript:void(0)">主页</a></li> 
                    </ul>
                    <div class="navTab-panel tabsPageContent layoutBox">
                        <div id="homeContent" class="page unitBox">                             
                        </div>   
                    </div>
                    
                </div>
            </div>
        </div>
        <div id="footer">
            <c:if test="${website.template eq 'template1'}"><fmt:message key="ui.copyrights" /></c:if>
        </div>
    </body>
</html>