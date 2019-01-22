<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.inc.jsp"%>

<header id="header">
    <div id="sm" class="contaiterBox">
        <div class="CB_T"><span class="CB_T_l">&nbsp;</span></div>
        
        <ul>
            <li><a href="<c:out value='/logout'/>"><fmt:message key="ui.login"/></a></li>
            <%-- 
            <li><a href="<c:out value='/help'/>"><fmt:message key="ui.help"/></a></li>
            <li><a href="<c:out value='/contactUs'/>"><fmt:message key="ui.contactUs"/></a></li>
             --%>
            <li><a href="<c:out value='/aboutMe'/>"><fmt:message key="ui.about"/></a></li>
        </ul>
    </div>
</header>

