<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.inc.jsp"%>
<c:import url="/management/frag/pagerForm"></c:import>
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
<form method="post" rel="pagerForm" action="<c:url value='/management/imsi_phone'/>" onsubmit="return navTabSearch(this)">
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
            </ul>
        </div>
    </div>
</div>
</form>
<div class="pageContent">
    <div class="panelBar">
        <ul class="toolBar">
            <li><a class="add" target="dialog" width="600" height="400" rel="monitorNav" href="<c:url value='/management/imsi_phone/add'/>" title="添加"><span>添加</span></a></li>
            <li><a class="edit" target="dialog" width="600" height="400" rel="monitorNav" href="<c:url value='/management/imsi_phone/edit/{slt_objId}'/>" title="编辑"><span>编辑</span></a></li>
            <li><a class="delete" target="ajaxTodo" href="<c:url value='/management/imsi_phone/delete/{slt_objId}'/>" title="你确定要删除吗?"><span>删除</span></a></li>
            <li class="line">line</li>
            <li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids" postType="string" href="<c:url value='/management/imsi_phone/deleteBatch/{ids}'/>" class="deleteAll"><span>批量删除</span></a></li>
            <li class="line">line</li>            
            <li><a class="export" href="<c:url value='/management/imsi_phone/exportExcel'/>" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
            <li><a class="import" target="dialog" width="450" height="300" rel="importExcelDialog" href="<c:url value='/management/imsi_phone/importExcelDialog'/>" title="文件导入"><span>导入EXCEL</span></a></li>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="138">
        <thead>
            <tr>
                <th width="40"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
                <th width="50">序号</th>                
                <th width="130" orderField="Phone" class="${param.orderField eq 'Phone' ? param.orderDirection : ''}">电话号码</th>
                <th width="130" orderField="IMSI" class="${param.orderField eq 'IMSI' ? param.orderDirection : ''}">IMSI</th>
<%--                 <th width="130" orderField="Name" class="${param.orderField eq 'Name' ? param.orderDirection : ''}">用户名</th> --%>
<%--                 <th width="130" orderField="OwnID" class="${param.orderField eq 'OwnID' ? param.orderDirection : ''}">身份证号码</th> --%>
                <th orderField="Address" class="${param.orderField eq 'Address' ? param.orderDirection : ''}">地址</th>
                <th width="130" orderField="Province" class="${param.orderField eq 'Province' ? param.orderDirection : ''}">省</th>
                <th width="130" orderField="City" class="${param.orderField eq 'City' ? param.orderDirection : ''}">市</th>                 
<%--                 <th width="130" orderField="imei" class="${param.orderField eq 'imei' ? param.orderDirection : ''}">IMEI</th>                --%>
<%--                 <th width="130" orderField="Mac" class="${param.orderField eq 'Mac' ? param.orderDirection : ''}">MAC</th> --%>
<%--                 <th width="130" orderField="Imsi_MSISDN" class="${param.orderField eq 'Imsi_MSISDN' ? param.orderDirection : ''}">Imsi_MSISDN</th>  --%>
                <th width="130" orderField="RecordTime" class="${param.orderField eq 'RecordTime' ? param.orderDirection : ''}">记录时间</th> 
<%--                 <th width="80"  orderField="Remark" class="${param.orderField eq 'Remark' ? param.orderDirection : ''}">详情</th>             --%>
                <th width="60">操作</th>                
            </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${callList}" varStatus="s">
            <tr target="slt_objId" rel="${item.id }">
                <td><input name="ids" value="${item.id }" type="checkbox"></td>
                <td>${s.index + 1}</td>                
                <td>${item.phone}</td>                
                <td>${item.imsi}</td>
<%--                 <td>${item.name}</td>  --%>
<%--                 <td>${item.ownID}</td> --%>
                <td>${item.address}</td> 
                <td>${item.province}</td> 
                <td>${item.city}</td>                
<%--                 <td>${item.imei}</td> --%>
<%--                 <td>${item.mac}</td> --%>
<%--                 <td>${item.imsi_MSISDN}</td>  --%>
                <td><fmt:formatDate value="${item.recordTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>                                                
<%--                 <td>${item.remark}</td>                 --%>
                <td>
                    <a title="删除" target="ajaxTodo" href="management/imsi_phone/delete/${item.id}" class="btnDel">删除</a>
                    <a title="编辑" target="dialog" href="management/imsi_phone/edit/${item.id}" mask="true" width="600" height="400" class="btnEdit">编辑</a>
                </td>                
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <c:import url="/management/frag/panelBar"></c:import>
</div>