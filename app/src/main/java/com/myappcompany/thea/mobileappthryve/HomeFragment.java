package com.myappcompany.thea.mobileappthryve;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment{

    Button selectDataButton;
    ListView selectDataListView;
    SimpleAdapter myAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //remove - return super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        CardView planCareerCardView = (CardView) view.findViewById(R.id.planyourcareer_cardView);
        planCareerCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CareerFragment nextFrag= new CareerFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        CardView planEducationCardView = (CardView) view.findViewById(R.id.planyoureducation_cardView);
        planEducationCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EducationFragment nextFrag= new EducationFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        CardView coCurricularCardView = (CardView) view.findViewById(R.id.cocurricular_cardView);
        coCurricularCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordFragment nextFrag= new RecordFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }
    /*public void changeFragment(View v) {
        System.out.println("CHECKPOINT: " + v.getId());
        switch (v.getId()) {
            case R.id.planyourcareer_textView:
                CareerFragment fr = new CareerFragment();
                FragmentChangeListener fc = (FragmentChangeListener) getActivity();
                fc.replaceFragment(fr);
                break;
        }
    }*/

    /*@Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selectDataButton = (Button) view.findViewById(R.id.select_data);
        selectDataListView = (ListView) view.findViewById(R.id.select_listView);

        selectDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Map<String,String>> selectDataList = null;
                SelectData myData = new SelectData();
                selectDataList = myData.getData();

                /*if (selectDataList == null) {
                    Toast.makeText(getActivity(), "selectDataList is null!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getActivity(), "selectDataList is not null!", Toast.LENGTH_SHORT).show();
                }

                String[] fromWhere = { "id","FirstName","LastName","Title","EmailAddress","PhoneNumber","PhoneExtension" };

                int[] viewWhere = {R.id.staff_id_data, R.id.staff_fname_data, R.id.staff_lname_data, R.id.staff_title_data, R.id.staff_email_data, R.id.staff_phonenumber_data, R.id.staff_phoneextension_data};

                myAdapter = new SimpleAdapter(getContext(), selectDataList, R.layout.selectdatalist_template, fromWhere, viewWhere);

                selectDataListView.setAdapter(myAdapter);
            }
        });
    }*/
}
