package iamprogrammer.brian.com.mygym;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    String gender = "male";
    EditText emailET, passET, dobET;
    ActionProcessButton signUpBtn;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Find views
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
        String email = emailET.getText().toString();
        String pass = passET.getText().toString();
        String dob = dobET.getText().toString();

        if( !email.isEmpty() && !pass.isEmpty() && !dob.isEmpty() ) {
            signUpBtn.setProgress(1);
            User user = new User( email, pass, dob, gender );

            // Init firebase
            mDatabase = FirebaseDatabase.getInstance().getReference("users");
            String userID = mDatabase.push().getKey();

            mDatabase.child(userID).setValue(user, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if( databaseError == null ) {
                        signUpBtn.setProgress(100);
                        Toast.makeText( SignUpActivity.this, "Done!", Toast.LENGTH_SHORT ).show();

                        // Proceed to home
                    }else {
                        Toast.makeText( SignUpActivity.this, "Something went wrong! Try again", Toast.LENGTH_SHORT ).show();
                        signUpBtn.setProgress(0);
                    }
                }
            });
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
}
