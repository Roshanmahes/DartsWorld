package mprog.nl.a10973710.dartsworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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

/**
 * Created by Roshan Mahes on 8-6-2017.
 */

public class PlayerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private static final String TAG = "PlayerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Bundle extras = getIntent().getExtras();
        String playerName = extras.getString("playerName");

        getFromDB(playerName);
    }

    public void getFromDB(final String playerName) {

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Player player = dataSnapshot.child("players").child(playerName).getValue(Player.class);

                ListView playerInfoList = (ListView) findViewById(R.id.player_info_list);
                ArrayList<PlayerProperty> propertyList = processPlayerInfo(player);

                PropertyListAdapter adapter = new PropertyListAdapter(PlayerActivity.this,
                        R.layout.adapter_view_player, propertyList);
                playerInfoList.setAdapter(adapter);

                setPlayerName(player.fullName);
                setNationImage(player.country);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, databaseError.toException());
            }
        };
        mDatabase.addListenerForSingleValueEvent(postListener);
    }

    private void setPlayerName(String name) {
        TextView playerName = (TextView) findViewById(R.id.playerName);
        playerName.setText(name);
    }

    private void setNationImage(String country) {
        ImageView nationFlight = (ImageView) findViewById(R.id.nationFlight);
        String nationLink = "https://firebasestorage.googleapis.com/v0/b/" +
                "dartsworld-e9f85.appspot.com/o/" + country + ".png?alt=media";
        Picasso.with(PlayerActivity.this).load(nationLink).fit().into(nationFlight);
    }

    private ArrayList<PlayerProperty> processPlayerInfo(Player player) {

        ArrayList<PlayerProperty> propertyList = new ArrayList<>();

        PlayerProperty nickName = new PlayerProperty("Nickname", player.nickName);
        PlayerProperty twitter = new PlayerProperty("Twitter", player.twitter);
        PlayerProperty country = new PlayerProperty("Country", player.country);
        PlayerProperty born = new PlayerProperty("Born", player.born);
        PlayerProperty darts = new PlayerProperty("Darts", player.darts);
        PlayerProperty walkOn = new PlayerProperty("Walk-on", player.walkOn);
        PlayerProperty money = new PlayerProperty("Money", "£" + player.money);
        PlayerProperty pos = new PlayerProperty("Position (difference)", String.valueOf(player.currPos)
                + " (" + String.valueOf(player.prevPos - player.currPos) + ")");
        PlayerProperty majors = new PlayerProperty("Majors", String.valueOf(player.majors));
        PlayerProperty champ = new PlayerProperty("World champion", String.valueOf(player.champ));
        PlayerProperty nineDarts = new PlayerProperty("Nine darts (televised)",
                String.valueOf(player.nineDarts) + " (" + String.valueOf(player.nineDartsTelevised) + ")");

        PlayerProperty highAvg;
        if (player.highAvg == 0) {
            highAvg = new PlayerProperty("Highest average", "N/A");
        } else {
            highAvg = new PlayerProperty("Highest average", String.valueOf(player.highAvg));
        }

        propertyList.add(nickName); propertyList.add(twitter); propertyList.add(country);
        propertyList.add(born); propertyList.add(darts); propertyList.add(walkOn);
        propertyList.add(money); propertyList.add(pos); propertyList.add(majors);
        propertyList.add(champ); propertyList.add(highAvg); propertyList.add(nineDarts);

        return propertyList;
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

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
        } else if (id == R.id.nav_ranking) {
            Intent intent = new Intent(this, RankingActivity.class);
            this.startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
