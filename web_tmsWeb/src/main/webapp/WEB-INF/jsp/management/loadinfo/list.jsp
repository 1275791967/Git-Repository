<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.inc.jsp"%>
<c:import url="/management/frag/pagerForm"></c:import>

<form method="post" rel="pagerForm" action="<c:url value='/management/loadinfo'/>" onsubmit="return navTabSearch(this)">
<div class="pageHeader">
    <div class="searchBar">
        <ul class="searchContent">
            <li>
                <label>关键词：</label>
                <input type="text" name="keywords" value="${param.keywords}"/>
            </li>
            
<!--             <li> -->
<!--                 <label>监控设备：</label> -->
<!--                 <select class="combox" name="status"> -->
<!--                     <option value="">全部</option> -->
<%--                     <c:forEach var="item" items="${devBeans}"> --%>
<%--                         <option value="${item.id}" ${item.id eq vo.status ? 'selected="selected"' : ''}>${item.deviceName}</option> --%>
<%--                     </c:forEach> --%>
<!--                 </select> -->
<!--             </li> -->
<!--             <li> -->
<!--                 <label>开始时间：</label> -->
<%--                 <input type="text" name="startDate" value="${vo.startDate}" class="date" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true"/> --%>
<!--                 <a class="inputDateButton" href="javascript:;">选择</a> -->
<!--             </li> -->
<!--             <li> -->
<!--                 <label>结束时间：</label> -->
<%--                 <input type="text" name="endDate" value="${vo.endDate}" class="date" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="true"/> --%>
<!--                 <a class="inputDateButton" href="javascript:;">选择</a> -->
<!--             </li> -->
        </ul>
        <div class="subBar">
            <ul>                        
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit">搜索</button></div></div></li>
            </ul>
        </div>
    </div>
</div>
</form>

<div class="pageContent" layoutH="60">
    
    <div class="panelBar">
        <ul class="toolBar">
            <li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids" postType="string" href="<c:url value='/management/loadinfo/deleteBatch/{ids}'/>" class="deleteAll"><span>批量删除</span></a></li>
            <li class="line">line</li>
            <li><a class="export" href="<c:url value='/management/loadinfo/exportExcel'/>" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="138">
        <thead>
            <tr>
                <th width="40"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
                <th width="50">序号</th>
                <th  width="100" orderField="DeviceName" class="${param.orderField eq 'DeviceName' ? param.orderDirection : ''}">设备名称</th>
                <th width="100" orderField="Ch1Counts" class="${param.orderField eq 'Ch1Counts' ? param.orderDirection : ''}">通道1总通道数</th>
                <th width="120" orderField="Ch1NormalCounts" class="${param.orderField eq 'Ch1NormalCounts' ? param.orderDirection : ''}">通道1正常通道数</th>
                <th width="140" orderField="Ch1WaitTranslates" class="${param.orderField eq 'Ch1WaitTranslates' ? param.orderDirection : ''}">通道1待翻译数</th>
                <th width="100" orderField="Ch2Counts" class="${param.orderField eq 'Ch2Counts' ? param.orderDirection : ''}">通道2总通道数</th>
                <th width="120" orderField="Ch2NormalCounts" class="${param.orderField eq 'Ch2NormalCounts' ? param.orderDirection : ''}">通道2正常通道数</th>
                <th width="140" orderField="Ch2WaitTranslates" class="${param.orderField eq 'Ch2WaitTranslates' ? param.orderDirection : ''}">通道2待翻译数</th>
                <th width="100" orderField="Ch3Counts" class="${param.orderField eq 'Ch3Counts' ? param.orderDirection : ''}">通道3总通道数</th>
                <th width="120" orderField="Ch3NormalCounts" class="${param.orderField eq 'Ch3NormalCounts' ? param.orderDirection : ''}">通道3正常通道数</th>
                <th width="140" orderField="Ch3WaitTranslates" class="${param.orderField eq 'Ch3WaitTranslates' ? param.orderDirection : ''}">通道3待翻译数</th>
                <th width="100" orderField="Ch4Counts" class="${param.orderField eq 'Ch4Counts' ? param.orderDirection : ''}">通道4总通道数</th>
                <th width="120" orderField="Ch4NormalCounts" class="${param.orderField eq 'Ch4NormalCounts' ? param.orderDirection : ''}">通道4正常通道数</th>
                <th width="140" orderField="Ch4WaitTranslates" class="${param.orderField eq 'Ch4WaitTranslates' ? param.orderDirection : ''}">通道4待翻译数</th>
                <th width="100" orderField="Ch5Counts" class="${param.orderField eq 'Ch5Counts' ? param.orderDirection : ''}">通道5总通道数</th>
                <th width="120" orderField="Ch5NormalCounts" class="${param.orderField eq 'Ch5NormalCounts' ? param.orderDirection : ''}">通道5正常通道数</th>
                <th width="140" orderField="Ch5WaitTranslates" class="${param.orderField eq 'Ch5WaitTranslates' ? param.orderDirection : ''}">通道5待翻译数</th>
                <th width="100" orderField="AlarmStatus" class="${param.orderField eq 'Ch5WaitTranslates' ? param.orderDirection : ''}">告警状态</th>
                <th width="160" orderField="RecordTime" class="${param.orderField eq 'RecordTime' ? param.orderDirection : ''}">记录时间</th>
                <th width="60" orderField="Remark" class="${param.orderField eq 'Remark' ? param.orderDirection : ''}">备注</th>               
            </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${beans}" varStatus="s">
            <tr target="slt_objId" rel="${item.id }">
                <td><input name="ids" value="${item.id }" type="checkbox"></td>
                <td>${s.index + 1}</td>
                <td>${item.deviceName}</td>
                <td>${item.ch1Counts}</td>
                <td>${item.ch1NormalCounts}</td>
                <td>${item.ch1WaitTranslates}</td>
                <td>${item.ch2Counts}</td>
                <td>${item.ch2NormalCounts}</td>
                <td>${item.ch2WaitTranslates}</td>
                <td>${item.ch3Counts}</td>
                <td>${item.ch3NormalCounts}</td>
                <td>${item.ch3WaitTranslates}</td>
                <td>${item.ch4Counts}</td>
                <td>${item.ch4NormalCounts}</td>
                <td>${item.ch4WaitTranslates}</td>
                <td>${item.ch5Counts}</td>
                <td>${item.ch5NormalCounts}</td>
                <td>${item.ch5WaitTranslates}</td>               
                <td>${item.alarmStatus}</td>
                <td><fmt:formatDate value="${item.recordTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>               
                <td>${item.remark}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>    
    <c:import url="/management/frag/panelBar"></c:import>    
</div>