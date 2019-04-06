package com.bts.lucasoskorep.hackathon_base_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.TextView;

import com.bts.lucasoskorep.hackathon_base_project.Database.AppDatabase;
import com.bts.lucasoskorep.hackathon_base_project.Entity.Category;
import com.bts.lucasoskorep.hackathon_base_project.Entity.Entries;
import com.bts.lucasoskorep.hackathon_base_project.Entity.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MAIN_ACTIVITY_TAG";

    private static final int CAMERA_CODE = 1;

    private static AppDatabase appDatabase;

    @BindView(R.id.textView)
    TextView textView;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

//    Button button;
//
//    ImageView cameraImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpFabAndSideMenu();

//        button = findViewById(R.id.graphButton);
//        cameraImage = findViewById(R.id.cameraImage);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, CAMERA_CODE);
//            }
//        });

        //Start with your onCreate code here!
        exampleDatabaseQueryCategory();
        exampleDatabaseQueryEntries();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (requestCode == CAMERA_CODE  && resultCode  == RESULT_OK) {
//            Uri selectedImage = data.getData();
//            String[] filePathColumn = {MediaStore.Images.Media.DATA};
//
//            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//            cursor.moveToFirst();
//
//            //yourSelectedImage = BitmapFactory.decodeFile(filePath);
//
//            Toast.makeText(this, "SAve img", Toast.LENGTH_LONG).show();
//
//        }

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


//    private void exampleDatabaseQuery(){
//        //database code here
//        appDatabase = AppDatabase.getAppDatabase(this);
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                populateWithTestData(appDatabase);
//                for(User user: appDatabase.userDao().getAll()){
//                    Log.i(TAG, user.getFirstName() + " " + user.getLastName() + " : "  +user.getUid());
//                    updateUser(appDatabase, user);
//                }
//
//                Log.i(TAG, "Done updating the users, printing out the update results.");
//                for(User user: appDatabase.userDao().getAll()){
//                    Log.i(TAG, user.getFirstName() + " " + user.getLastName() + " : " + +user.getUid());
//                    deleteUser(appDatabase, user);
//                }
//                Log.i(TAG, "Removing all users from the database. ");
//                Log.i(TAG, "Attempting to print all users from the database. There should be no more log messages after this. ");
//                for(User user: appDatabase.userDao().getAll()){
//                    Log.i(TAG, user.getFirstName() + " " + user.getLastName() + " : " +user.getUid());
//
//                }
//            }
//        };
//        new Thread(runnable).start();
//    }

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

// Method of the EntriesData

    private void exampleDatabaseQueryEntries(){
        //database code here
        appDatabase = AppDatabase.getAppDatabase(this);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                populateWithTestData(appDatabase);
                for(Entries entry: appDatabase.entriesDao().getAll()){
                    Log.i(TAG, entry.getNameTitle() + entry.getTransPrimaryKey());
                    updateEntry(appDatabase, entry);
                }

                Log.i(TAG, "Done updating the entries, printing out the update results.");
                for(Entries entry: appDatabase.entriesDao().getAll()){
                    Log.i(TAG, entry.getNameTitle() + entry.getTransPrimaryKey());
                    deleteEntry(appDatabase, entry);
                }
                Log.i(TAG, "Removing all entries from the database. ");
                Log.i(TAG, "Attempting to print all entries from the database. There should be no more log messages after this. ");
                for(Entries entry: appDatabase.entriesDao().getAll()){
                    Log.i(TAG, entry.getNameTitle());

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


    private static User updateUser(AppDatabase db, User user){
        db.userDao().updateUsers(user);
        return user;
    }

    private static Category updateCategory(AppDatabase db, Category category){
        db.categoryDao().updateCategory(category);
        return category;
    }

    private static void deleteUser(AppDatabase db, User user){
        db.userDao().delete(user);
    }

    private static void deleteCategory(AppDatabase db, Category category){
        db.categoryDao().delete(category);
    }

    private static User addUser(final AppDatabase db, User user) {
        db.userDao().insertAll(user);
        return user;
    }

    private static Category addCategory(final  AppDatabase db, Category category){
        db.categoryDao().insertAll(category);
        return category;
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
        entry.setNameTitle("trip to jewel osco");
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
