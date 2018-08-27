package com.example.risa.notifications;

import android.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.app.PendingIntent;
import android.net.Uri;
public class MainActivity extends AppCompatActivity {

    private int notification_id = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
    public void launchSecretNotification(View view) {
        createNotification(Notification.VISIBILITY_SECRET, getString(R.string.secret_text));
    }

    public void createNotification(int visibility, String text) {
        // create a new notification
        Notification notification  = new Notification.Builder(this)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setContentTitle("PTM notification")
                .setContentText(text)
                .setSmallIcon(R.drawable.heart)
                .setAutoCancel(true)
                .setVisibility(visibility).build();
        // connect notification manager
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Adds the back stack - see manifest
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(SecondView.class);
        // create action intent to open application in device (ResultActivity)
        Intent contentIntent = new Intent(this,SecondView.class);
        // adds the Intent to the top of the stack
        stackBuilder.addNextIntent(contentIntent);
        // gets a PendingIntent containing the entire back stack
        PendingIntent contentPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        // make a new notification with a new unique id
        notification_id++;
        notificationManager.notify(notification_id, notification);
        // create new screen
        //Intent intent = new Intent(this, SecondView.class);
        //startActivity(intent);
    }
    */
    public void launchHeadsUpNotification(View view) {

        // create action intent to open application in device (ResultActivity)
        Intent contentIntent = new Intent(this,SecondView.class);
        // Adds the back stack - see manifest
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // adds the Intent to the top of the stack
        stackBuilder.addParentStack(SecondView.class);
        // adds the Intent to the top of the stack
        stackBuilder.addNextIntent(contentIntent);
        // gets a PendingIntent containing the entire back stack
        PendingIntent contentPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // create a notification
        Notification notification = new Notification.Builder(this)
                .setCategory(Notification.CATEGORY_PROMO)
                .setContentTitle("Photo notification")
                .setContentText("Do you want to see your memories in May?")
                .setSmallIcon(R.drawable.heart)
                .setAutoCancel(true)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .addAction(android.R.drawable.ic_menu_view, "View details", contentPendingIntent)
                .setContentIntent(contentPendingIntent)
                .setPriority(Notification.PRIORITY_HIGH)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000}).build(); // wait, vibrate, wait, ...
        // cancel the notification after it is launched
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        // connect notification manager and create unique notification
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notification_id++;
        notificationManager.notify(notification_id, notification);
    }

}
