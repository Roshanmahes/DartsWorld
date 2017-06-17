package mprog.nl.a10973710.dartsworld;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Roshan Mahes on 10-6-2017.
 */

class LiveScoreAsyncTask extends AsyncTask<String, Integer, String>{

    private Context context;
    private MainActivity mainAct;

    LiveScoreAsyncTask(MainActivity main) {
        this.mainAct = main;
        this.context = this.mainAct.getApplicationContext();
    }

    @Override
    protected void onPreExecute() {
        Toast.makeText(context, "retrieving data...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String... params) {

        String link = "http://www.sofascore.com/darts/livescore/json";
        return HttpRequestHelper.downloadFromServer(link);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        JSONObject liveScoreObj = null;
        try {
            liveScoreObj = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        assert liveScoreObj != null;

        this.mainAct.fetchLiveScore(liveScoreObj);
    }
}
