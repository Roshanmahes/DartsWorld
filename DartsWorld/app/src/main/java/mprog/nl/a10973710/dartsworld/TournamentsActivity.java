package mprog.nl.a10973710.dartsworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TournamentsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "Tournaments Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournaments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tournaments");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getTournaments();
    }

    public void getTournaments() {

        DatabaseReference playersDatabase = FirebaseDatabase.getInstance().getReference().child("tournaments");

        playersDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {

                    ArrayList<PlayerProperty> propertyList = new ArrayList<>();

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Log.d(TAG, "Volgens mij gaat t hier mis");
                        Tournament post = postSnapshot.getValue(Tournament.class);
                        // dit hoeft niet, gwn get("sponsor") doen

                        PlayerProperty property = new PlayerProperty(post.getSponsor(),postSnapshot.getKey());
                        propertyList.add(property);
                    }

                    ListView tournamentsView = (ListView) findViewById(R.id.tournamentsView);
                    TournamentsListAdapter adapter = new TournamentsListAdapter(TournamentsActivity.this, R.layout.adapter_view_tournaments, propertyList);
                    tournamentsView.setAdapter(adapter);

                } catch (Exception e) {
                    Log.e(TAG, "Exception e = " + e.getLocalizedMessage());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, databaseError.toString());
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
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tournaments, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(this, MainActivity.class);
            this.startActivity(intent);
        } else if (id == R.id.nav_calendar) {
            Intent intent = new Intent(this, CalendarActivity.class);
            this.startActivity(intent);
        } else if (id == R.id.nav_players) {
            Intent intent = new Intent(this, PlayersActivity.class);
            this.startActivity(intent);
        } else if (id == R.id.nav_info) {
            Intent intent = new Intent(this, InfoActivity.class);
            this.startActivity(intent);
        } else if (id == R.id.nav_tournaments) {
            Intent intent = new Intent(this, TournamentsActivity.class);
            this.startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void tournamentClick(View view) {
        TextView tournamentName = (TextView) view.findViewById(R.id.tournamentName);

        Intent intent = new Intent(this, TournamentActivity.class);
        intent.putExtra("tournamentName", tournamentName.getText());
        this.startActivity(intent);
    }
}
