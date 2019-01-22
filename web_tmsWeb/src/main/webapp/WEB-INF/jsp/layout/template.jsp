<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.inc.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><decorator:title/><fmt:message key="ui.title" /></title>

<c:choose>
    <c:when test="${website.template eq 'template1'}">
        <link rel="shortcut icon" href="<c:url value='/images/favicon.ico'/>" type="image/x-icon" />
    </c:when>
    <c:otherwise>
        <link rel="shortcut icon" href="<c:url value='/images/favicon.ico'/>" type="image/x-icon" />
    </c:otherwise>
</c:choose>

<c:set var="website" value="${info:contextWebsite()}"></c:set>

<meta name="keywords" content='<c:out value="${website.metaKeywords}"></c:out>' />
<meta name="description" content='<c:out value="${website.metaDescription}"></c:out>' />
<meta name="author" content="support@irongteng.com" />
<meta name="ROBOTS" content="INDEX, FOLLOW" />

<link href="<c:url value='/styles/website/common/layout/hlcf.css'/>" rel="stylesheet" type="text/css" />
<link href="<c:url value='/styles/website/common/theme/color.css'/>" rel="stylesheet" type="text/css" />
<link href="<c:url value='/styles/dwz/themes/css/login.css'/>" rel="stylesheet" type="text/css" />

<!--[if IE]>
<script src="<c:out value='/styles/website/common/js/html5.js'/>"></script>
<![endif]-->
<script src="<c:url value='/styles/dwz/js/jquery-1.7.2.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='/styles/website/common/js/ui.js'/>" type="text/javascript"></script>
<decorator:head/>
</head>
<body>

<c:choose>
<%-- 有logo和公司信息 --%>
<c:when test="${website.template eq 'template1'}">
    <c:import url="/WEB-INF/jsp/layout/home/header.jsp" charEncoding="UTF-8"/>
    
    <article id="container">
    
    <div class="contaiterBox">
        <div class="CB_T"><span class="CB_T_l">&nbsp;</span></div>
        
        <decorator:body/>
    
        <div class="clearBoth">&nbsp;</div>
        <div class="CB_B"><span class="CB_B_l">&nbsp;</span></div>
    </div>
    </article>
    
    <c:import url="/WEB-INF/jsp/layout/home/footer.jsp" charEncoding="UTF-8"/>
</c:when>

<c:otherwise>
    <c:import url="/WEB-INF/jsp/layout/nologo/header.jsp" charEncoding="UTF-8"/>
    
    <article id="container">
        <decorator:body/>
        
        <div class="clearBoth">&nbsp;</div>
    </article>
    
    <c:import url="/WEB-INF/jsp/layout/nologo/footer.jsp" charEncoding="UTF-8"/>
</c:otherwise>
</c:choose>

</body>

</html>
