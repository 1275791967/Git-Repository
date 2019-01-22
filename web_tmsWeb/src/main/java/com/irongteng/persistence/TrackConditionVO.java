package com.irongteng.persistence;

import java.util.ArrayList;
import java.util.List;

public class TrackConditionVO extends BaseConditionVO {
    
    private List<BaseConditionVO> deviceVoList = new ArrayList<>();
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
        this.deviceVoList = deviceVoList!=null ? deviceVoList : new ArrayList<>();
        this.deviceSize = this.deviceVoList.size();
    }
    
    public int getDeviceSize() {
        return deviceSize;
    }
}
