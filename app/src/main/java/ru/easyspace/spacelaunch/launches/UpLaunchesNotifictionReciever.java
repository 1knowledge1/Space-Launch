package ru.easyspace.spacelaunch.launches;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

import ru.easyspace.spacelaunch.R;

public class UpLaunchesNotifictionReciever extends BroadcastReceiver {
    public static final String CHANNEL_LAUNCHES = "launches";
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_LAUNCHES);
        Uri sound_uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSmallIcon(R.drawable.ic_page_launches)
                .setContentTitle(intent.getStringExtra("Title"))
                .setContentText(intent.getStringExtra("Text"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(sound_uri)
                .setAutoCancel(true);
     
        nm.notify(intent.getIntExtra("ID",0),builder.build());
     }
}
