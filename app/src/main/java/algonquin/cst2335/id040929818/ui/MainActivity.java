package algonquin.cst2335.id040929818.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import algonquin.cst2335.id040929818.data.MainViewModel;
import algonquin.cst2335.id040929818.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding variableBinding;
    private MainViewModel model;

    @Override
    //test
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        model = new ViewModelProvider(this).get(MainViewModel.class);

        model.editString.observe(this, string -> {
            variableBinding.textview.setText("Your edit text has " + string);
        });

        model.isChecked.observe(this, isChecked -> {
            variableBinding.radiobutton.setChecked(isChecked);
            variableBinding.switchbutton.setChecked(isChecked);
            variableBinding.checkbox.setChecked(isChecked);

            Toast.makeText(this, "The value is now: " + isChecked, Toast.LENGTH_SHORT).show();
        });

        variableBinding.button.setOnClickListener(click -> {
            model.editString.postValue(variableBinding.edittext.getText().toString());
        });

        variableBinding.checkbox.setOnCheckedChangeListener((btn, isChecked) -> {
            model.isChecked.postValue(isChecked);
        });

        variableBinding.switchbutton.setOnCheckedChangeListener((btn, isChecked) -> {
            model.isChecked.postValue(isChecked);
        });

        variableBinding.radiobutton.setOnCheckedChangeListener((btn, isChecked) -> {
            model.isChecked.postValue(isChecked);
        });

        variableBinding.imagebutton.setOnClickListener(click -> {
            Toast.makeText(
                    this,
                    String.format("The width = %d, the height = %d", variableBinding.imagebutton.getWidth(), variableBinding.imagebutton.getHeight()),
                    Toast.LENGTH_SHORT
            ).show();
        });
    }
}