<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.inc.jsp"%>
<form id="pagerForm" method="post" action="<c:url value='/management/device/lookup'/>">
    <input type="hidden" name="pageNum" value="1" />
    <input type="hidden" name="pageSize" value="${vo.pageSize}" />
    <input type="hidden" name="orderField" value="${param.orderField}" />
    <input type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>
<form method="post" rel="pagerForm" action="<c:url value='/management/device/lookup'/>" onsubmit="return dwzSearch(this, 'dialog');">
<div class="pageHeader">
    <div class="searchBar">
        
        <ul class="searchContent">
            <li>
                <label>关键词：</label>
                <input type="text" name="keywords" value="${param.keywords}"/>
            </li>
        </ul>
        <div class="subBar">
            <ul>                        
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit">搜索</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button type="button" onclick="javascript:$.bringBack({id:'', customerName:'全部'})">重置默认</button></div></div></li>
            </ul>
        </div>
    </div>
</div>
</form>

<div class="pageContent">
    <table class="table" width="100%" layoutH="138">
        <thead>
            <tr>
<!--                 <th width="40"><input type="checkbox" group="ids" class="checkboxCtrl"></th> -->
                <th width="50">序号</th>
<%--                 <th width="130" orderField="ID" class="${param.orderField eq 'ID' ? param.orderDirection : ''}">ID</th>   --%>
                <th width="130" orderField="DeviceName" class="${param.orderField eq 'DeviceName' ? param.orderDirection : ''}">设备名称</th>
                <th width="130" orderField="DeviceType" class="${param.orderField eq 'DeviceType' ? param.orderDirection : ''}">设备类型</th>
                <th width="130" orderField="FeatureCode" class="${param.orderField eq 'FeatureCode' ? param.orderDirection : ''}">特征码</th> 
                <th width="130" orderField="Latitude" class="${param.orderField eq 'Latitude' ? param.orderDirection : ''}">经度</th> 
                <th width="130" orderField="Longitude" class="${param.orderField eq 'FeatureCode' ? param.orderDirection : ''}">纬度</th> 
                <th width="160" orderField="CreateTime" class="${param.orderField eq 'Longitude' ? param.orderDirection : ''}">创建时间</th>
<%--                 <th width="130" orderField="UpdateTime" class="${param.orderField eq 'UpdateTime' ? param.orderDirection : ''}">更新时间</th>                 --%>
                <th orderField="Remark" class="${param.orderField eq 'Remark' ? param.orderDirection : ''}">详情</th>
                <th width="80">查找带回</th>               
            </tr>
        </thead>
        <tbody>
<%--         <c:forEach var="item" items="${customers}" varStatus="s"> --%>
            <c:forEach var="item" items="${beans}" varStatus="s">
            <tr target="slt_objId" rel="${item.id}">
<%--                 <td><input name="ids" value="${item.id }" type="checkbox"></td> --%>
                <td>${s.index + 1}</td>               
                <td>${item.deviceName}</td> 
                <td>${deviceType[item.deviceType]}</td>
                <%-- <c:choose>
                    <c:when test="${not empty item.deviceType and item.deviceType==0}">
                        <td>翻译前端</td>
                    </c:when>
                    <c:when test="${not empty item.deviceType and item.deviceType==1}">
                        <td>翻译服务器</td>
                    </c:when>
                    <c:when test="${not empty item.deviceType and item.deviceType==2}">
                        <td>手机客户端</td>
                    </c:when>
                </c:choose> --%>               
<%--                 <td>${item.deviceType}</td> --%>
                <td>${item.featureCode}</td>  
                <td>${item.latitude}</td>  
                <td>${item.longitude}</td>         
                <td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
<%--                 <td><fmt:formatDate value="${item.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td> --%>
                <td>${item.remark}</td>
                <td>
                   <a class="btnSelect" href="javascript:$.bringBack({id:${item.id}, deviceName:'${item.deviceName}'})" title="查找带回">选择</a>
                </td>   
<!--                 <td> -->
<%--                    <a class="btnSelect" href="javascript:$.bringBack({id:${item.id}, mname:'${item.mname}', mid:'${item.mid}', specModel:'${item.specModel}',dimension:'${item.dimension}', mtype:'${mtypes[item.type]}'})" title="查找带回">选择</a> --%>
<!--                 </td>                           -->
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    <div class="panelBar">
        <div class="pages">
            <span>显示</span>
            <select name="pageSize" onchange="dwzPageBreak({targetType:"dialog",data:{numPerPage:this.value}})">
                <c:forEach begin="10" end="100" step="10" varStatus="s">
                    <option value="${s.index}" ${vo.pageSize eq s.index ? 'selected="selected"' : ''}>${s.index}</option>
                </c:forEach>
            </select>

            <span>总共: ${vo.totalCount}条</span>
        </div>
        <div class="pagination" targetType="dialog" totalCount="${vo.totalCount}" numPerPage="${vo.pageSize}" pageNumShown="10" currentPage="${vo.pageNum}"></div>
    </div>
</div>