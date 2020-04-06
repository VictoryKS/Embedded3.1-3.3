package com.example.lab31;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText nIn;
    TextView resText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button funcBtn = findViewById(R.id.count_btn);
        funcBtn.setOnClickListener(this);

        resText = findViewById(R.id.result);
        nIn = findViewById(R.id.N_in);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.count_btn:
                if (nIn.getText().toString().length() > 0) {
                    BigInteger n = new BigInteger(nIn.getText().toString());
                    Ferma ferma = new Ferma();
                    BigInteger[] result = ferma.factorization(n);
                    if (result[0] == null) {
                        Toast.makeText(this, "Runtime Error", Toast.LENGTH_LONG).show();
                    }
                    String res = result[0] + ", " + result[1];
                    resText.setText(res);
                }
                break;
        }
    }

    private static class Ferma {
        public BigInteger[] factorization(BigInteger n) {
            double start = System.currentTimeMillis(), current;
            boolean isEnd = false;
            long x = (long) Math.sqrt(n.doubleValue());
            BigInteger[] result = new BigInteger[2];
            while (!isEnd) {
                double z = x * x - n.longValue();
                long y = (long) Math.sqrt(z);
                isEnd = (y == Math.sqrt(z));
                if (isEnd) {
                    result[0] = BigInteger.valueOf(Math.abs(x - y));
                    result[1] = BigInteger.valueOf(x + y);
                }
                x++;
                if (x > n.longValue()) {
                    result[0] = BigInteger.valueOf(1);
                    result[1] = n;
                    isEnd = true;
                }
                current = System.currentTimeMillis() - start;
                if (current > 3000) {
                    isEnd = true;
                    return result;
                }
            }
            return result;
        }
    }
}
