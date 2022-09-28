package algonquin.cst2335.id040929818;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.widget.TextView;

import algonquin.cst2335.id040929818.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySecondBinding variableBinding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        Intent previousIntent = getIntent();
        String email = previousIntent.getStringExtra("EmailAddress");

        TextView textView = variableBinding.textView;
        textView.setText(String.format("Welcome back %s", email));

        variableBinding.callNumberButton.setOnClickListener(click -> {
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:" + variableBinding.editTextPhone.getText().toString()));
            startActivity(call);
        });

        variableBinding.changeImageButton.setOnClickListener(click -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            
        });
    }
}