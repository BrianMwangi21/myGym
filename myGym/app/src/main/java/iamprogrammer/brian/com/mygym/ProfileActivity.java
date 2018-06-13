package iamprogrammer.brian.com.mygym;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    Bundle extras;
    String email;
    EditText usernameET, dobET, homeET, initWET, targetWET;
    ActionProcessButton saveBtn;
    DatabaseReference mDatabase;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get extras
        extras = getIntent().getExtras();
        email = extras.getString("email");
        getUserData( email );

        // Get views
        usernameET = findViewById(R.id.profile_username);
        usernameET.setText( extras.getString("username") );
        dobET = findViewById(R.id.profile_dob);
        dobET.setKeyListener(null);
        dobET.requestFocus();
        homeET = findViewById(R.id.profile_home);
        initWET = findViewById(R.id.profile_init_weight);
        targetWET = findViewById(R.id.profile_target_weight);
        saveBtn = findViewById(R.id.button_profile_save);
        saveBtn.setMode(ActionProcessButton.Mode.ENDLESS);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( !usernameET.getText().toString().isEmpty() && !homeET.getText().toString().isEmpty() &&
                        !initWET.getText().toString().isEmpty() && !targetWET.getText().toString().isEmpty() ) {
                    saveBtn.setProgress(1);
                    saveChanges();
                }else {
                    Toast.makeText( ProfileActivity.this, "Fill all fields", Toast.LENGTH_SHORT ).show();
                }
            }
        });
    }

    public void getUserData( String email ) {
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mDatabase.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if( dataSnapshot.exists() ) {
                    for( DataSnapshot childSnapshot : dataSnapshot.getChildren() ) {
                        user = childSnapshot.getValue(User.class);

                        // Populate the data
                        dobET.setText( user.getDob() );
                        homeET.setText( user.getHome() );
                        initWET.setText( user.getInit_weight() );
                        targetWET.setText( user.getTarget_weight() );
                    }
                }else {
                    Toast.makeText( ProfileActivity.this, "Email not found", Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText( ProfileActivity.this, "Ref cancelled", Toast.LENGTH_SHORT ).show();
            }
        });
    }

    public void saveChanges() {
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        Query query = mDatabase.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for( DataSnapshot childSnapshot : dataSnapshot.getChildren() ) {
                    String userID = childSnapshot.getKey();

                    Toast.makeText(ProfileActivity.this, userID, Toast.LENGTH_SHORT).show();

                    /**
                    mDatabase.child(userID);
                    user.setUsername( usernameET.getText().toString() );
                    user.setHome( homeET.getText().toString() );
                    user.setInit_weight( initWET.getText().toString() );
                    user.setTarget_weight( targetWET.getText().toString() );

                    Map<String, Object> new_values = user.toMap();
                    mDatabase.updateChildren(new_values, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            Toast.makeText( ProfileActivity.this, "Completed", Toast.LENGTH_SHORT ).show();
                        }
                    });
                     **/
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText( ProfileActivity.this, "Ref cancelled", Toast.LENGTH_SHORT ).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent( ProfileActivity.this, MainActivity.class );
                intent.putExtras( extras );
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
