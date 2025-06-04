package com.example.ain_individual;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button enter, enter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.aboutLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        enter = (Button) findViewById(R.id.button);
        enter1 = (Button) findViewById(R.id.button2);
    }
    public void enterMain(View view){
        Intent intent = new Intent(getApplicationContext(), MainPage.class);
        startActivity(intent);
    }
    public void enterAbout(View view){
        Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
        startActivity(intent);
    }
}