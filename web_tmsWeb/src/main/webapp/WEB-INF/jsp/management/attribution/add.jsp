<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/include.inc.jsp"%>
<script src="/styles/js/Mock/Mock.js" type="text/javascript"></script>
<script src="/styles/js/Mock/jquery.mockjax.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
var data = Mock.mock('/management/attribution/add',{
    'userName|3':'fei',//重复fei这个字符串 3 次，打印出来就是'feifeifei'。
    'pwd|2-5':'12',//重复jiang这个字符串 2-5 次。
    /*'big|+1':0, //属性值自动加 1，初始值为 0
    'age|20-30':25,//生成一个大于等于 20、小于等于 30 的整数，属性值 25 只是用来确定类型
    'weight|100-120.2-5':110.24,//生成一个浮点数,整数部分大于等于 100、小于等于 120，小数部分保留 2 到 5 位。
    'likeMovie|1':Boolean,//随机生成一个布尔值，值为 true 的概率是 1/2，值为 false 的概率同样是 1/2。
    'friend1|1':arr,//从数组 arr 中随机选取 1 个元素，作为最终值。
    'friend2|+1':arr,//从属性值 arr 中顺序选取 1 个元素，作为最终值
    'friend3|2-3':arr,//通过重复属性值 arr 生成一个新数组，重复次数大于等于 2，小于等于 3。
    'life1|2':obj,//从属性值 obj 中随机选取 2 个属性
    'life1|1-2':obj,//从属性值 obj 中随机选取 1 到 2 个属性。
    'regexp1':/^[a-z][A-Z][0-9]$/,//生成的符合正则表达式的字符串 */   
});
$.ajax({
    url:'/management/attribution/add',
    dataType:'json',
    success:function(data){
    	return data;
       /*  console.log(data) */
    }
});
});

</script>

<div class="pageContent">
<form method="post" action="<c:url value='/management/attribution/insert?navTabId=AttributionLiNav&callbackType=closeCurrent'/>" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
    <div class="pageFormContent" layoutH="57">
        <dl>
            <dt>号码片段(前7位): </dt>
            <dd>
                <input type="text" name="phoneNO" value="${bean.phoneNO}" class="required digits" minlength="4" maxlength="200"/>
            </dd>
        </dl>
        <dl>
            <dt>所属省: </dt>
            <dd>
                <input type="text" name="province" value="${bean.province}" class="required" minlength="2" maxlength="40"/>
            </dd>
        </dl>
        
        <dl>
            <dt>所属城市: </dt>
            <dd>
                <input type="text" name="city" value="${bean.city}" class="required" maxlength="30"/>
            </dd>
        </dl>
        <dl>
            <dt>号码前缀: </dt>
            <dd>
                <input type="text" name="cellNO" value="${bean.cellNO}" class="digits" maxlength="30"/>
            </dd>
        </dl>
        
        <div class="divider"></div>
        <dl>
            <dt>详情：</dt>
            <dd>
                <textarea name="remark" rows="6" cols="50"></textarea>
            </dd>
        </dl>

    </div>
    <div class="formBar">
        <ul>
            <li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
            <li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
        </ul>
    </div>
</form>
</div>
