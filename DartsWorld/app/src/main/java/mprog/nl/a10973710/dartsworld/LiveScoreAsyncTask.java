package mprog.nl.a10973710.dartsworld;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

/**
 * Created by Roshan Mahes on 10-6-2017.
 */

class LiveScoreAsyncTask extends AsyncTask<String, Integer, String>{

    private MainActivity mainAct;

    LiveScoreAsyncTask(MainActivity main) { this.mainAct = main; }

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

        Log.d(TAG, liveScoreObj.toString());
        this.mainAct.fetchLiveScore(liveScoreObj);
    }
}
