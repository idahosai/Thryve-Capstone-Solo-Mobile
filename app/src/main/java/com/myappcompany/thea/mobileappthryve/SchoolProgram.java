package com.myappcompany.thea.mobileappthryve;

import com.google.gson.annotations.SerializedName;

public class SchoolProgram {
    private int id;

    @SerializedName("program_code")
    private String programCode;

    @SerializedName("program_title")
    private String programTitle;

    @SerializedName("faculty_code")
    private String facultyCode;

    @SerializedName("educ_level")
    private String educLevel;

    public SchoolProgram(String programCode, String programTitle, String facultyCode, String educLevel) {
        this.programCode = programCode;
        this.programTitle = programTitle;
        this.facultyCode = facultyCode;
        this.educLevel = educLevel;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getProgramCode() {
        return programCode;
    }

    public String getProgramTitle() {
        return programTitle;
    }

    public String getFacultyCode() {
        return facultyCode;
    }

    public String getEducLevel() {
        return educLevel;
    }
}
