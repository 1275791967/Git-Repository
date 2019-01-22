<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<div id="login">
    <div id="login_header">
        <h1 class="login_logo">
            <a href="#"><img src="<c:url value='/styles/dwz/themes/default/images/login_logo.png'/>" /><%-- <span class="logo-title"><fmt:message key="ui.title" /></span> --%></a>
        </h1>
        <div class="login_headerContent">
            <div class="navList">
                <ul>
                    <%-- 
                    <li><a href="<c:out value='/logout'/>"><fmt:message key="ui.login"/></a></li>
		            <li><a href="<c:out value='/help'/>"><fmt:message key="ui.help"/></a></li>
		            <li><a href="<c:out value='/contactUs'/>"><fmt:message key="ui.contactUs"/></a></li>
		            <li><a href="<c:out value='/aboutMe'/>"><fmt:message key="ui.about"/></a></li>
		            --%>
                </ul> 
            </div>
            <h2 class="login_title"><img src="<c:url value='/styles/dwz/themes/default/images/login_title.png'/>" /></h2>
        </div>
    </div>
    <div id="login_content">
        <div class="loginForm">
            <form action="<c:url value='/passport/login'/>">
                <p>
                    <label><fmt:message key="form.username"/>：</label>
                    <input type="text" name="username" size="20" class="login_input" />
                </p>
                <p>
                    <label><fmt:message key="form.pwd"/>：</label>
                    <input type="password" name="password" size="20" class="login_input" />
                </p>              
                <p style="color:red; ">${error}</p>
                <div class="login_bar">
                    <input class="sub" type="submit" value=" " />
                </div>
            </form>
        </div>
        <div class="login_banner"><img src="<c:url value='/styles/dwz/themes/default/images/login_banner.jpg'/>" /></div>
        <div class="login_main">
            <!-- 
            <ul class="helpList">
                <li><a href="#">下载驱动程序</a></li>
                <li><a href="#">如何安装密钥驱动程序？</a></li>
                <li><a href="#">忘记密码怎么办？</a></li>
                <li><a href="#">为什么登录失败？</a></li>
            </ul>
             -->           
             <div class="login_inner">
                <p>首次登录请默认密码很简单，请及时修改密码。</p>
                <p>通过管理平台可以实现侦码信息汇总分析。</p>
            </div>
        </div>
    </div>
    <div id="login_footer">
        <!-- Copyright &copy; 2009 www.dwzjs.com Inc. All Rights Reserved. -->
    </div>
</div>