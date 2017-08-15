package mochamad.ulin.nuha.ta;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Akshay Raj on 5/31/2016.
 * Snow Corporation Inc.
 * www.snowcorp.org
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    String title,no_hp;
    String massage,status,id,name;
    Intent intent;
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        bacaPreferensi();
        massage = remoteMessage.getNotification().getBody();
        title = remoteMessage.getNotification().getTitle();


        //     Toast.makeText(this, massage, Toast.LENGTH_SHORT).show();
        sendNotification(massage);

    }

    private void sendNotification(String message) {
        if (no_hp.toString().equals("0")) {
            intent = new Intent(this, Signin.class);
        }else{
            intent = new Intent(this, tampilnotif.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message));
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void bacaPreferensi() {
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
        no_hp = pref.getString("no_hp", "0");
    }
}