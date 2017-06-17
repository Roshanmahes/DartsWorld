package mprog.nl.a10973710.dartsworld;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Roshan Mahes on 14-6-2017.
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
        this.calendarAct.startDateActivity(result);
    }
}
