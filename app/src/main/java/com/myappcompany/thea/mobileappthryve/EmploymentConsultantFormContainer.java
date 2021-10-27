package com.myappcompany.thea.mobileappthryve;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EmploymentConsultantFormContainer {

    @SerializedName("employmentconsultantforms")
    private List<EmploymentConsultantForm> myEmploymentConsultantForms;

    public List<EmploymentConsultantForm> getMyEmploymentConsultantForms() {
        return myEmploymentConsultantForms;
    }
}
