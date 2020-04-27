package com.example.simpleasynctask;

import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class SimpleAsyncTask extends AsyncTask <Void, Integer, String> {
    private WeakReference<TextView> mTextView;
    private WeakReference<ProgressBar> mProgressBar;
    private int SLEEP;

    // Constructor that provides a reference to the TextView from the MainActivity
    SimpleAsyncTask(TextView TV, ProgressBar PB) {
        mTextView = new WeakReference<>(TV);
        mProgressBar = new WeakReference<>(PB);
    }

    /**
     * Runs on the background thread.
     *
     * @return Returns the string including the amount of time that
     * the background thread slept.
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Random r = new Random();
        int n = r.nextInt(11);
        SLEEP = n * 200;
        mProgressBar.get().setMax(SLEEP);
        mProgressBar.get().setProgress(0);
    }

    @Override
    protected String doInBackground(Void... voids) {
        int n = SLEEP/2000;
        // Sleep for the random amount of time
        try {
            for (int i = 1; i <= n; i++){
                Thread.sleep(200);
                publishProgress(i*200);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Return a String result
        return "Awake at last after sleeping for " + SLEEP + " milliseconds!";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mTextView.get().setText("Sleeping for " + values[0] + " miliseconds...");
        mProgressBar.get().incrementProgressBy(200);
    }

    protected void onPostExecute(String result) {
        mTextView.get().setText(result);
    }
}
