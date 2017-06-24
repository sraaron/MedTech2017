package jmak.deye;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
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

    private TextView mTextMessage;
    private Button vBtnScan;
    private GraphView graphHistory;

    private int scan(){
        
        return 1;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dashboard: //for the scan
                    mTextMessage.setText(R.string.scan_description);
                    vBtnScan.setVisibility(View.VISIBLE); //sets the scan button to be visible
                    graphHistory.setVisibility(View.INVISIBLE); //sets the graphHistory to be invisible
                    return true;
                case R.id.navigation_notifications: //for history
                    //Intent intent = new Intent(currentActivity.this, nextActivity.class);
                    //startActivity(intent);
                    mTextMessage.setText(R.string.history_description);
                    vBtnScan.setVisibility(View.INVISIBLE); //sets the scan button to be invisible
                    graphHistory.setVisibility(View.VISIBLE); //sets the graphHistory to be visible
                    return true;
            }
            return false;
        }

    };

    private void displayGraph() {

        
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

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


        mTextMessage = (TextView) findViewById(R.id.message);
        vBtnScan=(Button)findViewById(R.id.btnScan);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }



}
