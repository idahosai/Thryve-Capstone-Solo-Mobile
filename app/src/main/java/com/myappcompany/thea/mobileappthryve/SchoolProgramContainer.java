package com.myappcompany.thea.mobileappthryve;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SchoolProgramContainer {
    @SerializedName("schoolprogram")
    private List<SchoolProgram> mySchoolPrograms;

    public List<SchoolProgram> getMySchoolPrograms() {
        return mySchoolPrograms;
    }
}
