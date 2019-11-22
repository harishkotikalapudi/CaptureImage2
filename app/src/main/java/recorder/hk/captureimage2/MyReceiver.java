package recorder.hk.captureimage2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Toast;

import recorder.hk.dbHandler.DBHelper;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("recorder.hk.captureimage2")){
            DBHelper dbHelper = new DBHelper(context);
            Bundle extras = intent.getExtras();
            Toast.makeText(context, "Received: "+(Bitmap) intent.getParcelableExtra("image") ,Toast.LENGTH_SHORT).show();

            boolean status = dbHelper.insertImage(intent.getStringExtra("text"),(Bitmap) intent.getParcelableExtra("image"));
            if (status)
            Toast.makeText(context, "Saved Successfuly..", Toast.LENGTH_SHORT).show();

        }
    }
}
