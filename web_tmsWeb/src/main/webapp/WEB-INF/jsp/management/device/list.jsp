<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.inc.jsp"%>
<c:import url="/management/frag/pagerForm"></c:import>

<form method="post" rel="pagerForm" action="<c:url value='/management/device'/>" onsubmit="return navTabSearch(this)">
<div class="pageHeader">
    <div class="searchBar">
        <ul class="searchContent">
            <li>
                <label>关键词：</label>
                <input type="text" name="keywords" value="${param.keywords}"/>
            </li>
            <li>
                <label>设备类型：</label>
                <%-- <select class="combox" name="deviceType">
                    <option value="" ${empty bean.deviceType ? 'selected="selected"' : ''}>全部</option>
                    <option value="0" ${bean.deviceType == 0 ? 'selected="selected"' : ''}>翻译前端</option>
                    <option value="1" ${bean.deviceType == 1 ? 'selected="selected"' : ''}>翻译服务器</option>
                    <option value="2" ${bean.deviceType == 2 ? 'selected="selected"' : ''}>手机客户端</option>
                </select> --%>
                <select class="combox" name="deviceType">
                    <option value="">全部</option>
                    <c:forEach var="item" items="${deviceType}">
                        <option value="${item.key}" ${item.key eq vo.deviceType ? 'selected="selected"' : ''}>${item.value}</option>
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

<div class="pageContent">

    <div class="panelBar">
        <ul class="toolBar">
<%--             <c:if test="${vo.status==1}"> --%>
            <li><a class="add" target="dialog" rel="deviceAddNav" href="<c:url value='/management/device/add'/>" mask="true" width="750" height="450" title="添加"><span>添加</span></a></li>
<%--             </c:if> --%>
            <li><a class="edit" target="dialog" rel="deviceEditNav" href="<c:url value='/management/device/edit/{slt_objId}'/>" mask="true" width="750" height="${vo.status==2 ? "700": "450"}" title="编辑"><span>编辑</span></a></li>
            <li><a class="delete" target="ajaxTodo" href="<c:url value='/management/device/delete/{slt_objId}'/>" title="你确定要删除吗?"><span>删除</span></a></li>
            <li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids" postType="string" href="<c:url value='/management/device/deleteBatch/{ids}'/>" class="deleteAll"  warn="请选择要删除的设备"><span>批量删除</span></a></li>
            <li class="line">line</li>
            <li><a class="export" href="<c:url value='/management/device/exportExcel'/>" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
<%--             <c:if test="${vo.status==1}"> --%>
            <li><a class="import" target="dialog" width="450" height="300" rel="importExcelDialog" href="<c:url value='/management/device/importExcelDialog'/>" title="设备信息文件导入"><span>导入EXCEL</span></a></li>
<%--             </c:if> --%>
            <li class="line">line</li>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="138">
        <thead>
            <tr>
                <th width="30"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
                <th width="30">序号</th>
                <th width="150" orderField="DeviceName" class="${param.orderField eq 'DeviceName' ? param.orderDirection : ''}">设备名称</th>
                <th width="120" orderField="CustomerName" class="${param.orderField eq 'CustomerName' ? param.orderDirection : ''}">客户名</th>               
                <th width="80" orderField="DeviceType" class="${param.orderField eq 'DeviceType' ? param.orderDirection : ''}">设备类型</th>
                <th width="150" orderField="FeatureCode" class="${param.orderField eq 'FeatureCode' ? param.orderDirection : ''}">特征码/IMEI</th>                
                <th width="200" orderField="Address" class="${param.orderField eq 'Address' ? param.orderDirection : ''}">地址</th>
                <th width="80" orderField="Longitude" class="${param.orderField eq 'Longitude' ? param.orderDirection : ''}">经度</th>
                <th width="80" orderField="Latitude" class="${param.orderField eq 'Latitude' ? param.orderDirection : ''}">纬度</th>
                <th width="130" orderField="CreateTime" class="${param.orderField eq 'CreateTime' ? param.orderDirection : ''}">登记时间</th>
                <th orderField="Remark" class="${param.orderField eq 'Remark' ? param.orderDirection : ''}">备注</th>
                <th width="60">操作</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${beans}" varStatus="s">
            <tr target="slt_objId" rel="${item.id }">
                <td><input name="ids" value="${item.id }" type="checkbox"></td>
                <td>${s.index + 1}</td>
                <td>${item.deviceName}</td>
                <td>${item.customerName }
                </td>                                
             <%--    <c:choose>
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
                <td>${deviceType[item.deviceType]}</td>
                <td>${item.featureCode}</td>                
                <td>${item.address}</td>
                <td>${item.longitude}</td>
                <td>${item.latitude}</td>              
                <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${item.createTime}"/></td>
                <td>${item.remark}</td>
                <td>
                    <a title="删除" target="ajaxTodo" href="<c:url value='/management/device/delete/${item.id}'/>" class="btnDel">删除</a>
<%--                     <c:choose> --%>
<%--                         <c:when test="${not empty item.status and item.status==2}"> --%>
<%--                             <a title="编辑未配置设备" rel="unconfDeviceEditNav" target="dialog" href="management/device/edit/${item.id}" class="btnEdit" mask="true" width="750" height="750">编辑</a> --%>
<%--                         </c:when> --%>
<%--                         <c:otherwise> --%>
<%--                             <a title="编辑设备" target="dialog" rel="deviceEditNav" href="<c:url value='/management/device/edit/${item.id}'/>" mask="true" width="750" height="600" class="btnEdit">编辑</a> --%>
<%--                         </c:otherwise> --%>
<%--                     </c:choose> --%>
                    <a title="编辑设备" target="dialog" rel="deviceEditNav" href="<c:url value='/management/device/edit/${item.id}'/>" mask="true" width="750" height="450" class="btnEdit">编辑</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    <c:import url="/management/frag/panelBar"></c:import>
</div>