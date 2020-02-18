package com.example.vitinew.Classes;

public class Addskills {
    String skillname,rating,id;

    public Addskills(String skillname, String rating, String id) {
        this.skillname = skillname;
        this.rating = rating;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Addskills(String skillname, String rating) {
        this.skillname = skillname;
        this.rating = rating;
    }

    public String getSkillname() {
        return skillname;
    }

    public void setSkillname(String skillname) {
        this.skillname = skillname;
    }


    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
