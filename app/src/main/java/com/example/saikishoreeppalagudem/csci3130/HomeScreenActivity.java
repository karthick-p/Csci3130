package com.example.saikishoreeppalagudem.csci3130;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * @author Documented by Sam Barefoot
 */

public class HomeScreenActivity extends AppCompatActivity {



    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    AppSharedResources appSharedResources;

    FirebaseAuth AuthRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        AuthRef = FirebaseAuth.getInstance();

        appSharedResources = AppSharedResources.getInstance();
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        switch(menuItem.getItemId()){

                            case R.id.nav_RegisterLogin:
                                callIntent(MainActivity.class);
                                break;

                            case R.id.nav_CourseList:
                                if(FirebaseAuth.getInstance().getCurrentUser()!=null)
                                    callIntent(TermFilterActivity.class);
                                else
                                    Toast.makeText(HomeScreenActivity.this, "Please Sign in to view courses",
                                            Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.nav_MyCourses:
                                if(FirebaseAuth.getInstance().getCurrentUser()!=null)
                                    callIntent(StudentCoursesActivity.class);
                                else
                                    Toast.makeText(HomeScreenActivity.this, "Please Sign in to view my courses",
                                            Toast.LENGTH_SHORT).show();
                                break;

                            case R.id.nav_deadline:
                                callIntent(Activity_deadline.class);
                                break;

                            case R.id.nav_Logout:
                                AuthRef.signOut();
                                if(FirebaseAuth.getInstance().getCurrentUser() == null)
                                    Toast.makeText(HomeScreenActivity.this, "Sign out Successful",
                                            Toast.LENGTH_SHORT).show();
                                    appSharedResources.setStudentId(null);
                                    break;
                        }

                        return true;
                    }
                });

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
             return true;
        }
        return super.onOptionsItemSelected(item);

    }


    public void registerOnClick(View view) {
        callIntent(MainActivity.class);
    }

    public void courseListOnClick(View view) {
        callIntent(CourseList.class);
    }

    public void myCoursesOnClick(View view) {
        callIntent(StudentCoursesActivity.class);
    }

    public void callIntent(Class className){
        Intent intent = new Intent(getApplicationContext(), className);
        startActivity(intent);
    }
}
