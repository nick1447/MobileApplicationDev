package algonquin.cst2335.id040929818;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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

        ActivityResultLauncher<Intent> cameraResultIntent = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Bitmap image = data.getParcelableExtra("data");
                        //variableBinding.imageView.setImageBitmap(image);
                        FileOutputStream fileOut;
                        try {
                            fileOut = openFileOutput("Test.png", Context.MODE_PRIVATE);
                            image.compress(Bitmap.CompressFormat.PNG, 100, fileOut);
                            Log.w("SecondActivity", getFilesDir().toString());
                            fileOut.flush();
                            fileOut.close();
                            File inputFile = new File(getFilesDir(), "Test.png");
                            if(inputFile.exists()) {
                                Log.w("SecondActivity", Boolean.toString(inputFile.exists()));
                            }
                        } catch(IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        variableBinding.changeImageButton.setOnClickListener(click -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraResultIntent.launch(cameraIntent);
        });

        File inputFile = new File(getFilesDir(), "Test.png");
        if(inputFile.exists()) {
            Log.w("SecondActivity", "file exists");
            Bitmap image = BitmapFactory.decodeFile(getFilesDir().getPath() + "/Test.png");
            variableBinding.imageView.setImageBitmap(image);
        } else {
            Log.w("SecondActivity", "file does not exist");
        }
    }
}