/*
 * Created by Roshan Mahes on 14-6-2017.
 */

package mprog.nl.a10973710.dartsworld;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Loads all matches of a selected date using the SofaScore API.
 * Starts the DateActivity while giving these data.
 */

class DateAsyncTask extends AsyncTask<String, Integer, String>{

    private Context context;
    private CalendarActivity calendarAct;
    private String date;

    DateAsyncTask(CalendarActivity calendarActivity, String date) {
        this.calendarAct = calendarActivity;
        this.context = this.calendarAct.getApplicationContext();
        this.date = date;
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(context, "loading scores...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String... params) {
        String link = "http://www.sofascore.com/darts//" + params[0] + "/json";
        return HttpRequestHelper.downloadFromServer(link);
    }

    @Override
    protected void onPostExecute(String result) {

        super.onPostExecute(result);

        JSONObject scoreObj = null;
        try {
            scoreObj = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        assert scoreObj != null;
        calendarAct.startDateActivity(result, date);
    }
}