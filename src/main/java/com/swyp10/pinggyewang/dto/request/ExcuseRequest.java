package com.swyp10.pinggyewang.dto.request;

public class ExcuseRequest {

    private String situation;
    private String target;
    private String tone;

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public String getSituation(){
        return situation;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTarget() {
        return target;
    }

    public void setTone(String tone) {
        this.tone = tone;
    }

    public String getTone() {
        return tone;
    }
}
