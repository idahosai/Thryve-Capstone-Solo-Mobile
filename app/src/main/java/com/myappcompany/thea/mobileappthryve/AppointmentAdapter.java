package com.myappcompany.thea.mobileappthryve;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentHolder> {
    private List<Appointment> appointmentList = new ArrayList<>();

    /*public AppointmentAdapter(List<Appointment> appts) {
        appointmentList = appts;
    }*/

    @NonNull
    @Override
    public AppointmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appt_item, parent, false);
        return new AppointmentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentHolder holder, int position) {
        Appointment currentAppointment = appointmentList.get(position);
        holder.textViewTitle.setText(currentAppointment.getTitle());

        String formattedTime = (currentAppointment.getStartDate()).substring(0, 10) + " " + (currentAppointment.getStartDate()).substring(11, 16);
        holder.textViewStart.setText(formattedTime);
        //holder.textViewStatus.setText(Integer.toString(currentAppointment.getId()));
        holder.textViewStatus.setText(currentAppointment.getStatus());
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public void setAppointments(List<Appointment> appointments) {
        appointmentList = appointments;
        notifyDataSetChanged();
    }

    public Appointment getAppointmentAt(int position) {
        return appointmentList.get(position);
    }

    class AppointmentHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewStart;
        private TextView textViewStatus;

        public AppointmentHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewStart = itemView.findViewById(R.id.text_view_start);
            textViewStatus = itemView.findViewById(R.id.text_view_status);
        }
    }
}
