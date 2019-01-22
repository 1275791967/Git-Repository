package com.irongteng.conf;

public final class CenterAndZoomConfig extends AbstractSystemConfig{
    
    private double longitude; //经度
    private double latitude;  //纬度
    private int zoom;          //显示级别
    
    public CenterAndZoomConfig() {
        
        try {
            this.loadSecionProperties("CenterAndZoom");
            
            setLongitude(Double.parseDouble(getProperty("center.longitude", "116.403875")));
            setLatitude(Double.parseDouble(getProperty("center.latitude","39.915168")));
            
            setZoom(Integer.parseInt(getProperty("zoom")));
        } catch(NumberFormatException e) {
            logger.info("设置地图显示中心点配置文件信息错误：" + e.getMessage());
        }
        
    }
    
    public double getLongitude() {
        return longitude;
    }
    
    public void setLongitude(double longitude) {
        setProperty("center.longitude", "" + longitude);
        this.longitude = longitude;
    }
    
    public double getLatitude() {
        return latitude;
    }
    
    public void setLatitude(double latitude) {
        setProperty("center.latitude", "" + latitude);
        this.latitude = latitude;
    }
    
    public int getZoom() {
        return zoom;
    }
    
    public void setZoom(int zoom) {
        setProperty("zoom", "" + zoom);
        this.zoom = zoom;
    }
    
    public static void main(String[] args) {
        CenterAndZoomConfig logs = new CenterAndZoomConfig();
        logs.setZoom(12);
        logs.save();
        System.out.println(logs.getLongitude());
        System.out.println(logs.getLatitude());
        System.out.println(logs.getZoom());
    }
}
