package com.example.vitinew.Classes;

public class AddExp {
    String org,des,desc,start,end,id;

    public AddExp(String org, String des, String desc, String start, String end, String id) {
        this.org = org;
        this.des = des;
        this.desc = desc;
        this.start = start;
        this.end = end;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public AddExp(String org, String des, String desc, String start, String end) {
        this.org = org;
        this.des = des;
        this.desc = desc;
        this.start = start;
        this.end = end;
    }
}
