package mprog.nl.a10973710.dartsworld;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static mprog.nl.a10973710.dartsworld.Helper.displayAlertDialog;
import static mprog.nl.a10973710.dartsworld.Helper.isConnectedToInternet;
import static mprog.nl.a10973710.dartsworld.Helper.navigateTo;

/**
 * Created by Roshan Mahes on 19-6-2017.
 */

public class TournamentsActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "TournamentsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournaments);

        if (isConnectedToInternet(TournamentsActivity.this)) {

            setUpBars(TournamentsActivity.this, "Tournaments");

            getTournaments();

        } else {
            displayAlertDialog(TournamentsActivity.this);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigateTo("TournamentsActivity", TournamentsActivity.this, id, drawer);
        return true;
    }

    public void getTournaments() {

        DatabaseReference tourRef = FirebaseDatabase.getInstance().getReference().child("tournaments");

        tourRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayList<String> tournamentsList = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    tournamentsList.add(postSnapshot.getKey());
                }

                setTournaments(tournamentsList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, databaseError.toString());
            }
        });
    }

    public void setTournaments(ArrayList<String> tournamentsList) {
        ListView tournamentsView = (ListView) findViewById(R.id.tournamentsView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(TournamentsActivity.this,
                android.R.layout.simple_list_item_1, tournamentsList);
        tournamentsView.setAdapter(adapter);

        setListener(tournamentsList);
    }

    private void setListener(final ArrayList<String> tournamentsList) {
        ListView tournamentsView = (ListView) findViewById(R.id.tournamentsView);
        tournamentsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tournamentName = tournamentsList.get(position);
                startPlayerActivity(tournamentName);
            }
        });
    }

    public void startPlayerActivity(String tournamentName) {
        Intent intent = new Intent(this, TournamentActivity.class);
        intent.putExtra("tournamentName", tournamentName);
        this.startActivity(intent);
    }
}
