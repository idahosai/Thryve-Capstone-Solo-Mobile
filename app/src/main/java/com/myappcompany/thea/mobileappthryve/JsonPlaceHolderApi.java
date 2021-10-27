package com.myappcompany.thea.mobileappthryve;

import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface JsonPlaceHolderApi {

    @GET
    Call<StudentContainer> getStudentAccounts(@Url String url);

    @GET
    Call<StudentAuthContainer> getStudentAuths(@Url String url);

    @GET
    Call<AppointmentContainer> getAppointments(@Url String url);

    @GET
    Call<SchoolProgramContainer> getAssignedSchoolProgram(@Url String url);

    @GET("appointments/list-day-appointments/{staff}/{y}/{m}/{d}")
    Call<AppointmentContainer> listDayAppointments(
            @Path("staff") int staffId,
            @Path("y") int year,
            @Path("m") int month,
            @Path("d") int day
    );

    @GET("appointments/list-user-appointments/{aptId}")
    Call<AppointmentContainer> listUserAppointments(
            @Path("aptId") int myId
    );

    //@POST("student/get-studentaccount")
    //Call<StudentAccount> createStudentAccount(@Body StudentAccount newStudentAccount);

    //@POST("studentauth/get-studentauth")
    //Call<StudentAuthContainer> createStudentAuth(@Body StudentAuthContainer newStudentAuth);

    /*@FormUrlEncoded
    @POST("student/insert-studentaccount/{auth_id}/{program_id}")
    Call<StudentContainer> createStudentAccount(
            @Path("auth_id") int newAuth,
            @Path("program_id") int newProg,
            @Field("fname") String firstName,
            @Field("lname") String lastName,
            @Field("email") String eMail,
            @Field("student_number") String studentNumber,
            @Field("program_year") String programYear,
            @Field("als") boolean als,
            @Field("coop") boolean coop,
            @Field("international") boolean international,
            @Field("phone_number") String phoneNumber
    );*/

    @POST("appointments/delete-mobile-appointment/{aptId}")
    Call<AppointmentContainer> deleteAppointment(
            @Path("aptId") int appointmentId
    );

    @POST("appointments/insert-appointment/{cc}/{ec}/{staf}/{stud}")
    Call<AppointmentContainer> createAppointment(
            @Path("cc") int newCCForm,
            @Path("ec") int newECForm,
            @Path("staf") int newStaff,
            @Path("stud") int newStudent,
            @Body Appointment newAppointment);

    @POST("student/insert-studentaccount/{auth_id}/{program_id}")
    Call<StudentContainer> createStudentAccount(
            @Path("auth_id") int newAuth,
            @Path("program_id") int newProg,
            @Body StudentAccount newStudent);

    @FormUrlEncoded
    @POST("studentauth/get-studentauth")
    Call<StudentAuthContainer> createStudentAuth(
            @Field("sheridan_id") String sheridanId,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("employmentform/get-employmentconsultantform")
    Call<EmploymentConsultantFormContainer> createNewEmploymentConsultantForm(
            @Field("q1e_sso") boolean checkQsso,
            @Field("q1e_friend") boolean checkQfriend,
            @Field("q1e_faculty") boolean checkQfaculty,
            @Field("q1e_visit") boolean checkQvisit,
            @Field("q1e_orient") boolean checkQorient,
            @Field("q1e_event") boolean checkQevent,
            @Field("q1e_kpi2") boolean checkQkpi2,
            @Field("q1e_outreach") boolean checkQoutreach,
            @Field("q1e_posters") boolean checkQposters,
            @Field("q1e_stv") boolean checkQstv,
            @Field("q1e_social") boolean checkQsocial,
            @Field("q1e_media") boolean checkQmedia,
            @Field("q1e_walkby") boolean checkQwalkby,
            @Field("q1e_website") boolean checkQwebsite,
            @Field("ecs_resume") boolean checkEresume,
            @Field("ecs_cover") boolean checkEcover,
            @Field("ecs_interview") boolean checkEinterview,
            @Field("ecs_jobsearch") boolean checkEjobsearch,
            @Field("ecs_mockinterview") boolean checkEmockinterview,
            @Field("ecs_networking") boolean checkEnetworking,
            @Field("ecs_portfolio") boolean checkEportfolio
    );

    @POST("employmentform/insert-employmentconsultantform")
    Call<EmploymentConsultantFormContainer> createEmploymentConsultantForm(
            @Body EmploymentConsultantForm newEmpForm
    );
}
