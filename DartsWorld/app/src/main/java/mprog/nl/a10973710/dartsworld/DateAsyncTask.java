package mprog.nl.a10973710.dartsworld;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import static android.content.ContentValues.TAG;

/**
 * Created by Gebruiker on 14-6-2017.
 */

class DateAsyncTask extends AsyncTask<String, Integer, String>{

    private Context context;
    private CalendarActivity calendarAct;

    DateAsyncTask(CalendarActivity calendarActivity) {
        this.calendarAct = calendarActivity;
        this.context = this.calendarAct.getApplicationContext();
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(context, "loading scores...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String... params) {

        String link = "http://www.sofascore.com/darts//" + params[0] + "/json";
        Log.d(TAG, "link: " + link);
        return HttpRequestHelper.downloadFromServer(link);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        JSONObject scoreObj = null;
    }
}
