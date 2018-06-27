package iamprogrammer.brian.com.mygym;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class SessionsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Bundle extras;
    String email;
    User user;
    DatabaseReference mDatabase;
    EditText locationET, dateET, repsET;
    Spinner exerciseSpinner;
    String exercises[] = {"Aerobic exercise", "Breathing exercise", "Strength exercise", "Stretching exercise"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Workout sessions");

        // Get extras
        extras = getIntent().getExtras();
        email = extras.getString("email");
        getUserData( email );

        // Get views
        locationET = findViewById(R.id.session_location);
        locationET.setText( extras.getString("gym") );
        dateET = findViewById(R.id.sessions_date);
        repsET = findViewById(R.id.session_reps);

        exerciseSpinner = findViewById(R.id.sessions_exercise);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, exercises);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exerciseSpinner.setAdapter( adapter );
    }

    public void getUserData( String email ) {
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mDatabase.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if( dataSnapshot.exists() ) {
                    for( DataSnapshot childSnapshot : dataSnapshot.getChildren() ) {
                        user = childSnapshot.getValue(User.class);
                    }
                }else {
                    Toast.makeText( SessionsActivity.this, "Email not found", Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText( SessionsActivity.this, "Ref cancelled", Toast.LENGTH_SHORT ).show();
            }
        });
    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int i, int i1, int i2) {
        dateET.setText(i + "/" + i1 + "/" + i2);
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
