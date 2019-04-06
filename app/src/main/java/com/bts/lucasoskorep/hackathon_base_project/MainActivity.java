package com.bts.lucasoskorep.hackathon_base_project;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.bts.lucasoskorep.hackathon_base_project.Database.AppDatabase;
import com.bts.lucasoskorep.hackathon_base_project.Entity.Category;
import com.bts.lucasoskorep.hackathon_base_project.Entity.Entries;
import com.bts.lucasoskorep.hackathon_base_project.Entity.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int amountnum;
    int daynum;
    int monthnum;
    int yearnum;
    String categoryval;
    String commentsval;

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

    @BindView(R.id.editText5)
        EditText comments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpFabAndSideMenu();

        //Start with your onCreate code here!

        setUpSpinners();

        exampleDatabaseQuery();

        exampleDatabaseQueryCategory();
        exampleDatabaseQueryEntries();

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
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.new_entry) {
            // Handle the camera action
        } else if (id == R.id.show_statements) {

        } else if (id == R.id.show_reports) {

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

    private void exampleDatabaseQueryCategory(){
        //database code here
        appDatabase = AppDatabase.getAppDatabase(this);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                populateWithTestDataCategory(  appDatabase);
                for(Category category : appDatabase.categoryDao().getAll()){
                    Log.i(TAG, category.getName() + " " + category.getTransactionType() + " : "  + category.getUid());
                    updateCategory(appDatabase, category );
                }

                Log.i(TAG, "Done updating the users, printing out the update results.");
                for(Category category: appDatabase.categoryDao().getAll()){
                    Log.i(TAG, category.getName() + " " + category.getTransactionType() + " : " + category.getUid());
                    deleteCategory(appDatabase, category);
                }
                Log.i(TAG, "Removing all users from the database. ");
                Log.i(TAG, "Attempting to print all users from the database. There should be no more log messages after this. ");
                for(Category category: appDatabase.categoryDao().getAll()){
                    Log.i(TAG, category.getName() + " " + category.getTransactionType() + " : " +category.getUid());

                }
            }
        };
        new Thread(runnable).start();
    }

    private void exampleDatabaseQueryEntries(){
        //database code here
        appDatabase = AppDatabase.getAppDatabase(this);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                populateWithTestDataEntries(  appDatabase);
                for(Entries entries : appDatabase.entriesDao().getAll()){
                    Log.i(TAG, entries.getDate());
                    updateEntry(appDatabase, entries);
                }

                Log.i(TAG, "Entries - Done updating the users, printing out the update results.");
                for(Entries entries: appDatabase.entriesDao().getAll()){
                    Log.i(TAG, "Date is: " + entries.getDate());
                    deleteEntry(appDatabase, entries);
                }
                Log.i(TAG, "Removing all users from the database. ");
                Log.i(TAG, "Entries - Attempting to print all users from the database. There should be no more log messages after this. ");
                for(Entries entries: appDatabase.entriesDao().getAll()){
                    Log.i(TAG, entries.getDate());

                }
            }
        };
        new Thread(runnable).start();
    }



    public void setUpFabAndSideMenu(){


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


    private static Category updateCategory(AppDatabase db, Category category){
        db.categoryDao().updateCategory(category);
        return category;
    }


    private static void deleteUser(AppDatabase db, User user) {
        db.userDao().delete(user);
    }

    private static void deleteCategory(AppDatabase db, Category category){
        db.categoryDao().delete(category);
    }


    private static User addUser(final AppDatabase db, User user) {
        db.userDao().insertAll(user);
        return user;
    }

    private static Category addCategory(final AppDatabase db, Category category){
        db.categoryDao().insertAll(category);
        return category;
    }

    public void Transactions() {
        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // amountnum = Integer.parseInt(amount.getText().toString());
                System.out.println("here");
                daynum = (int) day.getSelectedItem();
                monthnum = (int) month.getSelectedItem();
                yearnum = (int) year.getSelectedItem();
                categoryval = category.getSelectedItem().toString();
                System.out.println("Here2");

                Entries newentry = new Entries( daynum, monthnum, yearnum);
                addEntry(appDatabase, newentry);
                //commentsval = (comments.getText().toString);
            }
        });

    }

    public void setUpSpinners() {
        String[] spinnermonth = new String[]{
                "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnermonth);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month.setAdapter(adapter);

        String[] spinnerday = new String[]{
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18",
                "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
        };

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnerday);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day.setAdapter(adapter2);

        String[] spinneryear = new String[]{
                "2019", "2018", "2017", "2016", "2015", "2014", "2013", "2012", "2011", "2010"
        };
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinneryear);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(adapter3);

        String[] spinnercategory = new String[]{
                "Income", "Housing", "Food", "Clothing", "Entertainment", "Transportation"
        };
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, spinnercategory);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter4);

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


    private static void populateWithTestDataCategory(AppDatabase db) {
        Category category = new Category();
        category.setName("Housing");
        category.setTransactionType("negative");
        addCategory(db, category);

        category = new Category();
        category.setName("income");
        category.setTransactionType("positive");
        addCategory(db, category);

        category = new Category();
        category.setName("Food");
        category.setTransactionType("negative");
        addCategory(db, category);

        category = new Category();
        category.setName("transportation");
        category.setTransactionType("negative");
        addCategory(db, category);
    }

    private static void populateWithTestDataEntries(AppDatabase db) {
        Entries entry = new Entries();
        entry.setDate(1, 2, 20);
        addEntry(db, entry);

    }

    private static Entries addEntry(final AppDatabase db, Entries entry) {
        db.entriesDao().insertAll(entry);
        return entry;
    }

    private static Entries updateEntry(AppDatabase db, Entries entry){
        db.entriesDao().updateEntries(entry);
        return entry;
    }

    private static void deleteEntry(AppDatabase db, Entries entry){
        db.entriesDao().delete(entry);
    }


}
