package com.myappcompany.thea.mobileappthryve;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentRepository {
    private ArrayList<Appointment> appointments = new ArrayList<>();
    private MutableLiveData<List<Appointment>> mutableLiveData = new MutableLiveData<>();
    private Application application;

    public AppointmentRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Appointment>> getMutableLiveData() {
        JsonPlaceHolderApi jsonPlaceHolderApi = RetrofitInstance.getApiService();

        Call<AppointmentContainer> call = jsonPlaceHolderApi.getAppointments("appointments/get-appointment");
        call.enqueue(new Callback<AppointmentContainer>() {
            @Override
            public void onResponse(Call<AppointmentContainer> call, Response<AppointmentContainer> response) {
                if(!response.isSuccessful()) {
                    System.out.println("This is an error at getMutableLiveData! " + response.code());
                    return;
                }

                AppointmentContainer container = response.body();
                if (container != null && container.getMyAppointments() != null) {
                    appointments = (ArrayList<Appointment>) container.getMyAppointments();
                    mutableLiveData.setValue(appointments);
                }
            }

            @Override
            public void onFailure(Call<AppointmentContainer> call, Throwable t) {
                System.out.println("THIS IS A BIG ERROR AGAIN!");
            }
        });
        return mutableLiveData;
    }

    public void insertNewAppointmentFromEC(int cc_id, int ec_id, int staf_id, int stud_id, Appointment apt) {
        JsonPlaceHolderApi jsonPlaceHolderApi = RetrofitInstance.getApiService();

        Call<AppointmentContainer> call = jsonPlaceHolderApi.createAppointment(cc_id, ec_id, staf_id, stud_id, apt);
        call.enqueue(new Callback<AppointmentContainer>() {
            @Override
            public void onResponse(Call<AppointmentContainer> call, Response<AppointmentContainer> response) {
                if(!response.isSuccessful()) {
                    System.out.println("This is an error at insertNewAppointmentFromEC! " + response.code());
                    return;
                }

                System.out.println("SUCCESSFUL INSERT HERE!");
            }

            @Override
            public void onFailure(Call<AppointmentContainer> call, Throwable t) {

            }
        });
    }

    public void deleteAppointment(int aptId) {
        JsonPlaceHolderApi jsonPlaceHolderApi = RetrofitInstance.getApiService();

        Call<AppointmentContainer> call = jsonPlaceHolderApi.deleteAppointment(aptId);
        call.enqueue(new Callback<AppointmentContainer>() {
            @Override
            public void onResponse(Call<AppointmentContainer> call, Response<AppointmentContainer> response) {
                if(!response.isSuccessful()) {
                    System.out.println("ERROR at DELETE! " + response.code());
                    return;
                }

                System.out.println("SUCCESSFUL INSERT HERE!");
            }

            @Override
            public void onFailure(Call<AppointmentContainer> call, Throwable t) {

            }
        });
    }

    /*public void insertNewAppointmentFromEC(int ec_form, int cc_form, int myStaff, int myStudent, Appointment newApppointment) {
        JsonPlaceHolderApi jsonPlaceHolderApi = RetrofitInstance.getApiService();

        Call<AppointmentContainer> call = jsonPlaceHolderApi.createAppointment(cc_form, ec_form, myStaff, myStudent, newApppointment);
        call.enqueue(new Callback<AppointmentContainer>() {
            @Override
            public void onResponse(Call<AppointmentContainer> call, Response<AppointmentContainer> response) {
                System.out.println("THIS IS AN ERROR FROM insertNewAppoinmentfromEC!");
                return;
            }

            @Override
            public void onFailure(Call<AppointmentContainer> call, Throwable t) {

            }
        });
    }*/



    /*public MutableLiveData<List<Blog>> getMutableLiveData() {

        RestApiService apiService = RetrofitInstance.getApiService();
        Call<BlogWrapper> call = apiService.getPopularBlog();
        call.enqueue(new Callback<BlogWrapper>() {
            @Override
            public void onResponse(Call<BlogWrapper> call, Response<BlogWrapper> response) {
                if(!response.isSuccessful()) {
                    System.out.println("THIS IS AN ERROR AGAIN!");
                    return;
                }

                BlogWrapper mBlogWrapper = response.body();
                if (mBlogWrapper != null && mBlogWrapper.getBlog() != null) {
                    movies = (ArrayList<Blog>) mBlogWrapper.getBlog();
                    mutableLiveData.setValue(movies);
                }
            }
            @Override
            public void onFailure(Call<BlogWrapper> call, Throwable t) {
                System.out.println("THIS IS AN ERROR!");
            }
        });
        return mutableLiveData;
    }*/
}
