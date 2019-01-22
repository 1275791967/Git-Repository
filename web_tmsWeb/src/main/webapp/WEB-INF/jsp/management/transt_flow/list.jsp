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
                <label>类型：</label>
<!--                 采用map映射key value的形式 -->
                 <select class="combox" name="logTypeID">
                    <option value="">全部</option>
                    <c:forEach var="item" items="${dataTypes}">
                        <option value="${item.key}" ${item.key eq vo.logTypeID ? 'selected="selected"' : ''}>${item.value}</option>
                    </c:forEach>
                </select>                 
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
         <ul class="searchContent">          
            <li>
                <label>发起设备：</label>
                <input type="hidden" name="FromDeviceID" bringbackname="org.id" value="${vo.fromDeviceID}" >
                <input type="text"  name="FromDeviceName" bringbackname="org.deviceName" value="${vo.fromDeviceName}" autocomplete="off" lookupgroup="org" suggesturl="<c:url value='/management/device/lookupSuggest'/>" suggestfields="deviceName" postfield="keywords" lookuppk="id" />
                <a class="btnLook" href="<c:url value='/management/device/lookup'/>" lookupGroup="org" suggestfields="deviceName" autocomplete="off" postfield="keywords" lookuppk="id">查找带回</a>           
            </li>
            <li>
                <label>目的设备：</label>
                <input type="hidden" name="ToDeviceID" bringbackname="org1.id" value="${vo.toDeviceID}" >
                <input type="text"  name="ToDeviceName" bringbackname="org1.deviceName" value="${vo.toDeviceName}" autocomplete="off" lookupgroup="org1" suggesturl="<c:url value='/management/device/lookupSuggest'/>" suggestfields="deviceName" postfield="keywords" lookuppk="id" />
                <a class="btnLook" href="<c:url value='/management/device/lookup'/>" lookupGroup="org1" suggestfields="deviceName" autocomplete="off" postfield="keywords" lookuppk="id">查找带回</a>
            </li> 
            <li>
               <label>通道类型：</label>
                  <input type="hidden" name="OpInfoID" bringbackname="type.id" value="${vo.opInfoID}" >
                  <input type="text"  name="ChannelType" bringbackname="type.opName" value="${vo.channelType}" autocomplete="off" lookupgroup="type" suggesturl="<c:url value='/management/opInfo/lookupSuggest'/>" suggestfields="opName" postfield="keywords" lookuppk="id" />
                  <a class="btnLook" href="<c:url value='/management/opInfo/lookup'/>" lookupGroup="type" suggestfields="opName" autocomplete="off" postfield="keywords" lookuppk="id">查找带回</a>
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
            <li><a title="确实要删除这些记录吗?" target="selectedTodo" rel="ids" postType="string" href="<c:url value='/management/transt_flow/deleteBatch/{ids}'/>" class="deleteAll"><span>批量删除</span></a></li>
            <li class="line">line</li>
            <li><a class="export" href="<c:url value='/management/transt_flow/exportExcel'/>" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="163">
        <thead>
            <tr>
                <th width="30"><input type="checkbox" group="ids" class="checkboxCtrl"></th>
                <th width="40">序号</th>
                <th width="160" orderField="FromDeviceName" class="${param.orderField eq 'FromDeviceName' ? param.orderDirection : ''}">发起设备</th>
                <th width="160" orderField="ToDeviceName" class="${param.orderField eq 'ToDeviceName' ? param.orderDirection : ''}">目的设备</th>
                <th width="120"  orderField="LogTypeID" class="${param.orderField eq 'LogTypeID' ? param.orderDirection : ''}">类型</th>
                <th width="140" orderField="ChannelType" class="${param.orderField eq 'ChannelType' ? param.orderDirection : ''}">通道类型</th>
                <th width="120" orderField="Imsi" class="${param.orderField eq 'Imsi' ? param.orderDirection : ''}">IMSI</th>
                <th width="120" orderField="Imei" class="${param.orderField eq 'Imei' ? param.orderDirection : ''}">IMEI</th>
                <th width="120" orderField="Phone" class="${param.orderField eq 'Phone' ? param.orderDirection : ''}">电话</th>
                <th width="140" orderField="RecordTime" class="${param.orderField eq 'RecordTime' ? param.orderDirection : ''}">记录时间</th>
                <th orderField="Remark" class="${param.orderField eq 'Remark' ? param.orderDirection : ''}">备注</th>             
            </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${beans}" varStatus="s">
            <tr target="slt_objId" rel="${item.id }">
                <td><input name="ids" value="${item.id }" type="checkbox"></td>
                <td>${s.index + 1}</td>                
                <td>${item.fromDeviceName}</td>
                <td>${item.toDeviceName}</td>
                <td>${dataTypes[item.logTypeID]}</td>        
                <td>${item.channelType}</td>
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