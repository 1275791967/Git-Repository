<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.inc.jsp"%>
<c:import url="/management/frag/pagerForm"></c:import>

<form method="post" rel="pagerForm" action="<c:url value='/management/opInfo'/>" onsubmit="return navTabSearch(this)">
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
<%--             <c:if test="${vo.status==1}"> --%>
            <li><a class="add" target="dialog" rel="opInfoAddNav" href="<c:url value='/management/opInfo/add'/>" mask="true" width="650" height="350" title="添加运营商"><span>添加</span></a></li>
<%--             </c:if> --%>
            <li><a class="edit" target="dialog" rel="opInfoEditNav" href="<c:url value='/management/opInfo/edit/{slt_objId}'/>" mask="true" width="650" height="350" title="编辑运营商"><span>编辑</span></a></li>
            <li><a class="delete" target="ajaxTodo" href="<c:url value='/management/opInfo/delete/{slt_objId}'/>" title="你确定要删除吗?"><span>删除</span></a></li>
<%--             <li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids" postType="string" href="<c:url value='/management/opInfo/deleteBatch/{ids}'/>" class="delete"  warn="请选择要删除的运营商"><span>批量删除</span></a></li>            --%>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="138">
        <thead>
            <tr>
                <th width="30"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
                <th width="30">序号</th>
                <th width="80" orderField="OpNo" class="${param.orderField eq 'OpNo' ? param.orderDirection : ''}">运营商编号</th>
                <th width="360" orderField="OpName" class="${param.orderField eq 'OpName' ? param.orderDirection : ''}">运营商名称</th>               
                <th width="140" orderField="CreateTime" class="${param.orderField eq 'CreateTime' ? param.orderDirection : ''}">创建时间</th>
                <th width="140" orderField="UpdateTime" class="${param.orderField eq 'UpdateTime' ? param.orderDirection : ''}">修改时间</th>
                <th orderField="Remark" class="${param.orderField eq 'Remark' ? param.orderDirection : ''}">备注</th>
                <th width="60">操作</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${beans}" varStatus="s">
            <tr target="slt_objId" rel="${item.id }">  
                <td><input name="ids" value="${item.id }" type="checkbox"></td>             
                <td>${s.index + 1}</td>
                <td>${item.opNo}</td>
                <td>${item.opName}</td>                          
                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${item.createTime}"/></td>
                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${item.updateTime}"/></td>
                <td>${item.remark}</td>
                <td>
                    <a title="删除" target="ajaxTodo" href="<c:url value='/management/opInfo/delete/${item.id}'/>" class="btnDel">删除</a>
                    <a title="编辑设备" target="dialog" rel="opInfoEditNav" href="<c:url value='/management/opInfo/edit/${item.id}'/>" mask="true" width="650" height="350" class="btnEdit">编辑</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    <c:import url="/management/frag/panelBar"></c:import>
</div>