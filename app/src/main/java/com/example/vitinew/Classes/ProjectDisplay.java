package com.example.vitinew.Classes;

import java.io.Serializable;

public class ProjectDisplay implements Serializable {
    int id;
    String image,title,des,cat,start,end,duration,stipend,benefits,place,count,skill,
    proofs,user,created_at,updated_at,CompanyName,AboutCompany,Position,WorkPlace,AboutProject;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAboutProject() {
        return AboutProject;
    }

    public void setAboutProject(String aboutProject) {
        AboutProject = aboutProject;
    }

    public String getWorkPlace() {
        return WorkPlace;
    }

    public void setWorkPlace(String workPlace) {
        WorkPlace = workPlace;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getAboutCompany() {
        return AboutCompany;
    }

    public void setAboutCompany(String aboutCompany) {
        AboutCompany = aboutCompany;
    }

    public ProjectDisplay() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStipend() {
        return stipend;
    }

    public void setStipend(String stipend) {
        this.stipend = stipend;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String position) {
        Position = position;
    }

    public String getBenifits() {
        return benefits;
    }

    public void setBenifits(String benifits) {
        this.benefits = benifits;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getProofs() {
        return proofs;
    }

    public void setProofs(String proofs) {
        this.proofs = proofs;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
