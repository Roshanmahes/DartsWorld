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

        Log.d(TAG, "onCreate: Started.");
        ListView playerInfoList = (ListView) findViewById(R.id.player_info_list);

        // create the PlayerPropeerty objects
        PlayerProperty name = new PlayerProperty("name", "Phil Taylor");
        PlayerProperty nickName = new PlayerProperty("nickname", "The Power");
        PlayerProperty twitter = new PlayerProperty("twitter", "@PhilTaylor");
        PlayerProperty country = new PlayerProperty("country", "England");
        PlayerProperty born = new PlayerProperty("born", "13-Aug-60");
        PlayerProperty darts = new PlayerProperty("darts", "Target 'Phil Taylor' Power 9Five Gen4, 26g");
        PlayerProperty money = new PlayerProperty("money", "£324,250");
        PlayerProperty pos = new PlayerProperty("position", "8 (-1)");
        PlayerProperty majors = new PlayerProperty("majors", "82");
        PlayerProperty champ = new PlayerProperty("world champion", "16");
        PlayerProperty highAvg = new PlayerProperty("highest average", "118.66");
        PlayerProperty nineDarts = new PlayerProperty("nine darts (televised)", "21 (11)");

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

        PropertyListAdapter adapter = new PropertyListAdapter(this, R.layout.adapter_view_player, propertyList);
        playerInfoList.setAdapter(adapter);


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

    public void getFromDB(View view) {

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // get our object out of the database
                Player player = dataSnapshot.child("players").child("Anderson G").getValue(Player.class);

                TextView tv1 = (TextView) findViewById(R.id.tv1);
                Log.d(TAG, player.fullName);
                tv1.setText(player.twitter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting the data failed, log a message
                Log.w(TAG, "Something went wrong:", databaseError.toException());
            }
        };
        mDatabase.addListenerForSingleValueEvent(postListener);
    }
}
