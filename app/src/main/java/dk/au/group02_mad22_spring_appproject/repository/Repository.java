package dk.au.group02_mad22_spring_appproject.repository;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;

import java.util.function.Consumer;

public class Repository extends AppCompatActivity {

    private FirebaseLoginWithEmailAuth emailAuth;



    //region Authentication

    public boolean isLoggedIn() {
        return emailAuth.isLoggedIn();
    }


    public void createUserWithEmailAndPassword(String email, String password, Consumer<FirebaseUser> successCallback) {
        emailAuth.createUserWithEmailAndPassword(email, password, successCallback);
    }

    public void signInWithEmailAndPassword(String email, String password, Consumer<FirebaseUser> successCallback, Consumer<Exception> errorCallback) {
        emailAuth.signInWithEmailAndPassword(email, password, successCallback, errorCallback);
    }

    public void signOut() {
        emailAuth.signOut();
    }

    public void forgotPassword(String email) {
        emailAuth.forgotPassword(email);
    }

}
