package com.myappcompany.thea.mobileappthryve;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AddCareerAppointmentFragment extends Fragment {

    private EditText editTextCarTitle;
    private EditText editTextCarDescription;
    private EditText editTextCarDate;
    private DatePickerDialog datePickerDialogCarDate;
    private Spinner spinnerCarTime;
    private EditText editTextCarStudentNotes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_add, container, false);

        editTextCarTitle = (EditText) view.findViewById(R.id.edit_text_car_title);
        editTextCarDescription = (EditText) view.findViewById(R.id.edit_text_car_description);

        editTextCarDate = (EditText) view.findViewById(R.id.edit_text_car_date);
        editTextCarDate.setInputType(InputType.TYPE_NULL);
        editTextCarDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                datePickerDialogCarDate = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int myYear, int myMonth, int myDay) {
                                editTextCarDate.setText((myMonth + 1) + "/" + myDay + "/" + myYear);
                            }
                        }, year, month, day);
                datePickerDialogCarDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialogCarDate.show();
            }
        });

        spinnerCarTime = (Spinner) view.findViewById(R.id.spinner_emp_time);
        editTextCarStudentNotes = (EditText) view.findViewById(R.id.edit_text_emp_studentnotes);

        return view;
    }
}
