package jmak.deye;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;

public class Scan extends AppCompatActivity {

    private TextView mTextMessage;
    private Button vBtnScan;

    private int scan(){
        
        return 1;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.scan_description);
                    vBtnScan.setVisibility(View.VISIBLE); //To set visible
                    scan();
                    return true;
                case R.id.navigation_notifications:
                    vBtnScan.setVisibility(View.INVISIBLE); //To set inivisible
                    mTextMessage.setText(R.string.title_history);
                    displayGraph();
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

        mTextMessage = (TextView) findViewById(R.id.message);
        vBtnScan=(Button)findViewById(R.id.btnScan);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
