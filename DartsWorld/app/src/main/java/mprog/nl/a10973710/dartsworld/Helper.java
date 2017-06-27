package mprog.nl.a10973710.dartsworld;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Gebruiker on 27-6-2017.
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

    static void existsTournamentInfo(final String tournamentName, final Activity activity) {
        // alleen in MainActivity en DateActivity

        DatabaseReference playersRef = FirebaseDatabase.getInstance().getReference().child("tournaments");

        playersRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    if (tournamentName.contains(postSnapshot.getKey())) {
                        tournamentClick(tournamentName, activity);
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
}
