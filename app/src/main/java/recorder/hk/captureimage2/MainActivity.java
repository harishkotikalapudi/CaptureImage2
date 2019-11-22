package recorder.hk.captureimage2;

import androidx.appcompat.app.AppCompatActivity;
import recorder.hk.dbHandler.DBHelper;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Bitmap imageBitmap;


    private IntentFilter filter =
            new IntentFilter("recorder.hk.captureimage2");

    private MyReceiver receiver =
            new MyReceiver();

    @Override
    public synchronized void onResume()
    {
        super.onResume();

        // Register the broadcast receiver.
        registerReceiver(receiver, filter);
    }

    @Override
    public synchronized void onPause()
    {
        super.onPause();
        // Unregister the receiver
        unregisterReceiver(receiver);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerReceiver(receiver, filter);
        getImages();
    }

    private void getImages() {

        DBHelper dbHelper = new DBHelper(this);
        ArrayList<String> imglist = dbHelper.getAllImages();
        if(imglist.size()>0)
        Toast.makeText(this, ""+imglist.get(imglist.size()-1), Toast.LENGTH_SHORT).show();
    }

    public void captureImage(View view){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();

            imageBitmap = (Bitmap) extras.get("data");
            Toast.makeText(this, ""+imageBitmap, Toast.LENGTH_SHORT).show();
            ImageView imageView =findViewById(R.id.imageView);
            imageView.setImageBitmap(imageBitmap);
        }
    }
    public void sendData(View view){
        EditText editText = findViewById(R.id.editText);

        Intent intent = new Intent("recorder.hk.captureimage2");
        intent.putExtra("text",editText.getText().toString());
        Toast.makeText(this, ""+imageBitmap, Toast.LENGTH_SHORT).show();
        intent.putExtra("image",imageBitmap);
        sendBroadcast(intent);
    }
}
