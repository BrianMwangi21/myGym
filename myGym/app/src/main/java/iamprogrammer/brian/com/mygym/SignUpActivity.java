package iamprogrammer.brian.com.mygym;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.scottyab.aescrypt.AESCrypt;

public class SignUpActivity extends AppCompatActivity {

    String gender = "male";
    EditText usernameET, emailET, passET, dobET;
    ActionProcessButton signUpBtn;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Find views
        usernameET = findViewById(R.id.edittext_username_signup);
        emailET = findViewById(R.id.edittext_email_signup);
        passET = findViewById(R.id.edittext_password_signup);
        dobET = findViewById(R.id.edittext_dob);
        signUpBtn = findViewById(R.id.button_signup);
        signUpBtn.setMode(ActionProcessButton.Mode.ENDLESS);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    public void registerUser() {
        // Check if null
        String username = usernameET.getText().toString();
        String email = emailET.getText().toString();
        String pass = passET.getText().toString();
        String dob = dobET.getText().toString();

        if( !username.isEmpty() && !email.isEmpty() && !pass.isEmpty() && !dob.isEmpty() ) {
            signUpBtn.setProgress(1);

            // Init firebase
            if( userExists(email) ) {
                Toast.makeText( SignUpActivity.this, "User already exists", Toast.LENGTH_SHORT ).show();
            }else {
                try {
                    pass = AESCrypt.encrypt(pass, email);
                }catch (Exception e) {
                    e.printStackTrace();
                }
                mDatabase = FirebaseDatabase.getInstance().getReference("users");
                String userID = mDatabase.push().getKey();
                User user = new User( userID,username, email, pass, dob, gender, "", "", "" );

                mDatabase.child(userID).setValue(user, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if( databaseError == null ) {
                            signUpBtn.setProgress(100);

                            // Go to home immediately
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class );
                            Bundle extras = new Bundle();
                            extras.putString("username", usernameET.getText().toString());
                            extras.putString("email", emailET.getText().toString());
                            intent.putExtras( extras );
                            startActivity(intent);
                        }else {
                            Toast.makeText( SignUpActivity.this, "Something went wrong! Try again", Toast.LENGTH_SHORT ).show();
                            signUpBtn.setProgress(0);
                        }
                    }
                });
            }
        }else {
            Toast.makeText( this, "Fill all fields", Toast.LENGTH_SHORT ).show();
        }
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_male:
                if (checked)
                    gender = "male";
                break;
            case R.id.radio_female:
                if (checked)
                    // Ninjas rule
                    gender = "female";
                break;
        }
    }

    boolean exists = false;
    public boolean userExists(String email) {
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mDatabase.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if( dataSnapshot.exists() ) {
                    exists = true;
                }else {
                    exists = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText( SignUpActivity.this, "Ref cancelled", Toast.LENGTH_SHORT ).show();
            }
        });

        return exists;
    }
}
