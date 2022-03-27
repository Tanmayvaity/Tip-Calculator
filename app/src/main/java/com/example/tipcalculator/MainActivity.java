package com.example.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private static final String  TAG = "MainActivity";
    private static final int  INITIAL_TIP_PERCENT = 15;
    private EditText etBaseAmount;
    private TextView tvPercentageLabel,tvTotalAmount,tvTipAmount,tvTipDescription;
    private SeekBar seekBarTip;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etBaseAmount = findViewById(R.id.etBaseAmount);
        tvPercentageLabel = findViewById(R.id.tvPercentageLabel);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        tvTipAmount = findViewById(R.id.tvTipAmount);
        seekBarTip = findViewById(R.id.seekBarTip);
        tvTipDescription = findViewById(R.id.tvTipDescription);

        updateTipDescription(INITIAL_TIP_PERCENT);
        seekBarTip.setProgress(INITIAL_TIP_PERCENT);
        tvPercentageLabel.setText(Integer.toString(INITIAL_TIP_PERCENT)+"%");
        seekBarTip.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                Log.i(TAG,"onProgressChanged "+progress);
                String value = progress + "%";
                tvPercentageLabel.setText(value);
                computeTipAndTotal();
                updateTipDescription(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        etBaseAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {

                computeTipAndTotal();
            }
        });
    }

    private void updateTipDescription(int progress) {

        String tipDescription = "";
        if(progress>=0 && progress<=9){
            tipDescription = "Poor";
        }else if(progress >9 && progress <=14){
            tipDescription = "Average";
        }else if(progress >14 && progress <=19){
            tipDescription = "Good";
        }else if(progress >19 && progress <=24){
            tipDescription = "Great";
        }else{
            tipDescription = "Great";
        }

        tvTipDescription.setText(tipDescription);

        ArgbEvaluator argbEvaluator = new ArgbEvaluator();
        int color = (int)(argbEvaluator.evaluate(
                (float)progress/seekBarTip.getMax(),
                ContextCompat.getColor(this,R.color.color_worst_tip),
                ContextCompat.getColor(this,R.color.color_best_tip)));
        tvTipDescription.setTextColor(color);
    }

    private void computeTipAndTotal() {

        if(etBaseAmount.getText().toString().isEmpty()){
            tvTipAmount.setText("");
            tvTotalAmount.setText("");
            return;
        }
        double baseAmount = Double.parseDouble(etBaseAmount.getText().toString());
        int tipPercent = seekBarTip.getProgress();
        double tipAmount = baseAmount*tipPercent/100;
        double totalAmount  = tipAmount+baseAmount;


        tvTipAmount.setText(String.format("%.2f",tipAmount));
        tvTotalAmount.setText(String.format("%.2f",totalAmount));
    }
}