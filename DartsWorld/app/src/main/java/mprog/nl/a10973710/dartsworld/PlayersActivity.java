/*
 * Created by Roshan Mahes on 8-6-2017.
 */

package mprog.nl.a10973710.dartsworld;

import android.os.Bundle;
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
import static mprog.nl.a10973710.dartsworld.Helper.startPlayerActivity;

public class PlayersActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener{

    ArrayList<String> PlayerList = new ArrayList<String>();
    ArrayList<String> PlayerKeyList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);

        if (isConnectedToInternet(PlayersActivity.this)) {

            setUpBars(PlayersActivity.this, "Players");

            getPlayerInfo();
            setListener(PlayerKeyList);

        } else {
            displayAlertDialog(PlayersActivity.this);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigateTo("PlayersActivity", PlayersActivity.this, id, drawer);
        return true;
    }

    public void getPlayerInfo() {

        DatabaseReference playersDatabase = FirebaseDatabase.getInstance().getReference().child("players");

        playersDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Player post = postSnapshot.getValue(Player.class); // dit veranderen in playerSnapshot

                        PlayerKeyList.add(postSnapshot.getKey());
                        PlayerList.add(String.valueOf(post.getFullName()));
                    }

                    setPlayerInfo(PlayerList);

                } catch (Exception ignored) {}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void setPlayerInfo(ArrayList<String> playerList) {
        ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, playerList);

        ListView playerListView = (ListView) findViewById(R.id.player_list_view);
        playerListView.setAdapter(mArrayAdapter);
    }

    private void setListener(final ArrayList<String> playerKeyList) {
        ListView playerListView = (ListView) findViewById(R.id.player_list_view);
        playerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String playerName =  playerKeyList.get(position);
                startPlayerActivity(playerName, PlayersActivity.this);
            }

        });
    }
}