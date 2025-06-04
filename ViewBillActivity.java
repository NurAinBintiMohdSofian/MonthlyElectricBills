package com.example.ain_individual;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ViewBillActivity extends AppCompatActivity {
    TextView tv1, tv2, tv3, tv4, tv5;
    DataHelper dbHelper;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_bill);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.aboutLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tv1 = findViewById(R.id.tvMonth);
        tv2 = findViewById(R.id.tvUnit);
        tv3 = findViewById(R.id.tvRebate);
        tv4 = findViewById(R.id.tvTotal);
        tv5 = findViewById(R.id.tvFinal);
        dbHelper = new DataHelper(this);
        String month = getIntent().getStringExtra("month");
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM bill WHERE month = '" + month + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            tv1.setText(cursor.getString(1));
            tv2.setText(cursor.getDouble(2) + " kWh");
            tv3.setText(cursor.getDouble(3) + "%");
            tv4.setText("RM" + String.format("%.2f", cursor.getDouble(4)));
            tv5.setText("RM" + String.format("%.2f", cursor.getDouble(5)));
        }
    }
}