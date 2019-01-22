package com.irongteng.persistence;

import java.util.List;

public class CollisionAnalysisVO extends BaseConditionVO {
    
    private List<BaseConditionVO> deviceVoList;
    private String ids;
    private int deviceSize;
    
    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public List<BaseConditionVO> getDeviceVoList() {
        return deviceVoList;
    }

    public void setDeviceVoList(List<BaseConditionVO> deviceVoList) {
        this.deviceVoList = deviceVoList;
        this.deviceSize = this.deviceVoList.size();
    }

    public int getDeviceSize() {
        return deviceSize;
    }
}
