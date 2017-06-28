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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Roshan Mahes on 27-6-2017.
 */

public class Helper {

// wordt overal gebruikt
    public static void navigateTo(Activity activity, int id, DrawerLayout drawer) {

        if (id == R.id.nav_home) {
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
        } else if (id == R.id.nav_calendar) {
            Intent intent = new Intent(activity, CalendarActivity.class);
            activity.startActivity(intent);
        } else if (id == R.id.nav_players) {
            Intent intent = new Intent(activity, PlayersActivity.class);
            activity.startActivity(intent);
        } else if (id == R.id.nav_info) {
            Intent intent = new Intent(activity, InfoActivity.class);
            activity.startActivity(intent);
        } else if (id == R.id.nav_tournaments) {
            Intent intent = new Intent(activity, TournamentsActivity.class);
            activity.startActivity(intent);
        } else if (id == R.id.nav_ranking) {
            Intent intent = new Intent(activity, RankingActivity.class);
            activity.startActivity(intent);
        }

        drawer.closeDrawer(GravityCompat.START);
    }

    /// SOURCE!
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

    //////////
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

    static void existsTournamentInfo(final String tournamentName, final Activity activity) {
        // alleen in MainActivity en DateActivity

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
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private static void tournamentClick(String tournamentName, Activity activity) {
        Intent intent = new Intent(activity, TournamentActivity.class);
        intent.putExtra("tournamentName", tournamentName);
        activity.startActivity(intent);
    }

    static void loadPlayerInfo(final Activity activity, String playerName) {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        final String finalPlayerName = playerName;
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.child("players").hasChild(finalPlayerName)) {
                    startPlayerActivity(finalPlayerName, activity);
                } else {
                    Toast.makeText(activity, "No player information available.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    static void startPlayerActivity(String playerName, Activity activity) {
        Intent intent = new Intent(activity, PlayerActivity.class);
        intent.putExtra("playerName", playerName);
        activity.startActivity(intent);
    }
}
