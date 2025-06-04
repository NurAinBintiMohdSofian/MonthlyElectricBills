package com.example.ain_individual;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CreateBillActivity extends AppCompatActivity {
    EditText edtUnit, edtRebate;
    Spinner spinnerMonth;
    Button btnSave, btnBack;
    DataHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_bill);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.aboutLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        spinnerMonth = findViewById(R.id.spinnerMonth);
        edtUnit = findViewById(R.id.edtUnit);
        edtRebate = findViewById(R.id.edtRebate);
        btnSave = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);
        dbHelper = new DataHelper(this);
        btnSave.setOnClickListener(v -> {
            String month = spinnerMonth.getSelectedItem().toString();
            double unit = Double.parseDouble(edtUnit.getText().toString());
            double rebate = Double.parseDouble(edtRebate.getText().toString());
            double total = calcCharge(unit);
            double finalCost = total - (total * (rebate / 100));
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL("INSERT INTO bill(month, unit, rebate, total, final) VALUES('" +
                    month + "', " + unit + ", " + rebate + ", " + total + ", " + finalCost + ")");
            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
            finish();
        });
        btnBack.setOnClickListener(v -> finish());
    }
    private double calcCharge(double unit) {
        double total = 0;
        if (unit <= 200) total = unit * 0.218;
        else if (unit <= 300) total = 200 * 0.218 + (unit - 200) * 0.334;
        else if (unit <= 600) total = 200 * 0.218 + 100 * 0.334 + (unit - 300) * 0.516;
        else total = 200 * 0.218 + 100 * 0.334 + 300 * 0.516 + (unit - 600) * 0.546;
        return total;
    }
}