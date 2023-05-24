package com.example.photogenerator;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 200; i++) {
            createImage(i);
        }
    }

    private void createImage(int imageNr) {
        int width = 200;
        int height = 200;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawRect(0F, 0F, (float) width, (float) height, paint);

        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String fileName = "ImageNr_" + imageNr + ".png";
        File file = new File(directory, fileName);
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

            MediaScannerConnection.scanFile(this, new String[]{file.toString()}, null,
                    (path, uri) -> {
                        Log.i("PhotoGenerator", "Photos were generated successfully!");
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
