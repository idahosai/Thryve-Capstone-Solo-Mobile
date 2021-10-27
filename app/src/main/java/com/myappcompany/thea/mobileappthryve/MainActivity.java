package com.myappcompany.thea.mobileappthryve;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements AddEmploymentAppointmentFragment.AddEmpFragmentListener, NavigationView.OnNavigationItemSelectedListener, FragmentChangeListener {
    private DrawerLayout drawer;

    private Intent mainIntent;
    private StudentAccount activeAccount;
    private Appointment receivedAppointment;
    private EmploymentConsultantForm receivedEmploymentForm;

    private TextView accountHeaderName;
    private TextView accountHeaderEmail;

    private AddEmploymentAppointmentFragment empFragment;
    private AppointmentFragment fragmentAppointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //receive login details
        mainIntent = getIntent();
        activeAccount = (StudentAccount) mainIntent.getSerializableExtra("user");

        Toast.makeText(this, "LOGGED IN: " + activeAccount.getEmailAddress(), Toast.LENGTH_LONG).show();

        fragmentAppointment = new AppointmentFragment();

        //use the toolbar as the action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        //listen to buttons on navigation view
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //change account details on header
        View header = navigationView.getHeaderView(0);
        accountHeaderName = (TextView) header.findViewById(R.id.menu_header_name);
        accountHeaderName.setText(activeAccount.getFirstName() + " " + activeAccount.getLastName());

        accountHeaderEmail = (TextView) header.findViewById(R.id.menu_header_email);
        accountHeaderEmail.setText(activeAccount.getEmailAddress());

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //open the HomeFragment by default
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.nav_career:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CareerFragment()).commit();
                break;
            case R.id.nav_education:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new EducationFragment()).commit();
                break;
            case R.id.nav_record:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new RecordFragment()).commit();
                break;
            case R.id.nav_work:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new WorkFragment()).commit();
                break;
            case R.id.nav_apt:
                AppointmentFragment newAppointmentFragment = AppointmentFragment.newAppointmentFragmentInstance(activeAccount);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, newAppointmentFragment).commit();
                break;
            case R.id.nav_centre:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CentreFragment()).commit();
                break;
            /*case R.id.nav_share:
                Toast.makeText(this, "Share!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_send:
                Toast.makeText(this, "Share!", Toast.LENGTH_SHORT).show();
                break;*/
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment, fragment.toString());
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }

    //while drawer is open, close it using the back button
    //instead of leaving the Activity
    @Override
    public void onBackPressed() {
        //GravityCompat.START means drawer on the left side, END for right side
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public StudentAccount getActiveAccount() {
        return activeAccount;
    }

    @Override
    public void onNewEmpApptSent(Appointment newAppt, EmploymentConsultantForm newEmp) {


        System.out.println("newAppt null? " + newAppt.getStartDate());
        System.out.println("newEmp null? " + newEmp.isEcsJobsearch());
        System.out.println("ActiveAccount null? " + activeAccount.getFirstName());
        fragmentAppointment.insertNewEmpAppointment(newAppt, newEmp, activeAccount);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragmentAppointment).commit();
        //Fragment fragment = new AddCareerAppointmentFragment();
        //FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.replace(R.id.fragment_container, fragment);
        //fragmentTransaction.addToBackStack(null);
        //fragmentTransaction.commit();

        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragmentAppointment).commit();
    }
}
