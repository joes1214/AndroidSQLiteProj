package com.example.coursereg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.coursereg.DatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

public class AddStudentActivity extends AppCompatActivity {
    EditText[] inputText = new EditText[3];
    Spinner yearInput;
    private User user;
    private DatabaseHelper databaseHelper;

    int duration = Toast.LENGTH_SHORT;

    Toast toast;
    Context context = getApplicationContext();
    CharSequence toastTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        //dont touch

        yearInput = findViewById(R.id.yearInput);

        inputText[0] = findViewById(R.id.fNameInput);
        inputText[1] = findViewById(R.id.lNameInput);
        inputText[2] = findViewById(R.id.emailInput);

        Button submitBtn = findViewById(R.id.submitBtn);

        ArrayAdapter<CharSequence>adapter=ArrayAdapter.createFromResource(this, R.array.StudentYear, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        yearInput.setAdapter(adapter);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int x = 0; x < 3; x++){
                    if(inputText[x].getText().toString().trim().length()==0){
                        inputText[x].setError("Invalid Entry");
                        inputText[x].requestFocus();
                    }//end of if
                }//end of for loop

                if(validInfo()){
                    if(!databaseHelper.checkStudent(inputText[2].getText().toString().trim())){
                        sendToDatabase();
                    } else {
                        toastTest = "Email already in use!";
                        toast = Toast.makeText(context,toastTest, duration);
                    }
                }

            }//end of onClick
        });//end of function
    }

    //A lot of this I just got from a previous project
    private Boolean validInfo(){
        int y = 0;
        for (int x = 0; x < 3; x++){
            if(validateForms(x, inputText[x].getText().toString())) {
                y++;
            }
            if (y == 4) {
                return true;
            }
        }
        return false;
    }

    private void sendToDatabase(){
        user.setfName(inputText[0].getText().toString().trim());
        user.setlName(inputText[1].getText().toString().trim());
        user.setEmail(inputText[2].getText().toString().trim());
        user.setYear(yearInput.getSelectedItem().toString());

        toastTest = "Data successfully added";
        toast = Toast.makeText(context,toastTest, duration);
    }

    private Boolean validateForms(int Type, String Text){

        switch (Type){
            case 0:
            case 1:
                if (Text.length() < 3){
                    inputText[Type].setError("Name too short!");
                    return false;
                }

                if(Text.length() > 30){
                    inputText[Type].setError("Name too long!");
                    return false;
                }
                return true;

            case 2:
                String emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"; //got the regex code from here https://youtu.be/o9Y7HDkopHg?t=392
                if (!Text.matches(emailRegex)){
                    inputText[Type].setError("Enter valid Email!");
                    return false;
                }
                return true;

            default:
                return false;
        }
    }
}