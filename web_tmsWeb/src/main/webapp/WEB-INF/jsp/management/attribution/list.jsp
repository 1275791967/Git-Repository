<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.inc.jsp"%>
<!-- <script src="/styles/js/Mock/jquery-3.3.1.min.js" type="text/javascript"></script> -->

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
<form method="post" rel="pagerForm" action="<c:url value='/management/attribution'/>" onsubmit="return navTabSearch(this)">
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
            <li><a class="add" target="dialog" width="800" height="400" rel="monitorNav" href="<c:url value='/management/attribution/add'/>" title="添加"><span>添加</span></a></li>
            <li><a class="edit" target="dialog" width="800" height="400" rel="monitorNav" href="<c:url value='/management/attribution/edit/{slt_objId}'/>" title="编辑"><span>编辑</span></a></li>
            <li><a class="delete" target="ajaxTodo" href="<c:url value='/management/attribution/delete/{slt_objId}'/>" title="你确定要删除吗?"><span>删除</span></a></li>
            <li class="line">line</li>
            <li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids" postType="string" href="<c:url value='/management/attribution/deleteBatch/{ids}'/>" class="deleteAll"><span>批量删除</span></a></li>
            <li class="line">line</li>             
            <li><a class="export" href="<c:url value='/management/attribution/exportExcel'/>" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
            <li><a class="import" target="dialog" width="450" height="300" rel="importExcelDialog" href="<c:url value='/management/attribution/importExcelDialog'/>" title="文件导入"><span>导入EXCEL</span></a></li>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="138">
        <thead>
            <tr>
                <th width="40"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
                <th width="50">序号</th>
                <th width="130" orderField="PhoneNO" class="${param.orderField eq 'PhoneNO' ? param.orderDirection : ''}">号码片段</th>
                <th width="130" orderField="Province" class="${param.orderField eq 'Province' ? param.orderDirection : ''}">省</th>
                <th width="130" orderField=City class="${param.orderField eq 'City' ? param.orderDirection : ''}">市</th>
                <th width="130" orderField="CellNO" class="${param.orderField eq 'CellNO' ? param.orderDirection : ''}">号码前缀</th>
                <th orderField="Remark" class="${param.orderField eq 'Remark' ? param.orderDirection : ''}">详情</th>
                <th width="60">操作</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${beans}" varStatus="s">
            <tr target="slt_objId" rel="${item.id }">
                <td><input name="ids" value="${item.id }" type="checkbox"></td>
                <td>${s.index + 1}</td>
                <td>${item.phoneNO}</td>
                <td>${item.province}</td>
                <td>${item.city}</td>
                <td>${item.cellNO}</td>
                <td>${item.remark}</td>
                <td>
                    <a title="删除" target="ajaxTodo" href="management/attribution/delete/${item.id}" class="btnDel">删除</a>
                    <a title="编辑" target="dialog" href="management/attribution/edit/${item.id}" mask="true" width="800" height="400" class="btnEdit">编辑</a>
                </td>               
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <c:import url="/management/frag/panelBar"></c:import>
</div>