package com.smartclassroom;

public class RoomItem {
    private String roomTitle, levelTitle, buildingTitle;
    public  RoomItem(){}

    public RoomItem( String roomTitle, String levelTitle, String buildingTitle){
        this.roomTitle = roomTitle;
        this.levelTitle = levelTitle;
        this.buildingTitle = buildingTitle;
    }

    public String getRoomTitle() {
        return roomTitle;
    }

    public void setRoomTitle(String roomTitle) {
        this.roomTitle = roomTitle;
    }

    public String getLevelTitle() {
        return levelTitle;
    }

    public void setLevelTitle(String levelTitle) {
        this.levelTitle = levelTitle;
    }

    public String getBuildingTitle() {
        return buildingTitle;
    }

    public void setBuildingTitle(String buildingTitle) {
        this.buildingTitle = buildingTitle;
    }
}
