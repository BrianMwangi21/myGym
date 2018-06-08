package iamprogrammer.brian.com.mygym;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LogInActivity extends AppCompatActivity {

    EditText emailET, passET;
    ActionProcessButton loginBtn;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        // Find views
        emailET = findViewById(R.id.edittext_email_login);
        passET = findViewById(R.id.edittext_password_login);
        loginBtn = findViewById(R.id.button_login);
        loginBtn.setMode(ActionProcessButton.Mode.ENDLESS);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyCredentials();
            }
        });
    }

    public void verifyCredentials() {
        final String email = emailET.getText().toString();
        final String pass = passET.getText().toString();

        if( !email.isEmpty() && !pass.isEmpty() ) {
            loginBtn.setProgress(1);

            mDatabase = FirebaseDatabase.getInstance().getReference("users");
            mDatabase.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if( dataSnapshot.exists() ) {
                        loginBtn.setProgress(100);

                        for( DataSnapshot childSnapshot : dataSnapshot.getChildren() ) {
                            User user = childSnapshot.getValue(User.class);

                            if( email == user.getEmail() && pass == user.getPassword() ) {
                                Toast.makeText( LogInActivity.this, "Credentials match", Toast.LENGTH_SHORT ).show();
                            }else {
                                Toast.makeText( LogInActivity.this, "Credentials don't match", Toast.LENGTH_SHORT ).show();
                            }
                        }
                    }else {
                        Toast.makeText( LogInActivity.this, "Email not found", Toast.LENGTH_SHORT ).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText( LogInActivity.this, "Ref cancelled", Toast.LENGTH_SHORT ).show();
                }
            });
        }else {
            Toast.makeText( this, "Fill all fields", Toast.LENGTH_SHORT ).show();
        }
    }

}
