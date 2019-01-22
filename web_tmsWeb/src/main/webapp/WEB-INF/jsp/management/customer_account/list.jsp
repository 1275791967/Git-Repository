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
<form method="post" rel="pagerForm" action="<c:url value='/management/customer_account'/>" onsubmit="return navTabSearch(this)">
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
            <li><a class="add" target="dialog" width="600" height="450" rel="monitorNav" href="<c:url value='/management/customer_account/add'/>" title="添加"><span>添加</span></a></li>
            <li><a class="edit" target="dialog" width="600" height="450" rel="monitorNav" href="<c:url value='/management/customer_account/edit/{slt_objId}'/>" title="编辑"><span>编辑</span></a></li>
            <li><a class="delete" target="ajaxTodo" href="<c:url value='/management/customer_account/delete/{slt_objId}'/>" title="你确定要删除吗?"><span>删除</span></a></li>
            <li class="line">line</li>
            <li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids" postType="string" href="<c:url value='/management/customer_account/deleteBatch/{ids}'/>" class="deleteAll"><span>批量删除</span></a></li>
            <li class="line">line</li>
            <li><a class="favor" target="dialog" width="600" height="400" rel="monitorNav" href="<c:url value='/management/customer_account/pay/{slt_objId}'/>" title="充值"><span>充值</span></a></li> 
<%--             <li><a class="icon" href="<c:url value='/management/system/customer/exportExcel'/>" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li> --%>
<%--             <li><a class="add" target="dialog" width="520" height="400" rel="importExcelDialog" href="<c:url value='/management/system/customer/importExcelDialog'/>" title="号码归属地文件导入"><span>导入EXCEL</span></a></li> --%>
        
        </ul>
    </div>
    <table class="table" width="100%" layoutH="138">
        <thead>
            <tr>
                <th width="40"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
                <th width="50">序号</th>
<%--                 <th width="130" orderField="ID" class="${param.orderField eq 'ID' ? param.orderDirection : ''}">ID</th>   --%>                
                <th width="130" orderField="Name" class="${param.orderField eq 'Name' ? param.orderDirection : ''}">账户名</th>
                <th width="130" orderField="PSW" class="${param.orderField eq 'PSW' ? param.orderDirection : ''}">密码</th> 
                <th width="130" orderField="DeviceName" class="${param.orderField eq 'DeviceName' ? param.orderDirection : ''}">设备名称</th>
                <th width="130" orderField="Phone" class="${param.orderField eq 'Phone' ? param.orderDirection : ''}">联系方式</th>
                <th width="130" orderField="LocalBalance" class="${param.orderField eq 'LocalBalance' ? param.orderDirection : ''}">本地余额</th>
                <th width="130" orderField="ThirdBalance" class="${param.orderField eq 'ThirdBalance' ? param.orderDirection : ''}">远程余额</th>               
                <th width="130" orderField="Status" class="${param.orderField eq 'Status' ? param.orderDirection : ''}">状态</th> 
                <th width="130" orderField="CreateDate" class="${param.orderField eq 'CreateDate' ? param.orderDirection : ''}">创建时间</th>                
                <th orderField="Remark" class="${param.orderField eq 'Remark' ? param.orderDirection : ''}">详情</th>
                <th width="60">操作</th>                
            </tr>
        </thead>
        <tbody>
<%--         <c:forEach var="item" items="${customers}" varStatus="s"> --%>
            <c:forEach var="item" items="${beans}" varStatus="s">
            <tr target="slt_objId" rel="${item.id}">
                <td><input name="ids" value="${item.id }" type="checkbox"></td>
                <td>${s.index + 1}</td>                 
                <td>${item.name}</td>
                <td>${item.psw}</td>
                <td>${item.deviceName}</td>
                <td>${item.phone}</td>
                <td>${item.localBalance}</td>
                <td>${item.thirdBalance}</td> 
                <td>${'ACTIVE' eq item.status ? '激活' : '未激活'}</td>
                
<%--                 <td>${item.status}</td> --%>
<%--                 <td>${item.createDate}</td> --%>
<%--                 <td><fmt:formatDate value="${item.insertDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td> --%>
                <td><fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td>${item.remark}</td>
                <td>
                    <a title="删除" target="ajaxTodo" href="management/customer_account/delete/${item.id}" class="btnDel">删除</a>
                    <a title="编辑" target="dialog" href="management/customer_account/edit/${item.id}" mask="true" width="600" height="450" class="btnEdit">编辑</a>
                </td>               
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <c:import url="/management/frag/panelBar"></c:import>
</div>