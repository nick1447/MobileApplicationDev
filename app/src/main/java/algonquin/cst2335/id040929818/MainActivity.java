package algonquin.cst2335.id040929818;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import algonquin.cst2335.id040929818.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w("MainActivity", "onDestroy(): Any memory used by the application is freed..");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w("MainActivity", "onResume(): The application is now responding to user input.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w("MainActivity", "onStop(): The application is no longer visible.");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w("MainActivity", "onStart(): The application is now visible on screen.");
    }

    @Override
    //test
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        variableBinding.button.setOnClickListener(click -> startActivity(
            new Intent(MainActivity.this, SecondActivity.class)
                .putExtra("EmailAddress", variableBinding.emailAddressEdittext.getText().toString()))
        );


        //Log.w( "MainActivity", "In onCreate() - Loading Widgets" );
    }
}