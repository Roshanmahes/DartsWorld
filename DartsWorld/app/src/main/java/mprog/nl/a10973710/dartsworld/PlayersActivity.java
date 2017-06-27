package mprog.nl.a10973710.dartsworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import static mprog.nl.a10973710.dartsworld.Helper.navigateTo;


/**
 * Created by Roshan Mahes on 8-6-2017.
 */

public class PlayersActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "PlayersActivity";

    ArrayList<String> PlayerList = new ArrayList<String>();
    ArrayList<String> PlayerKeyList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getPlayerInfo();

        setListener(PlayerKeyList);
    }

    public void getPlayerInfo() {

        DatabaseReference playersDatabase = FirebaseDatabase.getInstance().getReference().child("players");

        playersDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Player post = postSnapshot.getValue(Player.class);

                        PlayerKeyList.add(postSnapshot.getKey());
                        PlayerList.add(String.valueOf(post.getFullName()));
                    }

                    setPlayerInfo(PlayerList);

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

    private void setPlayerInfo(ArrayList<String> playerList) {
        ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, playerList);

        ListView playerListView = (ListView) findViewById(R.id.player_list_view);

        playerListView.setAdapter(mArrayAdapter);
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

    private void setListener(final ArrayList<String> playerKeyList) {
        ListView playerListView = (ListView) findViewById(R.id.player_list_view);
        playerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String playerName =  playerKeyList.get(position);
                startPlayerActivity(playerName);
            }

        });
    }

    public void startPlayerActivity(String playerName) {
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putExtra("playerName", playerName);
        this.startActivity(intent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigateTo(PlayersActivity.this, id, drawer);
        return true;
    }
}