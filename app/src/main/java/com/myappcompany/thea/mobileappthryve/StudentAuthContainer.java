package com.myappcompany.thea.mobileappthryve;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudentAuthContainer {

    @SerializedName("studentauths")
    private List<StudentAuth> myStudentAuths;

    public StudentAuthContainer(List<StudentAuth> myStudentAuths) {
        this.myStudentAuths = myStudentAuths;
    }

    public List<StudentAuth> getMyStudentAuths() {
        return myStudentAuths;
    }
}
