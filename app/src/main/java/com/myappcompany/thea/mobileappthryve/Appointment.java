package com.myappcompany.thea.mobileappthryve;

import com.google.gson.annotations.SerializedName;

public class Appointment {

    private int id;

    private String title;

    @SerializedName("start_date")
    private String startDate;

    @SerializedName("end_date")
    private String endDate;

    @SerializedName("submission_date")
    private String submissionDate;

    @SerializedName("approval_date")
    private String approvalDate;

    private String description;

    @SerializedName("student_notes")
    private String studentNotes;

    @SerializedName("staff_notes")
    private String staffNotes;

    private String attachment1;

    private String attachment2;

    private String attachment3;

    private String status;

    @SerializedName("delete_appointment_row")
    private boolean deleteAppointmentRow;

    @SerializedName("student_id_id")
    private int studentId;

    @SerializedName("staff_id_id")
    private int staffId;

    @SerializedName("cc_form_id")
    private int ccId;

    @SerializedName("ec_form_id")
    private int ecId;

    public Appointment(String title, String startDate, String endDate, String submissionDate, String approvalDate, String description, String studentNotes, String staffNotes, String attachment1, String attachment2, String attachment3, String status, boolean deleteAppointmentRow) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.submissionDate = submissionDate;
        this.approvalDate = approvalDate;
        this.description = description;
        this.studentNotes = studentNotes;
        this.staffNotes = staffNotes;
        this.attachment1 = attachment1;
        this.attachment2 = attachment2;
        this.attachment3 = attachment3;
        this.status = status;
        this.deleteAppointmentRow = deleteAppointmentRow;

        this.id=1;
        this.studentId=1;
        this.staffId=1;
        this.ecId=1;
        this.ccId=1;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public String getApprovalDate() {
        return approvalDate;
    }

    public String getDescription() {
        return description;
    }

    public String getStudentNotes() {
        return studentNotes;
    }

    public String getStaffNotes() {
        return staffNotes;
    }

    public String getAttachment1() {
        return attachment1;
    }

    public String getAttachment2() {
        return attachment2;
    }

    public String getAttachment3() {
        return attachment3;
    }

    public String getStatus() {
        return status;
    }

    public boolean isDeleteAppointmentRow() {
        return deleteAppointmentRow;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getStaffId() {
        return staffId;
    }

    public int getCcId() {
        return ccId;
    }

    public int getEcId() {
        return ecId;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", submissionDate='" + submissionDate + '\'' +
                ", approvalDate='" + approvalDate + '\'' +
                ", description='" + description + '\'' +
                ", studentNotes='" + studentNotes + '\'' +
                ", staffNotes='" + staffNotes + '\'' +
                ", attachment1='" + attachment1 + '\'' +
                ", attachment2='" + attachment2 + '\'' +
                ", attachment3='" + attachment3 + '\'' +
                ", status='" + status + '\'' +
                ", deleteAppointmentRow=" + deleteAppointmentRow +
                ", studentId=" + studentId +
                ", staffId=" + staffId +
                ", ccId=" + ccId +
                ", ecId=" + ecId +
                '}';
    }
}
