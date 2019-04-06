package com.bts.lucasoskorep.hackathon_base_project;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.TextViewCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.bts.lucasoskorep.hackathon_base_project.Database.AppDatabase;
import com.bts.lucasoskorep.hackathon_base_project.Entity.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private static final String TAG = "MAIN_ACTIVITY_TAG";

    private static AppDatabase appDatabase;

//    @BindView(R.id.textView)
//    TextView textView;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.button2)
    Button submitbutton;

    @BindView(R.id.editText7)
    EditText amount;

    @BindView(R.id.spinner5)
    Spinner month;

    @BindView(R.id.spinner6)
    Spinner day;

    @BindView(R.id.spinner8)
    Spinner year;

    @BindView(R.id.spinner4)
    Spinner category;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpFabAndSideMenu();

        //Start with your onCreate code here!
        exampleDatabaseQuery();

        setUpSpinners();

        Transactions(); //get transaction info

    }


    /**
     * Triggered whenever the back button is pressed.
     */
    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void exampleDatabaseQuery() {
        //database code here
        appDatabase = AppDatabase.getAppDatabase(this);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                populateWithTestData(appDatabase);
                for (User user : appDatabase.userDao().getAll()) {
                    Log.i(TAG, user.getFirstName() + " " + user.getLastName() + " : " + user.getUid());
                    updateUser(appDatabase, user);
                }

                Log.i(TAG, "Done updating the users, printing out the update results.");
                for (User user : appDatabase.userDao().getAll()) {
                    Log.i(TAG, user.getFirstName() + " " + user.getLastName() + " : " + +user.getUid());
                    deleteUser(appDatabase, user);
                }
                Log.i(TAG, "Removing all users from the database. ");
                Log.i(TAG, "Attempting to print all users from the database. There should be no more log messages after this. ");
                for (User user : appDatabase.userDao().getAll()) {
                    Log.i(TAG, user.getFirstName() + " " + user.getLastName() + " : " + user.getUid());

                }
            }
        };
        new Thread(runnable).start();
    }

    public void setUpFabAndSideMenu() {

        setSupportActionBar(toolbar);
        //Creates the floating action button
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Creates the side drawer incase you want to use that.
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
    }


    private static User updateUser(AppDatabase db, User user) {
        db.userDao().updateUsers(user);
        return user;
    }

    private static void deleteUser(AppDatabase db, User user) {
        db.userDao().delete(user);
    }

    private static User addUser(final AppDatabase db, User user) {
        db.userDao().insertAll(user);
        return user;
    }

    private static void populateWithTestData(AppDatabase db) {
        User user = new User();
        user.setFirstName("Lucas");
        user.setLastName("Oskorep");
        addUser(db, user);
        user = new User();
        user.setFirstName("Carmen");
        user.setLastName("Bertucci");
        addUser(db, user);
        user = new User();
        user.setFirstName("Student");
        user.setLastName("McStudentFace");
        addUser(db, user);
    }

    public void Transactions() {
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //int amountnum = Integer.parseInt(amount.getText());
                //int daynum = (int) day.getSelectedItem();
                //int monthnum = (int) month.getSelectedItem();
               // int yearnum = (int) year.getSelectedItem();
               // String categoryval = String.valueOf(category.getSelectedItem());
               // System.out.println("Amount num: " + amountnum);
            }
        });

    }

    public void setUpSpinners(){
        String[] spinnermonth = new String[]{
                "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnermonth);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month.setAdapter(adapter);

        String[] spinnerday = new String[]{
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18",
                "19", "20", "21", "22", "23", "24","25", "26", "27", "28", "29", "30", "31"
        };

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerday);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day.setAdapter(adapter2);

        String[] spinneryear= new String[]{
                "2019", "2018", "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010"
        };
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinneryear);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(adapter3);

        String[] spinnercategory= new String[]{
                "Income", "Housing", "Food", "Clothing", "Entertainment", "Transportation"
        };
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnercategory);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter4);

    }
}
