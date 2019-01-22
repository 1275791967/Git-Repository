<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.inc.jsp"%>
<c:import url="/management/frag/pagerForm"></c:import>

<form method="post" rel="pagerForm" action="<c:url value='/management/log'/>" onsubmit="return navTabSearch(this)">
<div class="pageHeader">
    <div class="searchBar">
        <ul class="searchContent">
            <li>
                <label>关键词：</label>
                <input type="text" name="keywords" value="${param.keywords}"/>
            </li>
            <li>
                <label>类型：</label>
                <%-- <select class="combox" name="logTypeID">
                    <option value="" ${empty bean.logTypeID ? 'selected="selected"' : ''}>全部</option>
                    <option value="1" ${bean.logTypeID == 1 ? 'selected="selected"' : ''}>获取电话号码</option>
                    <option value="2" ${bean.logTypeID == 2 ? 'selected="selected"' : ''}>电话号码结果</option>
                </select> --%>
                <select class="combox" name="logTypeID">
                    <option value="">全部</option>
                    <c:forEach var="item" items="${dataTypes}">
                        <option value="${item.key}" ${item.key eq vo.logTypeID ? 'selected="selected"' : ''}>${item.value}</option>
                    </c:forEach>
                </select>
            </li> 
            </ul>
            <ul class="searchContent">
<!--               <li>  -->
<!--                 <label>设备名称：</label> -->
<%--                 <input type="hidden" name="deviceID" bringbackname="org.id" value="${not empty vo.deviceID ? vo.deviceID : ''}" /> --%>
<%--                 <input type="text"  name="deviceName" bringbackname="org.deviceName" value="${not empty vo.deviceName ? vo.deviceName : ''}" class="readonly" readonly="readonly" maxlength="200" /> --%>
<%--                 <a class="btnLook" href="<c:url value='/management/device/lookup'/>" lookupGroup="org">查找带回</a> --%>
<!--             </li>  -->
            <li>  
                <label>设备名称：</label>
                <input type="hidden" name="DeviceID" bringbackname="org.id" value="${vo.deviceID}" >
                <input type="text"  name="DeviceName" bringbackname="org.deviceName" value="${vo.deviceName}" autocomplete="off" lookupgroup="org" suggesturl="<c:url value='/management/device/lookupSuggest'/>" suggestfields="deviceName" postfield="keywords" lookuppk="id" />
                <a class="btnLook" href="<c:url value='/management/device/lookup'/>" lookupGroup="org" suggestfields="deviceName" autocomplete="off" postfield="keywords" lookuppk="id">查找带回</a>
            </li> 
            <li>
                <label>开始时间：</label>
                <input type="text" name="startDate" value="${vo.startDate}" class="date" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true"/>
                <a class="inputDateButton" href="javascript:;">选择</a>
            </li>
            <li>
                <label>结束时间：</label>
                <input type="text" name="endDate" value="${vo.endDate}" class="date" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true"/>
                <a class="inputDateButton" href="javascript:;">选择</a>
            </li> 
        </ul>  
        <div class="subBar">
            <ul>                        
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit">搜索</button></div></div></li>
            </ul>
        </div>
    </div>
</div>
</form>
<div class="pageContent">   
    <div class="panelBar">
        <ul class="toolBar">
            <li><a class="delete" target="ajaxTodo" href="<c:url value='/management/log/delete/{slt_objId}'/>" title="你确定要删除吗?"><span>删除</span></a></li>
            <li class="line">line</li>
            <li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids" postType="string" href="<c:url value='/management/log/deleteBatch/{ids}'/>" class="deleteAll"><span>批量删除</span></a></li>
            <li class="line">line</li>            
            <li><a class="export" href="<c:url value='/management/log/exportExcel'/>" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>数据导出</span></a></li>            
        </ul>
    </div>
    <table class="table" width="100%" layoutH="138">
        <thead>
            <tr>
                <th width="40"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
                <th width="50">序号</th>
                <th width="100" orderField="DeviceName" class="${param.orderField eq 'DeviceName' ? param.orderDirection : ''}">设备名称</th>
                <th width="100" orderField="LogTypeID" class="${param.orderField eq 'LogTypeID' ? param.orderDirection : ''}">类型</th>
                <th orderField="Content" class="${param.orderField eq 'Content' ? param.orderDirection : ''}">日志内容</th>                
                <th width="150" orderField="RecordTime" class="${param.orderField eq 'RecordTime' ? param.orderDirection : ''}">记录时间</th>
                <th width="150" orderField="Remark" class="${param.orderField eq 'Remark' ? param.orderDirection : ''}">备注</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${beans}" varStatus="s">
            <tr target="slt_objId" rel="${item.id }">
                <td><input name="ids" value="${item.id }" type="checkbox"></td>
                <td>${s.index + 1}</td>
                <td>${item.deviceName}</td>
                <%-- <c:choose>                    
                    <c:when test="${not empty item.logTypeID and item.logTypeID==1}">
                        <td>获取电话号码</td>
                    </c:when>
                    <c:when test="${not empty item.logTypeID and item.logTypeID==2}">
                        <td>电话号码结果</td>
                    </c:when>
                </c:choose> --%>
                <td>${dataTypes[item.logTypeID]}</td>
                <td>${item.content}</td>
                <td><fmt:formatDate value="${item.recordTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td>${item.remark}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <c:import url="/management/frag/panelBar"></c:import>
</div>