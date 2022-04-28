package com.example.coursereg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button[] interactionButton = {findViewById(R.id.addStudentBtn),
                findViewById(R.id.removeStudentBtn),
                findViewById(R.id.lookupStudentBtn)};

        Intent AddStudent = new Intent(this, AddStudentActivity.class);
        Intent RemoveStudent = new Intent(this, RemoveStudentActivity.class);
        Intent LookupStudent = new Intent(this, LookupStudentActivity.class);

        interactionButton[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AddStudent);
            }
        });

        interactionButton[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(RemoveStudent);
            }
        });

        interactionButton[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(LookupStudent);
            }
        });

    }
}