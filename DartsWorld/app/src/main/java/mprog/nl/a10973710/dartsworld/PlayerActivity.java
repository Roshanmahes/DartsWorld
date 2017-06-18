package mprog.nl.a10973710.dartsworld;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private DatabaseReference mDatabase;
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

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Bundle extras = getIntent().getExtras();
        String playerName = extras.getString("playerName");

        // retrieve player info
        getFromDB(playerName);
    }

    public void getFromDB(final String playerName) {

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get our object out of the database
                Player player = dataSnapshot.child("players").child(playerName).getValue(Player.class);

                ListView playerInfoList = (ListView) findViewById(R.id.player_info_list);

                PlayerProperty nickName = new PlayerProperty("Nickname", player.nickName);
                PlayerProperty twitter = new PlayerProperty("Twitter", player.twitter);
                PlayerProperty country = new PlayerProperty("Country", player.country);
                PlayerProperty born = new PlayerProperty("Born", player.born);
                PlayerProperty darts = new PlayerProperty("Darts", player.darts);
                PlayerProperty money = new PlayerProperty("Money", "£" + player.money);
                PlayerProperty pos = new PlayerProperty("Position (difference)", String.valueOf(player.currPos)
                        + " (" + String.valueOf(player.prevPos - player.currPos) + ")");
                PlayerProperty majors = new PlayerProperty("Majors", String.valueOf(player.majors));
                PlayerProperty champ = new PlayerProperty("World champion", String.valueOf(player.champ));
                PlayerProperty highAvg = new PlayerProperty("Highest average", String.valueOf(player.highAvg));
                PlayerProperty nineDarts = new PlayerProperty("Nine darts (televised)",
                        String.valueOf(player.nineDarts) + " (" + String.valueOf(player.nineDartsTelevised) + ")");

                // add the PlayerProperty objects to an ArrayList
                ArrayList<PlayerProperty> propertyList = new ArrayList<>();

                propertyList.add(nickName);
                propertyList.add(twitter);
                propertyList.add(country);
                propertyList.add(born);
                propertyList.add(darts);
                propertyList.add(money);
                propertyList.add(pos);
                propertyList.add(majors);
                propertyList.add(champ);
                propertyList.add(highAvg);
                propertyList.add(nineDarts);

                PropertyListAdapter adapter = new PropertyListAdapter(PlayerActivity.this, R.layout.adapter_view_player, propertyList);
                playerInfoList.setAdapter(adapter);

                TextView playerName = (TextView) findViewById(R.id.playerName);
                playerName.setText(player.fullName);

                ImageView nationFlight = (ImageView) findViewById(R.id.nationFlight);
                String nationLink = "https://firebasestorage.googleapis.com/v0/b/" +
                        "dartsworld-e9f85.appspot.com/o/" + player.country + ".png?alt=media";
                Picasso.with(PlayerActivity.this).load(nationLink).fit().into(nationFlight);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Something went wrong:", databaseError.toException());
            }
        };
        mDatabase.addListenerForSingleValueEvent(postListener);
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
        getMenuInflater().inflate(R.menu.player, menu);
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
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
