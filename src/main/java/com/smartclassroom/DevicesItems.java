package com.smartclassroom;

public class DevicesItems {

    private String name;
    private boolean sws;
    public DevicesItems(){}

    public DevicesItems(String name, boolean sws){
        this.name = name;
        this.sws = sws;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSws() {
        return sws;
    }

    public void setSws(boolean sws) {
        this.sws = sws;
    }
}


