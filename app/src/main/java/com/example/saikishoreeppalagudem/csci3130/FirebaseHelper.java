package com.example.saikishoreeppalagudem.csci3130;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.content.ContentValues.TAG;

/**
 * Created by Karthick P  and Sam on 3/28/2018.
 */

/**
 * Creates user Account
 * <p>
 *     If successful, the account is created in firebase, and the Ui is updated accordingly
 * </p>
 * <p>
 *     If unsuccessful, the account is not created, and a message is shown stating as such
 * </p>
 */
public class FirebaseHelper {
    static public void createAccount(String email, String password, final Activity activity) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(activity, "Account Creation passed", Toast.LENGTH_SHORT).show();

                        } else {

                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(activity, "Account Creation failed", Toast.LENGTH_SHORT).show();
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

    static public void signInAccount(String email, String password, final Activity activity) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(activity, "Authentication Successful", Toast.LENGTH_SHORT).show();
                        } else {

                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(activity, "Authentication Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    /**
     * Allows user to sign out
     * <p>
     *     If Sign in is successful, the UI is updated with the signed-in user's information
     * </p>
     * <p>
     * If sign in fails, a message is displayed to the user.
     * </p>
     **/
    static public void signOut(){
        if(FirebaseAuth.getInstance().getCurrentUser() != null)
        {
            FirebaseAuth.getInstance().signOut();
        }
    }
}
