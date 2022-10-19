package algonquin.cst2335.id040929818;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app tests the ability to check if a password meets certain requirements
 * @author nickl
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    /**
     * TextView used to display prompt to enter password
     */
    private TextView passwordMessage;
    /**
     * Button used to run the logic to test the entered password
     */
    private Button loginButton;
    /**
     * EditText used to receive user inputted password
     */
    private EditText passwordEditText;

    @Override
    //test
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        passwordMessage = findViewById(R.id.passwordMessage);
        loginButton = findViewById(R.id.loginButton);
        passwordEditText = findViewById(R.id.passwordEditText);

        loginButton.setOnClickListener(click -> {
            if(checkPasswordComplexity(passwordEditText.getText().toString())) {
                passwordMessage.setText("Your password meets the requirements");
            } else {
                passwordMessage.setText("You shall not pass!");
            }
        });
    }

    /**
     * Checks if the passed in password parameter matches the password complexity requirements
     * @param password The password to run the requirement check on
     * @return Returns true if the password parameter meets the requirements, else return false
     */
    private boolean checkPasswordComplexity(String password) {
        boolean hasUpperCase, hasLowerCase, hasSpecialCharacter, hasNumber;
        hasNumber = hasUpperCase = hasLowerCase = hasSpecialCharacter = false;

        for(int index = 0; index < password.length(); index++) {
            if(Character.isDigit(password.charAt(index))) {
                hasNumber = true;
            } else if(Character.isUpperCase(password.charAt(index))) {
                hasUpperCase = true;
            } else if(Character.isLowerCase(password.charAt(index))) {
                hasLowerCase = true;
            } else if(isSpecialChar(password.charAt(index))) {
                hasSpecialCharacter = true;
            }
        }

        if(!hasUpperCase) {
            Toast.makeText(getApplicationContext(), "Password missing a upper case character", Toast.LENGTH_SHORT).show();
            return false;
        } else if(!hasLowerCase) {
            Toast.makeText(getApplicationContext(), "Password missing a lower case character", Toast.LENGTH_SHORT).show();
            return false;
        } else if(!hasSpecialCharacter) {
            Toast.makeText(getApplicationContext(), "Password missing a special character", Toast.LENGTH_SHORT).show();
            return false;
        } else if(!hasNumber) {
            Toast.makeText(getApplicationContext(), "Password missing a number", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks if the character passed into variable c is considered a special character
     * @param c The character to test
     * @return Returns true if character is in #$%^&*!@?
     */
    private boolean isSpecialChar(Character c) {
        switch(c) {
            case '#':
            case '$':
            case '%':
            case '^':
            case '&':
            case '*':
            case '!':
            case '@':
            case '?':
                return true;
            default:
                return false;
        }
    }
}