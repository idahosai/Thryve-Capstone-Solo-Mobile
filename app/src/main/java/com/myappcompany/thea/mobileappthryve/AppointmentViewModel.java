package com.myappcompany.thea.mobileappthryve;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class AppointmentViewModel extends AndroidViewModel {
    private AppointmentRepository appointmentRepository;

    public AppointmentViewModel(@NonNull Application application) {
        super(application);

        appointmentRepository = new AppointmentRepository(application);
    }

    public LiveData<List<Appointment>> getAllAppointments() {
        return appointmentRepository.getMutableLiveData();
    }

    public void insert(int cc_id, int ec_id, int staf_id, int stud_id, @Nullable Appointment apt) {
        System.out.println("cc_id: " + cc_id);
        System.out.println("ec_id: "+ ec_id);
        System.out.println("staf_id: " + staf_id);
        System.out.println("Apt: " + apt.getId());
        System.out.println("Apt: " + apt.getStartDate());
        appointmentRepository.insertNewAppointmentFromEC(cc_id, ec_id, staf_id, stud_id, apt);
    }

    public void delete(Appointment a) {
        appointmentRepository.deleteAppointment(a.getId());
    }
}
