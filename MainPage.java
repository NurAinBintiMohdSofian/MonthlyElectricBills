package com.example.ain_individual;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainPage extends AppCompatActivity {
    Button btnAdd;
    ListView listView;
    DataHelper dbHelper;
    ArrayList<String> billList;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.aboutLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dbHelper = new DataHelper(this);
        listView = findViewById(R.id.listView1);
        btnAdd = findViewById(R.id.btnAdd);
        loadBillData();
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selected = billList.get(position);
            String month = selected.split(" - ")[0];
            Intent intent = new Intent(getApplicationContext(), ViewBillActivity.class);
            intent.putExtra("month", month);
            startActivity(intent);
        });
    }
    private void loadBillData() {
        billList = new ArrayList<>();
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("SELECT * FROM bill", null);
        if (cursor.moveToFirst()) {
            do {
                String month = cursor.getString(1);
                double finalCost = cursor.getDouble(5);
                billList.add(month + " - RM " + String.format("%.2f", finalCost));
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, billList);
        listView.setAdapter(adapter);
    }
    public void addBtn(View view) {
        Intent intent = new Intent(getApplicationContext(), CreateBillActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadBillData();
    }
}