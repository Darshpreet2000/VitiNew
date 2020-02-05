package com.example.vitinew.Classes;

public class gigsClass {
    String cats;
    String campaign_title;
    String description;
    String brand;
    String created_at_timestamp;
    String updated_at;
    int per_cost;

    public int getPer_cost() {
        return per_cost;
    }

    public void setPer_cost(int per_cost) {
        this.per_cost = per_cost;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    String logo;
    int id,user_id;

    public gigsClass() {
    }

    public String getCats() {
        return cats;
    }

    public void setCats(String cats) {
        this.cats = cats;
    }

    public String getCampaign_title() {
        return campaign_title;
    }

    public void setCampaign_title(String campaign_title) {
        this.campaign_title = campaign_title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCreated_at_timestamp() {
        return created_at_timestamp;
    }

    public void setCreated_at_timestamp(String created_at_timestamp) {
        this.created_at_timestamp = created_at_timestamp;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
