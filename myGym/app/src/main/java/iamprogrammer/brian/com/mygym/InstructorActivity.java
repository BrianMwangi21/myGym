package iamprogrammer.brian.com.mygym;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InstructorActivity extends AppCompatActivity {

    Bundle extras;
    RecyclerView recyclerView;
    ArrayList<String> iNames, iGender, iEmail, iPhone;
    DatabaseReference mDatabase;
    int images[] = { R.drawable.trainer1, R.drawable.trainer2, R.drawable.trainer3 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title_activity_instructor);

        // Get bundle
        extras = getIntent().getExtras();

        // Init instructors
        iNames = new ArrayList<>();
        iGender = new ArrayList<>();
        iEmail = new ArrayList<>();
        iPhone = new ArrayList<>();

        getInstructors();
    }

    public void getInstructors() {
        mDatabase = FirebaseDatabase.getInstance().getReference("instructors");
        mDatabase.orderByChild("email").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if( dataSnapshot.exists() ) {
                    for( DataSnapshot childSnapshot : dataSnapshot.getChildren() ) {
                        Instructor instructor = childSnapshot.getValue(Instructor.class);
                        iNames.add( instructor.getName() );
                        iGender.add( instructor.getGender() );
                        iEmail.add( instructor.getEmail() );
                        iPhone.add( instructor.getPhonenumber() );
                    }

                    showInstructors();
                }else {
                    Toast.makeText( InstructorActivity.this, "Not found", Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText( InstructorActivity.this, "Ref cancelled", Toast.LENGTH_SHORT ).show();
            }
        });
    }

    public void showInstructors() {
        recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        CustomInstructors customInstructors = new CustomInstructors( images, iNames, iGender, iEmail, iPhone );
        recyclerView.setAdapter(customInstructors);
    }

}
