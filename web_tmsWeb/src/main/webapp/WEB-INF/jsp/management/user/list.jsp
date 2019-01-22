<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.inc.jsp"%>
<c:import url="/management/frag/pagerForm"></c:import>

<form method="post" rel="pagerForm" action="<c:url value='/management/user'/>" onsubmit="return navTabSearch(this)">
<div class="pageHeader">
    <div class="searchBar">
        <ul class="searchContent">
            <li>
                <label>关键词：</label>
                <input type="text" name="keywords" value="${param.keywords}"/>
            </li>
            <li>
                <label>状态：</label>
                <select name="status" class="combox">
                    <option value="">全部</option>
                    <option value="ACTIVE" ${'ACTIVE' eq vo.status ? 'selected="selected"' : ''}>激活</option>
                    <option value="INACTIVE" ${'INACTIVE' eq vo.status ? 'selected="selected"' : ''}>未激活</option>
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

<div class="pageContent">

    <div class="panelBar">
        <ul class="toolBar">
            <li><a class="add" target="dialog" width="800" height="400" rel="userDialog" href="<c:url value='/management/user/add'/>" title="添加用户"><span>添加</span></a></li>
            <li><a class="edit" target="dialog" width="800" height="400" rel="userDialog" href="<c:url value='/management/user/edit/{slt_objId}'/>" title="编辑用户"><span>编辑</span></a></li>
            <li><a class="delete" target="ajaxTodo" href="<c:url value='/management/user/delete/{slt_objId}'/>" title="你确定要删除吗?"><span>删除</span></a></li>
            <li class="line">line</li>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="138">
        <thead>
            <tr>
                <th width="50"></th>
                <th width="100" orderField="USERNAME" class="${param.orderField eq 'USERNAME' ? param.orderDirection : ''}">用户名</th>
                <th width="140" orderField="Role" class="${param.orderField eq 'Role' ? param.orderDirection : ''}">角色</th>
                <th orderField="EMAIL" class="${param.orderField eq 'EMAIL' ? param.orderDirection : ''}">电子邮箱</th>
                <th width="130" orderField="PHONE" class="${param.orderField eq 'PHONE' ? param.orderDirection : ''}">联系方式</th>
                <th width="160" orderField="InsertDate" class="${param.orderField eq 'InsertDate' ? param.orderDirection : ''}">创建时间</th>
                <th width="120" orderField="Status" class="${param.orderField eq 'Status' ? param.orderDirection : ''}">状态</th>
                <th width="80">操作</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${userList}" varStatus="s">
            <tr target="slt_objId" rel="${item.id }">
                <td>${s.index + 1}</td>
                <td>${item.username}</td>
                <td>${item.role eq 'ADMIN_ROLE'? '管理员': item.role eq 'MANAGER_ROLE' ? '管理者': item.role eq 'USER_ROLE'? '一般用户': '没有此角色'}</td>
                <td>${item.email}</td>
                <td>${item.phone}</td>
                <td><fmt:formatDate value="${item.insertDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                <td>${'ACTIVE' eq item.status ? '激活' : '未激活'}</td>
                <td>
                    <a title="删除" target="ajaxTodo" href="management/user/delete/${item.id}" class="btnDel">删除</a>
                    <a title="编辑" target="dialog" href="management/user/edit/${item.id}" mask="true" width="800" height="400" class="btnEdit">编辑</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <c:import url="/management/frag/panelBar"></c:import>
</div>