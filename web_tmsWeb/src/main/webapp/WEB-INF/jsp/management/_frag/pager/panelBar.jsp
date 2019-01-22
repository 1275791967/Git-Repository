<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.inc.jsp"%>

<div class="panelBar">
    <div class="pages">
        <span>显示</span>
        <select name="pageSize" onchange="dwzPageBreak({targetType:'${targetType}',data:{numPerPage:this.value}})">
            <c:forEach begin="10" end="100" step="10" varStatus="s">
                <option value="${s.index}" ${vo.pageSize eq s.index ? 'selected="selected"' : ''}>${s.index}</option>
            </c:forEach>
        </select>
        
        <span>总共: ${vo.totalCount}条</span>
    </div>
    
    <div class="pagination" targetType="${targetType}" totalCount="${vo.totalCount}" numPerPage="${vo.pageSize}" pageNumShown="10" currentPage="${vo.pageNum}"></div>
</div>