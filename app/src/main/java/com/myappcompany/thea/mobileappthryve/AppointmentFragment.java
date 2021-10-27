package com.myappcompany.thea.mobileappthryve;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AppointmentFragment extends Fragment{
    public static final String ARG_STUDENT = "argStudentAccount";
    private StudentAccount currentUser;

    private Appointment newEmpAppointment;
    private EmploymentConsultantForm newEmpForm;

    private TextView aptResult;
    private Retrofit retrofit;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    private AppointmentViewModel appointmentViewModel;

    public static AppointmentFragment newAppointmentFragmentInstance(StudentAccount myStudent) {
        AppointmentFragment fragment = new AppointmentFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_STUDENT, myStudent);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //remove - return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_appointment, container, false);

        if (getArguments() != null) {
            currentUser = (StudentAccount) getArguments().get(ARG_STUDENT);
        }

        FloatingActionButton buttonBookEmpAppt = view.findViewById(R.id.button_add_new_appt);
        buttonBookEmpAppt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myConfirmAlert = new AlertDialog.Builder((getActivity()));
                myConfirmAlert.setTitle("Choose Appointment Type");
                myConfirmAlert.setMessage("Do you want to have an appointment with a Career Counselor or Employment Consultant?");
                myConfirmAlert.setPositiveButton("Career Counselor", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Fragment fragment = new AddCareerAppointmentFragment();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });
                myConfirmAlert.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                myConfirmAlert.setNegativeButton("Employment Consultant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Fragment fragment = new AddEmploymentAppointmentFragment();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });
                myConfirmAlert.show();
            }
        });

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_appt);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        //final AppointmentAdapter adapter = new AppointmentAdapter();
        //recyclerView.setAdapter(adapter);
        AppointmentAdapter adapter = new AppointmentAdapter();
        recyclerView.setAdapter(adapter);

        appointmentViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(AppointmentViewModel.class);
        System.out.println("1viewmodel null? " + (appointmentViewModel == null));

        appointmentViewModel.getAllAppointments().observe(this, new Observer<List<Appointment>>() {
            @Override
            public void onChanged(List<Appointment> appointments) {
                adapter.setAppointments(appointments);
                //adapter.setAppointments(appointments);
                //Toast.makeText(getActivity(),"Test Toast!", Toast.LENGTH_SHORT).show();
            }
        });
        System.out.println("2viewmodel null? " + (appointmentViewModel == null));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                appointmentViewModel.delete(adapter.getAppointmentAt(viewHolder.getAdapterPosition()));
                getFragmentManager().beginTransaction().detach(AppointmentFragment.this).attach(AppointmentFragment.this).commit();
            }
        }).attachToRecyclerView(recyclerView);

        //((MainActivity)getActivity(
        return view;
    }

    public void insertNewEmpAppointment (Appointment a, EmploymentConsultantForm b, StudentAccount c) {
        newEmpAppointment = a;
        newEmpForm = b;

        /*appointmentViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(AppointmentViewModel.class);*/

        System.out.println("1viewmodel null? " + (appointmentViewModel == null));
        System.out.println("viewmodel null? " + (appointmentViewModel == null));
        System.out.println("a null? what? " + a.toString());
        System.out.println("b null? " + b.getId());
        System.out.println("c null? " + c.getId());
        //appointmentViewModel.insert(1, b.getId(), 7, c.getId(), a);

        JsonPlaceHolderApi jsonPlaceHolderApi = RetrofitInstance.getApiService();

        Call<AppointmentContainer> call = jsonPlaceHolderApi.createAppointment(1, b.getId(), 7, c.getId(), a);
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
}