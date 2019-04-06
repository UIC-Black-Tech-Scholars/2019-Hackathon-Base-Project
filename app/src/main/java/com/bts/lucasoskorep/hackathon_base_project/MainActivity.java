package com.bts.lucasoskorep.hackathon_base_project;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bts.lucasoskorep.hackathon_base_project.Database.AppDatabase;
import com.bts.lucasoskorep.hackathon_base_project.Entity.Category;
import com.bts.lucasoskorep.hackathon_base_project.Entity.Entries;
import com.bts.lucasoskorep.hackathon_base_project.Entity.User;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

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
    int ID = 0;

    private static final String TAG = "MAIN_ACTIVITY_TAG";

    private static final int CAMERA_CODE = 1;
    private static final int PERMISSION_EXTERNAL_STORAGE = 2;

    private ArrayList<String> listOfpaths = new ArrayList<>();
    private Integer numOfTransactions = 0;

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

    @BindView(R.id.editText)
    EditText amount;


    @BindView(R.id.spinner5)
    Spinner month;

    @BindView(R.id.spinner6)
    Spinner day;

    @BindView(R.id.spinner8)
    Spinner year;

    @BindView(R.id.imageView2)
    ImageView cameraImage;

    @BindView(R.id.spinner4)
    Spinner category;

    @BindView(R.id.editText5)
    EditText comments;

    @BindView(R.id.editText7)
    EditText nameTitle;


    private Button imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setUpFabAndSideMenu();

        imageButton = findViewById(R.id.button);
        cameraImage = findViewById(R.id.imageView2);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, CAMERA_CODE);
                }

            }
        });

        //Start with your onCreate code here!

        setUpSpinners();

        exampleDatabaseQuery();

        exampleDatabaseQueryCategory();
        exampleDatabaseQueryEntries();

        Log.i(TAG, "calling transactions");
        Transactions(); //get transaction info
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_CODE && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            cameraImage.setImageBitmap(imageBitmap);

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED) {


                MediaStore.Images.Media.insertImage(getContentResolver(), imageBitmap, "fds", "Fsd");

                // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                Uri tempUri = getImageUri(getApplicationContext(), imageBitmap);

                // CALL THIS METHOD TO GET THE ACTUAL PATH
                File finalFile = new File(getRealPathFromURI(tempUri));
                listOfpaths.add(finalFile.toString());
                Log.v(TAG, "Permission is granted " + finalFile);
                numOfTransactions +=1;
            } else {
                Log.v(TAG, "Permission  not is granted " );
                ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_EXTERNAL_STORAGE);

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_GRANTED) {

                    MediaStore.Images.Media.insertImage(getContentResolver(), imageBitmap, "fds", "Fsd");

                    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
                    Uri tempUri = getImageUri(getApplicationContext(), imageBitmap);

                    // CALL THIS METHOD TO GET THE ACTUAL PATH
                    File finalFile = new File(getRealPathFromURI(tempUri));
                    listOfpaths.add(finalFile.toString());
                    Log.v(TAG, "Permission is granted " + finalFile);
                    numOfTransactions += 1;
                }
            }

        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
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

        } else if (id == R.id.show_statements) {
            //Create new activity here
//            Intent intent = new Intent(MainActivity.this, SecondaryActivity.class);
//            startActivity(intent);

        } else if (id == R.id.show_barGraph) {
            //Create new activity here
//            Intent intent = new Intent(MainActivity.this, GraphActivity.class);
//            startActivity(intent);

        }

        else if (id == R.id.show_pieChart) {

        } else if (id == R.id.show_transactionsList) {
            Intent intent = new Intent(MainActivity.this, TransactionsActivity.class);
            startActivity(intent);

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
                amountnum = Integer.parseInt(amount.getText().toString());
                Log.i(TAG, "trasactions here. ");
                daynum = Integer.parseInt(day.getSelectedItem().toString());
                Log.i(TAG, "daynum:  " + daynum);
                String monthstr = month.getSelectedItem().toString();
                switch(monthstr){
                    case "January": monthnum = 1; break;
                    case "February": monthnum = 2; break;
                    case "March": monthnum = 3; break;
                    case "April": monthnum = 4; break;
                    case "May": monthnum = 5; break;
                    case "June": monthnum = 6; break;
                    case "July": monthnum = 7; break;
                    case "August": monthnum = 8; break;
                    case "September": monthnum = 9; break;
                    case "October": monthnum = 10; break;
                    case "November": monthnum = 11; break;
                    case "December": monthnum = 12; break;
                }
                yearnum =  Integer.parseInt(year.getSelectedItem().toString());
                categoryval = category.getSelectedItem().toString();
                Log.i(TAG, "transactions here 2 " + categoryval);

                if (listOfpaths.size() <= numOfTransactions || listOfpaths.get(numOfTransactions) == null){
                    Toast.makeText(MainActivity.this, "Please Add An Image :)",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    ID+=1;

                    Entries newentry = new Entries(daynum, monthnum, yearnum, listOfpaths.get(numOfTransactions), ID, amountnum, categoryval);
                    addEntry(appDatabase, newentry);

                    Log.i(TAG, "Entries - Done updating the users, printing out the update results.");
                    for(Entries entries: appDatabase.entriesDao().getAll()){
                        Log.i(TAG, "Date is: " + entries.getDate() + " PATH: " + entries.getImage());
                        deleteEntry(appDatabase, entries);
                    }
                }

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
                "Income", "Housing", "Food", "Clothing", "Entertainment", "Transportation", "Other"
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

    public void openGraphActivity(){
        Intent intent = new Intent(MainActivity.this, GraphActivity.class);
        startActivity(intent);
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
