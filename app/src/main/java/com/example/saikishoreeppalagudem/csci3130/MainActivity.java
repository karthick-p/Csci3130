package com.example.saikishoreeppalagudem.csci3130;

import android.app.Activity;
import android.content.Intent;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * @author Sam Barefoot
 * @author Documented by Sam Barefoot
 * @author Karthick P deleted the createAccount and signIn methood , linked with the Firebase Helper class
 */

// Derived and Inspired by Google's QuickStart FireBase Guide
//https://github.com/firebase/quickstart-js
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    /**
     * String that is use in using Log
     */
    final String TAG = "EmailPassword";
    /**
     * TextView object that displays the current status
     */
    private TextView mStatusTextView;
    /**
     *TextView object that stores details about the status
     */
    private TextView mDetailTextView;
    /**
     * EditText object where users input email addresses
     */
    EditText mEmailField;
    /**
     * EditText object whereas users input passwords that correspond to the inputted email
     */
    EditText mPasswordField;
    /**
     *  Reference to firebase database using the key "Students"
     */
    DatabaseReference databaseStudentReference;
    /**
     * Hashmap for student info, interacts with FireBase Database
     */
    Map<String, Object> studentInfoMap = new HashMap<>();
    /**
     * Firebase Authentification Object, used to test user credentials
     */
    FirebaseAuth mAuth;
    /**
     * Key used to search for a specific student within a database
     */

    public static String STUDENT_KEY = "";
    /**
     * AppSharedResources to initialise studentID on new Registration and Login
     * and also to get firebase database references
     */
    AppSharedResources appSharedResources;

    @Override
    /**
     * Dictates what is to be done once the activity is created
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        // Views
        mStatusTextView = findViewById(R.id.textView2);
        mDetailTextView = findViewById(R.id.textView3);
        mEmailField = findViewById(R.id.editText);
        mPasswordField = findViewById(R.id.editText2);
//        databaseStudentReference = FirebaseDatabase.getInstance().getReference("Student");
        // Buttons
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        //App shared resources
        appSharedResources = new AppSharedResources();

    }


    @Override
    /**
     * Check if user is signed in and update UI
     */
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
    }





    /**Validates form, make sure there is an email address inputed, a password of a certain length etc.
     *
     * @return returns a boolean value that steates whether the form is valid or not
     */
    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        }
        else if(password.length() < 5){
            mPasswordField.setError("Password needs to be at least 5 characters");
        }
        else {
            mPasswordField.setError(null);
        }

        return valid;
    }





    /**
     * Sets the process for what happens when you press any of the on screen buttons
     *
     * <p>
     *     Pressing the register button creates the account, initializes the user in firebase, and opens the course list
     * </p>
     * <p>
     *     Pressing the Sign In button
     * </p>
     */

    @Override
    public void onClick(View v) {
        boolean isSuccessful;
        int i = v.getId();
        if (i == R.id.button2) {
        //karthick and sam  - refactoring
            if(validateForm()){
                String studentName, studentCourses, waitlistCourses;
                STUDENT_KEY = appSharedResources.studentDbRef.push().getKey();
                studentName = mEmailField.getText().toString();
                studentCourses = "";
                waitlistCourses = "1";
                studentInfoMap.put("studentID", STUDENT_KEY);
                studentInfoMap.put("studentName", studentName);
                studentInfoMap.put("studentCourses", studentCourses);
                studentInfoMap.put("waitlistCourses",waitlistCourses);

                createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString(),
                                    studentInfoMap, STUDENT_KEY);

            //   goToCourseList();
            }
        }
        else if (i == R.id.button) {
            if(validateForm()){
                signInAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
                //ESK

            }
        }
    }
//ESK

    /**
     * Retrieves user details from the Firebase Database
     */
 public void getUserDetails(){
//            databaseStudentReference
     appSharedResources = AppSharedResources.getInstance();
     appSharedResources.studentDbRef.orderByChild("studentName").equalTo(mEmailField.getText().toString()).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Log.e("Query out", dataSnapshot.getKey());
                    STUDENT_KEY = dataSnapshot.getKey();
                    //Sets studentID in the appSharedResources
                    appSharedResources.setStudentId(STUDENT_KEY);
                    goToCourseList();
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }



    /**
     * Method to go to the CourseList activity
     */
    public void goToCourseList(){

        Intent intent = new Intent(getApplicationContext(), TermFilterActivity.class);
        startActivity(intent);

    }


    /*Method to create account*/
    public void createAccount(String email, String password, final Map<String, Object> studentInitMap,
                              final String studentKey) {
        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
            FirebaseAuth.getInstance().signOut();
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            appSharedResources = AppSharedResources.getInstance();
                            appSharedResources.studentDbRef.child(studentKey).setValue(studentInitMap);
                            appSharedResources.setStudentId(studentKey);
                            Toast.makeText(MainActivity.this, "Account successfully created", Toast.LENGTH_SHORT).show();
                            appSharedResources.setStudentId(studentKey);
                            goToCourseList();
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Account Creation failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    /**
     * Allows user to sign in
     * <p>
     *     If Sign in is successful, the UI is updated with the signed-in user's information
     * </p>
     * <p>
     * If sign in fails, a message is displayed to the user.
     * </p>
     **/

    public void signInAccount(String email, String password) {
        if (FirebaseAuth.getInstance()!=null)
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "signInWithEmail:success");
                                Toast.makeText(MainActivity.this, "Authentication Successful", Toast.LENGTH_SHORT).show();
                                getUserDetails();
                            } else {
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
    }
}
