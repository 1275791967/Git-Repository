<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>

<script type="text/javascript" src="<c:url value='/styles/js/device/device.js'/>"></script>

<div class="pageContent" id="form_wrapper">
    <form method="post" action="<c:url value='/management/device/${addOrUpdate==1 ? "insert": "update"}?navTabId=${bean.status==2 ? "unconfDeviceLiNav":"deviceListNav"}&callbackType=closeCurrent'/>" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
        
        <c:if test="${addOrUpdate==2 and not empty bean.id}">
            <input type="hidden" name="id" value="${bean.id}" />
        </c:if>
        <input type="hidden" name="status" value="${bean.status}" />
        
        <div class="pageFormContent nowrap" layoutH="57">
            <fieldset>
                <legend>设备基本信息</legend>
                <dl>
                    <dt>标记名称: </dt>
                    <dd>
                        <input type="text" name="deviceName" value="${bean.deviceName}" class="required" minlength="2" maxlength="200"/>
                    </dd>
                </dl>
                <dl>
                    <dt>设备编号: </dt><!-- 设备编号:  -->
                    <dd>
                        <input type="text" name="siteNumber" value="${bean.siteNumber}" class="required number" min="0" max="4294967295" minlength="1" maxlength="40"/>
                    </dd>
                </dl>
                <dl>
                    <dt>设备类型: </dt>
                    <dd>
                        <select name="categoryID" id="categoryID">
                            <c:forEach var="item" items="${catetoryMaps}">
                                <option value="${item.key}" ${item.key eq bean.categoryID ? 'selected="selected"' : ''}>${item.value}</option>
                            </c:forEach>
                        </select>
                    </dd>
                </dl>
                <dl>
                    <dt id="deviceNo-title"></dt><!-- 设备编号/城市代码:  -->
                    <dd>
                        <input type="text" name="deviceNumber" value="${bean.deviceNumber}" class="required" minlength="1" maxlength="40"/>
                    </dd>
                </dl>
                <dl>
                    <dt id="deviceName-title">: </dt><!-- MAC地址/设备名称 -->
                    <dd>
                        <input type="text" name="attachNumber" value="${bean.attachNumber}" class="required" minlength="1" maxlength="30"/>
                    </dd>
                </dl>
            </fieldset>

            <fieldset>
                <legend>通信设置</legend>
                <dl>
                    <dt>通信类型: </dt>
                    <dd>
                        <select name="commCategory" id="commCategory">
                            <c:forEach var="item" items="${commMaps}">
                                <option value="${item.key}" ${item.key eq bean.commCategory ? 'selected="selected"' : ''}>${item.value}</option>
                            </c:forEach>
                        </select>
                    </dd>
                </dl>
                <dl>
                    <dt>IP地址: </dt>
                    <dd>
                        <input type="text" name="ipAddress" value="${not empty bean.ipAddress ? bean.ipAddress : ''}" class="required"/>
                    </dd>
                </dl>
                <dl>
                    <dt>端口号：</dt>
                    <dd>
                        <input type="text" name="port" id="port" value="${not empty bean.port ? bean.port : ''}" class="required digits" min="0" max="65535" maxlength="5" />
                    </dd>
                </dl>
            </fieldset>
            <fieldset>
                <legend>归属站点设置</legend>
                <c:choose>
                    <c:when test="${bean.status==2}">
                        <dl>
                            <dt>站点名称: </dt>
                            <dd>
                                <input type="hidden" name="siteID" bringbackname="location.id" value="${not empty bean.siteID ? bean.siteID : ''}" />
                                <input type="text" suggestFields="siteName" postField="keywords" suggestUrl="<c:url value='/management/site/lookupSuggest'/>" lookupGroup="location" name="siteName" bringbackname="location.siteName" value="${not empty bean.siteName ? bean.siteName : ''}" class="required" maxlength="200" />
                                <a class="btnLook" href="<c:url value='/management/site/lookup'/>" width='800' height='600' lookupGroup="location">查找带回</a>
                            </dd>
                        </dl>
                        <div class="divider"></div>
                        <dl>
                            <dt>经度: </dt>
                            <dd>
                                <input type="text" name="longitude" bringbackname="location.longitude" value="${not empty bean.longitude ? bean.longitude : ''}" class="required number" min="-180" max="180" maxlength="100"/>
                                <a class="btnLook" href="<c:url value='/management/load_lookup_map'/>" lookupGroup="location">查找带回</a>
                            </dd>
                        </dl>
                        <dl>
                            <dt>纬度: </dt>
                            <dd>
                                <input type="text" name="latitude" bringbackname="location.latitude" value="${not empty bean.latitude ? bean.latitude : ''}" class="required number" min="-90" max="90" maxlength="30"/>
                            </dd>
                        </dl>
                        <dl>
                            <dt>地址:</dt>
                            <dd>
                                <input type="text" name="address" bringbackname="location.address" value="${bean.address}" maxlength="200" size="40" />
                            </dd>
                        </dl>
                        <div class="divider"></div>
                        <dl>
                            <dt>详情：</dt>
                            <dd>
                                <textarea name="remark" bringbackname="location.remark" rows="6" cols="50">${not empty bean.remark ? bean.remark : ''}</textarea>
                            </dd>
                        </dl>
                    </c:when>
                    <c:otherwise>
                        <dl>
                            <dt>站点名称: </dt>
                            <dd>
                                <input type="hidden" name="siteID" bringbackname="location.id" value="${not empty bean.siteID ? bean.siteID : ''}" />
                                <input type="text" suggestFields="siteName"  name="siteName" bringbackname="location.siteName" value="${not empty bean.siteName ? bean.siteName : ''}" class="required readonly" readonly="readonly" maxlength="200" />
                                <a class="btnLook" href="<c:url value='/management/site/lookup'/>" width='800' height='600' lookupGroup="location">查找带回</a>
                            </dd>
                        </dl>
                        <div class="divider"></div>
                        <dl>
                            <dt>经度: </dt>
                            <dd>
                                <input type="text" name="longitude" bringbackname="location.longitude" value="${not empty bean.longitude ? bean.longitude : ''}" class="required number readonly" readonly="readonly" min="-180" max="180" maxlength="100"/>
                            </dd>
                        </dl>
                        <dl>
                            <dt>纬度: </dt>
                            <dd>
                                <input type="text" name="latitude" bringbackname="location.latitude" value="${not empty bean.latitude ? bean.latitude : ''}" class="required number readonly" readonly="readonly" min="-90" max="90" maxlength="30"/>
                            </dd>
                        </dl>
                        <dl>
                            <dt>地址:</dt>
                            <dd>
                                <input type="text" name="address" bringbackname="location.address" value="${bean.address}" class="required readonly" readonly="readonly" maxlength="200" size="40" />
                            </dd>
                        </dl>
                    </c:otherwise>
                </c:choose>                
            </fieldset>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
            </ul>
        </div>
    </form>
</div>