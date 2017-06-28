package mprog.nl.a10973710.dartsworld;

import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.AdapterView;
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

/**
 * Created by Roshan Mahes on 17-6-2017.
 */

public class RankingActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    ArrayList<String> PlayerList = new ArrayList<String>();
    ArrayList<String> playerKeyList = new ArrayList<String>();
    ArrayList<Integer> DifferenceList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        if (isConnectedToInternet(RankingActivity.this)) {

            setUpBars(RankingActivity.this, "PDC Order of Merit");

            getPlayerInfo();

            setPlayerClickListener(playerKeyList);

        } else {
            displayAlertDialog(RankingActivity.this);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigateTo(RankingActivity.this, id, drawer);
        return true;
    }

    public void getPlayerInfo() {

        DatabaseReference playersDatabase = FirebaseDatabase.getInstance().getReference().child("players");

        playersDatabase.orderByChild("currPos").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<KeyValuePair> pList = new ArrayList<>();

                try {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Player post = postSnapshot.getValue(Player.class);

                        playerKeyList.add(postSnapshot.getKey());
                        PlayerList.add(String.valueOf(post.getFullName()));
                        DifferenceList.add(post.getCurrPos() - post.getPrevPos());
                        int difference = post.getCurrPos() - post.getPrevPos();
                        KeyValuePair player = new KeyValuePair(String.valueOf(difference), post.getFullName());
                        pList.add(player);
                    }

                    RankingListAdapter adapter = new RankingListAdapter(RankingActivity.this,
                            R.layout.adapter_view_ranking, pList);
                    ListView rankingList = (ListView) findViewById(R.id.rankingList);
                    rankingList.setAdapter(adapter);

                } catch (Exception ignored) {}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void setPlayerClickListener(final ArrayList<String> playerKeyList) {
        ListView rankingList = (ListView) findViewById(R.id.rankingList);
        rankingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String playerName =  playerKeyList.get(position);
                startPlayerActivity(playerName, RankingActivity.this);
            }

        });
    }
}
