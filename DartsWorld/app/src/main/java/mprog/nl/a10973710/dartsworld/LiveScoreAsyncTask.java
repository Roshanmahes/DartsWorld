/*
 * Created by Roshan Mahes on 10-6-2017.
 */

package mprog.nl.a10973710.dartsworld;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Loads all live matches using the SofaScore API.
 * The data is finally sent back to the MainActivity.
 */

class LiveScoreAsyncTask extends AsyncTask<String, Integer, String>{

    private MainActivity mainAct;

    LiveScoreAsyncTask(MainActivity main) {
        this.mainAct = main;
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
