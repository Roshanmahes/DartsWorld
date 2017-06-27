package mprog.nl.a10973710.dartsworld;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

/**
 * Created by Gebruiker on 27-6-2017.
 */

public class Helper {

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
}
