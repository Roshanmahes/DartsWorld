/*
 * Created by Roshan Mahes on 27-6-2017.
 */

package mprog.nl.a10973710.dartsworld;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Consists of methods which are used in several activities.
 */

public class Helper {

    private static final String TAG = "Helper";

    /**
     * Controls the navigation drawer.
     * Starts the right activity if that one is selected.
     */
    public static void navigateTo(String activityName, Activity activity, int id, DrawerLayout drawer) {

        if (id == R.id.nav_home && activityName != "MainActivity") {
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
        } else if (id == R.id.nav_calendar && activityName != "CalendarActivity") {
            Intent intent = new Intent(activity, CalendarActivity.class);
            activity.startActivity(intent);
        } else if (id == R.id.nav_players && activityName != "PlayersActivity") {
            Intent intent = new Intent(activity, PlayersActivity.class);
            activity.startActivity(intent);
        } else if (id == R.id.nav_info && activityName != "InfoActivity") {
            Intent intent = new Intent(activity, InfoActivity.class);
            activity.startActivity(intent);
        } else if (id == R.id.nav_tournaments && activityName != "TournamentsActivity") {
            Intent intent = new Intent(activity, TournamentsActivity.class);
            activity.startActivity(intent);
        } else if (id == R.id.nav_ranking && activityName != "RankingActivity") {
            Intent intent = new Intent(activity, RankingActivity.class);
            activity.startActivity(intent);
        }

        drawer.closeDrawer(GravityCompat.START);
    }

    /**
     * Checks internet connection. Returns true if connected.
     * Source:
     * https://stackoverflow.com/questions/15714122/checking-internet-connection-in-every-activity
     */
    static boolean isConnectedToInternet(Context context){

        ConnectivityManager connectivity = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (NetworkInfo anInfo : info)
                    if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    /**
     * Displays an alert dialog, saying that there is no internet connection available.
     */
    static void displayAlertDialog(final Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("No active internet connection available.");
        builder.setNeutralButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((Activity) context).recreate();
            }
        });
        builder.show();
    }

    /**
     * Only used in MainActivity and DateActivity.
     * Checks whether some string, containing a tournament name,
     * can be (partly) found in the Firebase database.
     */
    static void existsTournamentInfo(final String tournamentName, final Activity activity) {

        DatabaseReference playersRef = FirebaseDatabase.getInstance().getReference().child("tournaments");
        playersRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    if (tournamentName.contains(postSnapshot.getKey())) {
                        tournamentClick(postSnapshot.getKey(), activity);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, databaseError.toString());
            }
        });
    }

    /**
     * Starts the TournamentActivity giving the tournament name as inserted in Firebase.
     */
    private static void tournamentClick(String tournamentName, Activity activity) {
        Intent intent = new Intent(activity, TournamentActivity.class);
        intent.putExtra("tournamentName", tournamentName);
        activity.startActivity(intent);
    }

    /**
     * Checks whether there is some player info available of a player in Firebase.
     */
    static void loadPlayerInfo(final Activity activity, final String playerName) {

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.child("players").hasChild(playerName)) {
                    startPlayerActivity(playerName, activity);
                } else {
                    Toast.makeText(activity, "No player information available.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, databaseError.toString());
            }
        });
    }

    /**
     * Starts the PlayerActivity giving the player name as inserted in Firebase.
     */
    static void startPlayerActivity(String playerName, Activity activity) {
        Intent intent = new Intent(activity, PlayerActivity.class);
        intent.putExtra("playerName", playerName);
        activity.startActivity(intent);
    }
}