<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>
<div class="pageContent" layoutH="00">
    
    <table class="table" width="100%" layoutH="30">
        <thead>
            <tr>
                <th width="100">参数列</th>
                <th>参数值</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>标记名称: </td>
                <td>${bean.deviceName}</td>
            </tr>
            <tr>
                <td>设备编号: </td>
                <td>${bean.siteNumber}</td>
            </tr>
            <tr>
                <td>站点归属: </td>
                <td>${bean.siteName}</td>
            </tr>
            <tr>
                <td>${'1' eq bean.categoryID ? '城市代码' : '设备编号'}: </td>
                <td>${bean.deviceNumber}</td>
            </tr>
            
            <tr>
                <td>${'1' eq bean.categoryID ? '设备名称' : 'MAC地址'}: </td>
                <td>${bean.attachNumber}</td>
            </tr>
            <tr>
                <td>设备类型: </td>
                <td>${bean.categoryName}</td>
            </tr>
            
            <tr>
                <td>通信类型: </td>
                <td>${bean.commName}</td>
            </tr>
            <tr>
                <td>IP地址: </td>
                <td>${bean.ipAddress}</td>
            </tr>
            <tr>
                <td>端口号：</td>
                <td>${bean.port}</td>
            </tr>
        </tbody>
    </table>
</div>