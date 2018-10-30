package com.daka.entry;

public class AppPictureVO {
    private Integer id;
    private String pictureImg;
    private Integer type;
    private String picExplain;
    private String maintenanceTime;

    public Integer getId() {
        return id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPictureImg() {
        return pictureImg;
    }

    public void setPictureImg(String pictureImg) {
        this.pictureImg = pictureImg;
    }

    public String getPicExplain() {
        return picExplain;
    }

    public void setPicExplain(String picExplain) {
        this.picExplain = picExplain;
    }

    public String getMaintenanceTime() {
        return maintenanceTime;
    }

    public void setMaintenanceTime(String maintenanceTime) {
        this.maintenanceTime = maintenanceTime;
    }


}
