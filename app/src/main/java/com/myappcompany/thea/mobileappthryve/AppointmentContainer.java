package com.myappcompany.thea.mobileappthryve;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AppointmentContainer {

    @SerializedName("appointments")
    private List<Appointment> myAppointments;

    public List<Appointment> getMyAppointments() {
        return myAppointments;
    }

}
