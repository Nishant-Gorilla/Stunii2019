package obllivionsoft.djole.nis.rs.stusdeals.controller.Firebase;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import obllivionsoft.djole.nis.rs.stusdeals.R;
import obllivionsoft.djole.nis.rs.stusdeals.view.activity.MainActivity;

import static android.content.ContentValues.TAG;

public class fcmServiceReal extends FirebaseMessagingService {
    public fcmServiceReal() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        Log.e("hello", "Notification Message Body: " + remoteMessage.getNotification().getBody());



        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
      /*  String notificationMessage = remoteMessage.getData().get("_id");
        notificationIntent.putExtra("dealId", notificationMessage);
        notificationIntent.putExtra("_id", notificationMessage);*/
        PendingIntent pendingIntent= PendingIntent.getActivity(this,0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification n  = new Notification.Builder(this)
                .setContentTitle("From: " + remoteMessage.getFrom())
                .setContentText(remoteMessage.getNotification().getBody())
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.logosignup)
                .setVibrate(new long[] {1000, 100, 1000})
                .setContentIntent(pendingIntent)
                .build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(385, n);
    }
}
