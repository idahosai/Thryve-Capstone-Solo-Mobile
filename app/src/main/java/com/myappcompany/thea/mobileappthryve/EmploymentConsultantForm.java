package com.myappcompany.thea.mobileappthryve;

import com.google.gson.annotations.SerializedName;

public class EmploymentConsultantForm {
    private int id;

    @SerializedName("q1e_sso")
    private boolean q1eSso;

    @SerializedName("q1e_friend")
    private boolean q1eFriend;

    @SerializedName("q1e_faculty")
    private boolean q1eFaculty;

    @SerializedName("q1e_visit")
    private boolean q1eVisit;

    @SerializedName("q1e_orient")
    private boolean q1eOrient;

    @SerializedName("q1e_event")
    private boolean q1eEvent;

    @SerializedName("q1e_kpi2")
    private boolean q1eKpi2;

    @SerializedName("q1e_outreach")
    private boolean q1eOutreach;

    @SerializedName("q1e_posters")
    private boolean q1ePosters;

    @SerializedName("q1e_stv")
    private boolean q1eStv;

    @SerializedName("q1e_social")
    private boolean q1eSocial;

    @SerializedName("q1e_media")
    private boolean q1eMedia;

    @SerializedName("q1e_walkby")
    private boolean q1eWalkby;

    @SerializedName("q1e_website")
    private boolean q1eWebsite;

    @SerializedName("ecs_resume")
    private boolean ecsResume;

    @SerializedName("ecs_cover")
    private boolean ecsCover;

    @SerializedName("ecs_interview")
    private boolean ecsInterview;

    @SerializedName("ecs_jobsearch")
    private boolean ecsJobsearch;

    @SerializedName("ecs_mockinterview")
    private boolean ecsMockinterview;

    @SerializedName("ecs_networking")
    private boolean ecsNetworking;

    @SerializedName("ecs_portfolio")
    private boolean ecsPortfolio;

    public EmploymentConsultantForm(boolean q1eSso, boolean q1eFriend, boolean q1eFaculty, boolean q1eVisit, boolean q1eOrient, boolean q1eEvent, boolean q1eKpi2, boolean q1eOutreach, boolean q1ePosters, boolean q1eStv, boolean q1eSocial, boolean q1eMedia, boolean q1eWalkby, boolean q1eWebsite, boolean ecsResume, boolean ecsCover, boolean ecsInterview, boolean ecsJobsearch, boolean ecsMockinterview, boolean ecsNetworking, boolean ecsPortfolio) {
        this.q1eSso = q1eSso;
        this.q1eFriend = q1eFriend;
        this.q1eFaculty = q1eFaculty;
        this.q1eVisit = q1eVisit;
        this.q1eOrient = q1eOrient;
        this.q1eEvent = q1eEvent;
        this.q1eKpi2 = q1eKpi2;
        this.q1eOutreach = q1eOutreach;
        this.q1ePosters = q1ePosters;
        this.q1eStv = q1eStv;
        this.q1eSocial = q1eSocial;
        this.q1eMedia = q1eMedia;
        this.q1eWalkby = q1eWalkby;
        this.q1eWebsite = q1eWebsite;
        this.ecsResume = ecsResume;
        this.ecsCover = ecsCover;
        this.ecsInterview = ecsInterview;
        this.ecsJobsearch = ecsJobsearch;
        this.ecsMockinterview = ecsMockinterview;
        this.ecsNetworking = ecsNetworking;
        this.ecsPortfolio = ecsPortfolio;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isQ1eSso() {
        return q1eSso;
    }

    public boolean isQ1eFriend() {
        return q1eFriend;
    }

    public boolean isQ1eFaculty() {
        return q1eFaculty;
    }

    public boolean isQ1eVisit() {
        return q1eVisit;
    }

    public boolean isQ1eOrient() {
        return q1eOrient;
    }

    public boolean isQ1eEvent() {
        return q1eEvent;
    }

    public boolean isQ1eKpi2() {
        return q1eKpi2;
    }

    public boolean isQ1eOutreach() {
        return q1eOutreach;
    }

    public boolean isQ1ePosters() {
        return q1ePosters;
    }

    public boolean isQ1eStv() {
        return q1eStv;
    }

    public boolean isQ1eSocial() {
        return q1eSocial;
    }

    public boolean isQ1eMedia() {
        return q1eMedia;
    }

    public boolean isQ1eWalkby() {
        return q1eWalkby;
    }

    public boolean isQ1eWebsite() {
        return q1eWebsite;
    }

    public boolean isEcsResume() {
        return ecsResume;
    }

    public boolean isEcsCover() {
        return ecsCover;
    }

    public boolean isEcsInterview() {
        return ecsInterview;
    }

    public boolean isEcsJobsearch() {
        return ecsJobsearch;
    }

    public boolean isEcsMockinterview() {
        return ecsMockinterview;
    }

    public boolean isEcsNetworking() {
        return ecsNetworking;
    }

    public boolean isEcsPortfolio() {
        return ecsPortfolio;
    }
}

