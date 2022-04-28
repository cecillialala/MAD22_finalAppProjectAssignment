package dk.au.group02_mad22_spring_appproject.repository;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FirebaseLoginWithEmailAuth {
    private static final String TAG = "FirebaseEmailAuth";
    private FirebaseAuth firebaseAuth;

    private FirebaseUser currentUser;
    private static FirebaseLoginWithEmailAuth INSTANCE;


    private void FirebaseEmailAuth() {
        firebaseAuth = FirebaseAuth.getInstance();


    }

    public static FirebaseLoginWithEmailAuth getInstance(){
        if (INSTANCE == null){
            INSTANCE = new FirebaseLoginWithEmailAuth();
        }
        return INSTANCE;
    }

    // Current user is null if not logged in.

    public boolean isLoggedIn() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null){
            Log.d(TAG, "isLoggedIn: Not logged in");
            return false;
        } else {
            if (currentUser == null){

            }
            Log.d(TAG, "isLoggedIn: Logged in as: " + user.getEmail());
            return true;
        }
    }


    public void createUserWithEmailAndPassword(String email, String password, Consumer<FirebaseUser> successCallback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
               // userDb.addUser(new UserModel(email));
                Log.d(TAG, "createUserWithEmailAndPassword: User: " + user.getEmail());
               /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    successCallback.accept(user);
                }*/
            } else {
                Log.w(TAG, "createUserWithEmailAndPassword: Failed to create user. ", task.getException());
            }
        });
    }


    public void signInWithEmailAndPassword(String email, String password, Consumer<FirebaseUser> successCallback, Consumer<Exception> errorCallback) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                Log.d(TAG, "onComplete: User signed in: " + user.getEmail());

               // successCallback.accept(user);
            } else {
                Log.w(TAG, "signInWithEmailAndPassword: Failed to sign in. ", task.getException());
              //  errorCallback.accept(task.getException());
            }
        });
    }

    public void signOut() {
        firebaseAuth.signOut();
    }

    public void forgotPassword(String email) {
        firebaseAuth.sendPasswordResetEmail(email);
    }
}
