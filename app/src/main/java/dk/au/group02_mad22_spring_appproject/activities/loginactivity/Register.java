package dk.au.group02_mad22_spring_appproject.activities.loginactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import dk.au.group02_mad22_spring_appproject.R;
import dk.au.group02_mad22_spring_appproject.activities.mainactivity.MainActivity;

public class Register extends AppCompatActivity {
   // https://www.youtube.com/watch?v=tbh9YaWPKKs&t=0s
    public static final String TAG = "RegisterActivity";
    EditText mFullName,mEmail,mPassword,mPhone;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "Creating on Create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

       findIDs();
        setupButtons();
    }

    private void findIDs(){
        Log.d(TAG, "Finding IDs");
        mFullName   = findViewById(R.id.register_pt_nameID);
        mEmail      = findViewById(R.id.login_pt_mailID);
        mPassword   = findViewById(R.id.login_pt_passwordID);
        mRegisterBtn= findViewById(R.id.Register_Btn);
        mLoginBtn   = findViewById(R.id.createText);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
    }

    private void setupButtons(){
        Log.d(TAG, "Setting buttons and such");
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        mRegisterBtn.setOnClickListener(v -> {
            final String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();
            final String fullName = mFullName.getText().toString();

            if (TextUtils.isEmpty(email)) {
                mEmail.setError("Email is Required.");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                mPassword.setError("Password is Required.");
                return;
            }

            if (password.length() < 6) {
                mPassword.setError("Password Must be >= 6 Characters");
                return;
            }

            fAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(Register.this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser fuser = fAuth.getCurrentUser();

                            Toast.makeText(Register.this, "User Created.", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fName",fullName);
                            user.put("email",email);
                            user.put("password",password);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }
                            });
                            Toast.makeText(Register.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    });
        });
        mLoginBtn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),LoginActivity.class)));
    }
}