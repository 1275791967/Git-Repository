<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.inc.jsp"%>
<c:import url="/management/frag/pagerForm"></c:import>

<form method="post" rel="pagerForm" action="<c:url value='/management/transt_flow'/>" onsubmit="return navTabSearch(this)">
<div class="pageHeader">
    <div class="searchBar">
        <ul class="searchContent">
            <li>
                <label>关键词：</label>
                <input type="text" name="keywords" value="${param.keywords}"/>
            </li>
             <li>
                <label>设备类型：</label>
                <select class="combox" name="status">
                    <option value="">全部</option>
                    <c:forEach var="item" items="${devBeans}">
                        <option value="${item.id}" ${item.id eq vo.status ? 'selected="selected"' : ''}>${item.deviceName}</option>
                    </c:forEach>
                </select>
            </li>
            </ul>
            <ul class="searchContent">
            <li>
                <label>发起设备：</label>
                <select class="combox" name="status">
                    <option value="">全部</option>
                    <c:forEach var="item" items="${devBeans}">
                        <option value="${item.id}" ${item.id eq vo.status ? 'selected="selected"' : ''}>${item.deviceName}</option>
                    </c:forEach>
                </select>
            </li>
            <li>
                <label>目的设备：</label>
                <select class="combox" name="status">
                    <option value="">全部</option>
                    <c:forEach var="item" items="${devBeans}">
                        <option value="${item.id}" ${item.id eq vo.status ? 'selected="selected"' : ''}>${item.deviceName}</option>
                    </c:forEach>
                </select>
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

<div class="pageContent" layoutH="60">
    
    <div class="panelBar">
        <ul class="toolBar">
            <li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids" postType="string" href="<c:url value='/management/transt_flow/deleteBatch/{ids}'/>" class="delete"><span>批量删除</span></a></li>
            <li class="line">line</li>
            <li><a class="icon" href="demo/common/dwz-team.xls" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="138">
        <thead>
            <tr>
                <th width="50">序号</th>
                <th orderField="LogTypeID" class="${param.orderField eq 'LogTypeID' ? param.orderDirection : ''}">日志类型</th>
                <th width="140" orderField="FromDeviceID" class="${param.orderField eq 'FromDeviceID' ? param.orderDirection : ''}">发起设备</th>
                <th width="140" orderField="ToDeviceID" class="${param.orderField eq 'ToDeviceID' ? param.orderDirection : ''}">目的设备</th>
                <th width="140" orderField="Content" class="${param.orderField eq 'Content' ? param.orderDirection : ''}">内容</th>
                <th width="140" orderField="Imsi" class="${param.orderField eq 'Imsi' ? param.orderDirection : ''}">IMSI</th>
                <th width="140" orderField="Imei" class="${param.orderField eq 'Imei' ? param.orderDirection : ''}">IMEI</th>
                <th width="140" orderField="Phone" class="${param.orderField eq 'Phone' ? param.orderDirection : ''}">电话</th>
                <th width="140" orderField="RecordTime" class="${param.orderField eq 'RecordTime' ? param.orderDirection : ''}">记录时间</th>
                <th width="140" orderField="Remark" class="${param.orderField eq 'Remark' ? param.orderDirection : ''}">备注</th>             
            </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${beans}" varStatus="s">
            <tr target="slt_objId" rel="${item.id }">
                <td>${s.index + 1}</td>
                <td>${item.logTypeID}</td>
                <td>${item.fromDeviceID}</td>
                <td>${item.toDeviceID}</td>
                <td>${item.content}</td>
                <td>${item.imsi}</td>
                <td>${item.imei}</td>
                <td>${item.phone}</td>                
                <td><fmt:formatDate value="${item.recordTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>               
                <td>${item.remark}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    <c:import url="/management/frag/panelBar"></c:import>
    
</div>