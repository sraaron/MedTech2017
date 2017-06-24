package jmak.deye;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.provider.MediaStore;
import android.widget.Toast;

import android.content.Intent;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.PendingIntent.getActivity;
import static jmak.deye.R.id.graph;

public class Scan extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap mImageBitmap;
    private String mCurrentPhotoPath;
    private ImageView mImageView;
    private TextView mTextMessage;
    private Button vBtnScan;

    private GraphView graphHistory;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            graphHistory.setVisibility(View.INVISIBLE); //sets the graphHistory to be invisible
            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.scan_description);
                    vBtnScan.setVisibility(View.VISIBLE); //To set visible
                    return true;
                case R.id.navigation_notifications:
                    vBtnScan.setVisibility(View.INVISIBLE); //To set inivisible
                    mTextMessage.setText(R.string.title_history);
                    return true;
            }
            return false;
        }

    };

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        ContextWrapper c = new ContextWrapper(this);
        File storageDir = c.getFilesDir();
        File imageDir = new File(getFilesDir(), "Images");
        imageDir.mkdirs();
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                imageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPhotoPath));
                System.out.println(mImageBitmap.getHeight());
                System.out.println(mImageBitmap.getWidth());
                System.out.println("DONE!");
                //mImageView.setImageBitmap(mImageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(View view)
    {
        Intent intent = new Intent(Scan.this, ColorBlobDetectionActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("GRANTED");
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    System.out.println("DENIEDs");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        mTextMessage = (TextView) findViewById(R.id.message);
        vBtnScan=(Button)findViewById(R.id.btnScan);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, 5, 17);

//        DateTime d1 = new DateTime(2017, 5, 17);
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 2);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 2);
        Date d3 = calendar.getTime();
        calendar.add(Calendar.DATE, 2);
        Date d4 = calendar.getTime();
        calendar.add(Calendar.DATE, 2);
        Date d5 = calendar.getTime();

        android.text.format.DateFormat.format("MM-dd", d4);


        graphHistory = (GraphView) findViewById(R.id.graph);
        graphHistory.setTitle("White Blood Cell Count");
        graphHistory.getGridLabelRenderer().setHorizontalAxisTitle("              Date (Days in June)");
        graphHistory.getGridLabelRenderer().setVerticalAxisTitle("Concentration of WBC");
        graphHistory.getGridLabelRenderer().setLabelHorizontalHeight(50);
        graphHistory.getGridLabelRenderer().setPadding(80);




        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(14, 50),
                new DataPoint(16, 75),
                new DataPoint(18, 80),
                new DataPoint(20, 115),
        });


//        graphHistory.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(Scan.this));
//        graphHistory.getGridLabelRenderer().setNumHorizontalLabels(4); // only 4 because of the space



        // set manual x bounds to have nice steps
        graphHistory.getViewport().setMinX(14);
        graphHistory.getViewport().setMaxX(21);
        graphHistory.getViewport().setMinY(0.0);
        graphHistory.getViewport().setMaxY(200.0);
        //graphHistory.getViewport().setXAxisBoundsManual(true);
        graphHistory.getViewport().setYAxisBoundsManual(true);

        graphHistory.getViewport().setScrollable(false); // enables horizontal scrolling
        graphHistory.getViewport().setScrollableY(false); // enables vertical scrolling
        graphHistory.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graphHistory.getViewport().setScalableY(true); // enables vertical zooming and scrolling

        graphHistory.addSeries(series);

// as we use dates as labels, the human rounding to nice readable numbers
// is not necessary
        //graphHistory.getGridLabelRenderer().setHumanRounding(false);
        /*
        //Check if permission is already granted
        //thisActivity is your activity. (e.g.: MainActivity.this)
        if (ContextCompat.checkSelfPermission(Scan.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
            // Give first an explanation, if needed.
            if (ActivityCompat.shouldShowRequestPermissionRationale(Scan.this, Manifest.permission.CAMERA)) {
                System.out.println("WANT PERMISSION");
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(Scan.this, new String[]{Manifest.permission.CAMERA}, 1);
            }
        }
        */
        /*
        Button capture = (Button) findViewById(R.id.btnScan);
        capture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        System.out.print("IO Exception");
                        // Error occurred while creating the File
                        //Log.i(TAG, "IOException");
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = null;
                        try {
                            photoURI = FileProvider.getUriForFile(Scan.this, BuildConfig.APPLICATION_ID + ".provider", photoFile);
                        } catch (Exception ex) {
                            System.out.println("Exception");
                        }
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                    }
                }
            }
        });*/
    }

}
