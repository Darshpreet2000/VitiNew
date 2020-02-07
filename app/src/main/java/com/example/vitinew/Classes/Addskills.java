package com.example.vitinew.Classes;

public class Addskills {
    String skillname,rating;

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
