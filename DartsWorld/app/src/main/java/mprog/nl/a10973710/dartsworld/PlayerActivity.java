package mprog.nl.a10973710.dartsworld;

import android.content.Intent;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static mprog.nl.a10973710.dartsworld.Constants.FIRST_COLUMN;
import static mprog.nl.a10973710.dartsworld.Constants.SECOND_COLUMN;
import static mprog.nl.a10973710.dartsworld.R.id.imageView;

public class PlayerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;
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
        mStorageRef = FirebaseStorage.getInstance().getReference();

        // retrieve player info
        getFromDB();
    }

    public void getFromDB() {

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get our object out of the database
                Player player = dataSnapshot.child("players").child("Anderson G").getValue(Player.class);

                ListView playerInfoList = (ListView) findViewById(R.id.player_info_list);

                PlayerProperty name = new PlayerProperty("Name", player.fullName);
                PlayerProperty nickName = new PlayerProperty("Nickname", player.nickName);
                PlayerProperty twitter = new PlayerProperty("Twitter", player.twitter);
                PlayerProperty country = new PlayerProperty("Country", player.country);
                PlayerProperty born = new PlayerProperty("Born", player.born);
                PlayerProperty darts = new PlayerProperty("Darts", player.darts);
                PlayerProperty money = new PlayerProperty("Money", player.money);
                PlayerProperty pos = new PlayerProperty("Position (difference)", String.valueOf(player.currPos)
                        + " (" + String.valueOf(player.prevPos - player.currPos) + ")");
                PlayerProperty majors = new PlayerProperty("Majors", String.valueOf(player.majors));
                PlayerProperty champ = new PlayerProperty("World champion", String.valueOf(player.champ));
                PlayerProperty highAvg = new PlayerProperty("Highest average", String.valueOf(player.highAvg));
                PlayerProperty nineDarts = new PlayerProperty("Nine darts (televised)",
                        String.valueOf(player.nineDarts) + " (" + String.valueOf(player.nineDartsTelevised) + ")");

                // add the PlayerProperty objects to an ArrayList
                ArrayList<PlayerProperty> propertyList = new ArrayList<>();

                propertyList.add(name);
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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting the data failed, log a message
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
