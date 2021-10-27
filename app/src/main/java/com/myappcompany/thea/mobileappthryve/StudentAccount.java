package com.myappcompany.thea.mobileappthryve;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StudentAccount implements Serializable {
    private int id;

    @SerializedName("fname")
    private String firstName;

    @SerializedName("lname")
    private String lastName;

    @SerializedName("email")
    private String emailAddress;

    @SerializedName("student_number")
    private String studentNumber;

    @SerializedName("program_year")
    private int programYear;

    private boolean als;

    private boolean coop;

    private String gpa;

    @SerializedName("education_level")
    private String educationLevel;

    private boolean international;

    @SerializedName("program_id_id")
    private int programId;

    @SerializedName("auth_id_id")
    private int authId;

    @SerializedName("phone_number")
    private String phoneNumber;

    @SerializedName("student_status")
    private String studentStatus;

    public StudentAccount(String firstName, String lastName, String emailAddress, String studentNumber, int programYear, boolean als, boolean coop, String gpa, String educationLevel, boolean international, String phoneNumber, String studentStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.studentNumber = studentNumber;
        this.programYear = programYear;
        this.als = als;
        this.coop = coop;
        this.gpa = gpa;
        this.educationLevel = educationLevel;
        this.international = international;
        this.phoneNumber = phoneNumber;
        this.studentStatus = studentStatus;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public int getProgramYear() {
        return programYear;
    }

    public boolean isAls() {
        return als;
    }

    public boolean isCoop() {
        return coop;
    }

    public String getGpa() {
        return gpa;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public boolean isInternational() {
        return international;
    }

    public int getProgramId() {
        return programId;
    }

    public int getAuthId() {
        return authId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getStudentStatus() { return studentStatus; }

    public void setId(int id) {
        this.id = id;
    }
}
