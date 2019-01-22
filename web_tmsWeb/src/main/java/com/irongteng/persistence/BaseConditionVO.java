package com.irongteng.persistence;

public class BaseConditionVO {
   
    public final static int PAGE_SHOW_COUNT = 20;
    private int pageNum = 1;    //页码数
    private int pageSize = 0;   //每次查询数
    private int totalCount = 0; //总数
    private String orderField;
    private String orderDirection;
    private String keywords;
    private String status;
    private String type;
    private String content;
    private String startDate;
    private String endDate;
    private Integer userID;
    private Integer siteID;
    private String siteName;
//    private Integer deviceID;
//    private String deviceName;
    private Integer protocolID;  //协议类型
    private Integer monitorID;
    private String monitorName;
//    private Integer customerID;
    private Integer behaviorID;
    private String balanceType;
   
    
//    private String mac;
//    private String imsi;
//    private String imei;
   

    
    
    public String getType() {
        return "".equals(type) ? null : type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return "".equals(status) ? null : status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getSiteID() {
        return siteID;
    }

    public void setSiteID(Integer siteID) {
        this.siteID = siteID;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

//    public Integer getDeviceID() {
//        return deviceID;
//    }
//
//    public void setDeviceID(Integer deviceID) {
//        this.deviceID = deviceID;
//    }
//
//    public String getDeviceName() {
//        return deviceName;
//    }
//
//    public void setDeviceName(String deviceName) {
//        this.deviceName = deviceName;
//    }

    public Integer getProtocolID() {
        return protocolID;
    }

    public void setProtocolID(Integer protocolID) {
        this.protocolID = protocolID;
    }

    public Integer getMonitorID() {
        return monitorID;
    }

    public void setMonitorID(Integer monitorID) {
        this.monitorID = monitorID;
    }

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }
    
//    public String getMac() {
//        return mac;
//    }
//
//    public void setMac(String mac) {
//        this.mac = mac;
//    }
//
//    public String getImsi() {
//        return imsi;
//    }
//
//    public void setImsi(String imsi) {
//        this.imsi = imsi;
//    }
//
//    public String getImei() {
//        return imei;
//    }

//    public void setImei(String imei) {
//        this.imei = imei;
//    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize > 0 ? pageSize : PAGE_SHOW_COUNT;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getOrderDirection() {
        return "desc".equals(orderDirection) ? "desc" : "asc";
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getKeywords() {
        return "".equals(keywords) ? null : keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

   
//    public Integer getCustomerID() {
//        return customerID;
//    }
//
//    public void setCustomerID(Integer customerID) {
//        this.customerID = customerID;
//    }

    public Integer getBehaviorID() {
        return behaviorID;
    }

    public void setBehaviorID(Integer behaviorID) {
        this.behaviorID = behaviorID;
    }

    public String getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(String balanceType) {
        this.balanceType = balanceType;
    }

    public static int getPageShowCount() {
        return PAGE_SHOW_COUNT;
    }
    

    public int getStartIndex() {
        int pageNum = this.getPageNum() > 0 ? this.getPageNum() - 1 : 0;
        return pageNum * this.getPageSize();
    }
}
