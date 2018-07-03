package iamprogrammer.brian.com.mygym;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView mainTitle, subTitle;
    RecyclerView recyclerView;
    Bundle extras;
    DatabaseReference mDatabase;
    User user;
    ArrayList<Session> sessions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        // Get bundle
        extras = getIntent().getExtras();

        // Init sessions
        sessions = new ArrayList<>();

        // Get views
        mainTitle = headerView.findViewById(R.id.navbar_title);
        subTitle = headerView.findViewById(R.id.navbar_subtitle);
        subTitle.setText(extras.getString("email"));
        getUserData( extras.getString("email") );
    }

    public void getUserData( String email ) {
        // get user info
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mDatabase.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if( dataSnapshot.exists() ) {
                    for( DataSnapshot childSnapshot : dataSnapshot.getChildren() ) {
                        user = childSnapshot.getValue(User.class);

                        // Populate the data
                        mainTitle.setText( user.getUsername() );
                    }
                }else {
                    Toast.makeText( MainActivity.this, "Email not found", Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText( MainActivity.this, "Ref cancelled", Toast.LENGTH_SHORT ).show();
            }
        });


        mDatabase = FirebaseDatabase.getInstance().getReference("sessions");
        mDatabase.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if( dataSnapshot.exists() ) {
                    for( DataSnapshot childSnapshot : dataSnapshot.getChildren() ) {
                        Session session =  childSnapshot.getValue(Session.class);
                        sessions.add( session );
                    }

                    showUpcomingSessions();
                }else {
                    Toast.makeText( MainActivity.this, "Email not found", Toast.LENGTH_SHORT ).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText( MainActivity.this, "Ref cancelled", Toast.LENGTH_SHORT ).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finishAffinity();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gyms) {
            // Go to profile
            Intent intent = new Intent(MainActivity.this, GymsActivity.class);
            intent.putExtras( extras );
            startActivity( intent );
        } else if (id == R.id.nav_sessions) {
            // Go to sessions
            Intent intent = new Intent( MainActivity.this, AllSessionsActivity.class );
            intent.putExtras( extras );
            startActivity( intent );
        } else if (id == R.id.nav_profile) {
            // Go to profile
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            intent.putExtras( extras );
            startActivity( intent );
        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public  void showUpcomingSessions() {
        recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        CustomSessions customSessions = new CustomSessions( this, sessions, extras );
        recyclerView.setAdapter(customSessions);
    }
}
