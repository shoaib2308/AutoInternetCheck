package simplycodinghub.ciapp;

import android.animation.Animator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private MyInternetConnectionReceiver myInternetConnectionReceiver;
    private TextView internetStatus;
    private RelativeLayout rltConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        internetStatus = (TextView) findViewById(R.id.txtMsgStatus);


        rltConnection = findViewById(R.id.rltConnection);
        // Create a network change broadcast receiver.
        myInternetConnectionReceiver = new MyInternetConnectionReceiver();

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Create an IntentFilter instance.
        IntentFilter intentFilter = new IntentFilter();

        // Add network connectivity change action.
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

        // Register the broadcast receiver with the intent filter object.
        registerReceiver(myInternetConnectionReceiver, intentFilter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myInternetConnectionReceiver);
    }

    public class MyInternetConnectionReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            showMsgBar(netInfo != null && netInfo.isConnected());

        }
    }

    /**
     * @param isConnected this boolean variable will return connectivity status
     */
    private void showMsgBar(boolean isConnected) {
        if (isConnected) {
            rltConnection.setBackgroundColor(Color.GREEN);
            rltConnection.animate().alpha(1.0f).setDuration(4000).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    rltConnection.setVisibility(View.VISIBLE);
                    internetStatus.setText("Connected");
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    rltConnection.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            }).start();
        } else {
            rltConnection.setBackgroundColor(Color.RED);
            rltConnection.animate().alpha(1.0f).setDuration(4000).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    rltConnection.setVisibility(View.VISIBLE);
                    internetStatus.setText("Disconnected");
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    rltConnection.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            }).start();
        }
    }
}
