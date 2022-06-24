package com.germeny.pasqualesilvio.model;

public class IndiviModel {
    private int deviceimage, methodimage;
    private String indiviname, indivinumber, indivitemperature;

    public IndiviModel(int deviceimage, int methodimage, String indiviname, String indivinumber, String indivitemperature ){
        this.deviceimage = deviceimage;
        this.methodimage = methodimage;
        this.indiviname = indiviname;
        this.indivinumber = indivinumber;
        this.indivitemperature = indivitemperature;
    }

    public int getDeviceimage() {
        return deviceimage;
    }

    public String getIndiviname() {
        return indiviname;
    }

    public int getMethodimage() {
        return methodimage;
    }

    public String getIndivinumber() {
        return indivinumber;
    }

    public String getIndivitemperature() {
        return indivitemperature;
    }

    public void setDeviceimage(int deviceimage) {
        this.deviceimage = deviceimage;
    }

    public void setIndiviname(String indiviname) {
        this.indiviname = indiviname;
    }

    public void setIndivinumber(String indivinumber) {
        this.indivinumber = indivinumber;
    }

    public void setIndivitemperature(String indivitemperature) {
        this.indivitemperature = indivitemperature;
    }

    public void setMethodimage(int methodimage) {
        this.methodimage = methodimage;
    }
}
