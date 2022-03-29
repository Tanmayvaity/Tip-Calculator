package com.example.tipcalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    private static final String  TAG = "MainActivity";
    private static final int  INITIAL_TIP_PERCENT = 15;


    private EditText etBaseAmount;
    private TextView tvPercentageLabel,tvTotalAmount,tvTipAmount,tvTipDescription,tvPerPerson,tvSplitLabel;
    private SeekBar seekBarTip,seekBarPersons;
    private Button btSave;




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
        tvPerPerson = findViewById(R.id.tvPerPerson);
        seekBarPersons = findViewById(R.id.seekBarPersons);
        tvSplitLabel = findViewById(R.id.tvSplitLabel);
        btSave = findViewById(R.id.btSave);

        updateTipDescription(INITIAL_TIP_PERCENT);
        seekBarTip.setProgress(INITIAL_TIP_PERCENT);

        tvPercentageLabel.setText(Integer.toString(INITIAL_TIP_PERCENT)+"%");
        computeTipAndTotal();
        seekBarTip.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                Log.i(TAG,"onProgressChanged "+progress);
                String value = progress + "%";
                tvPercentageLabel.setText(value);
                double total = computeTipAndTotal();
                splitAbstraction(seekBarPersons.getProgress(),total);
                updateTipDescription(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        seekBarPersons.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                tvSplitLabel.setText(progress);
                double total  = computeTipAndTotal();
                splitAbstraction(seekBar.getProgress(),total);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
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

                double total = computeTipAndTotal();
                splitAbstraction(seekBarPersons.getProgress(),total);
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,PreviousTips.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.save_as_pdf:
                Toast.makeText(this,"save as pdf",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.settings:
                intent = new Intent(this,Settings.class);
                startActivity(intent);
                return true;
            case R.id.tips:
                intent = new Intent(this,PreviousTips.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void updateTipDescription(int progress) {

        String tipDescription = "";
        String unicode = "";
        if(progress>=0 && progress<=9){
            tipDescription = "Poor";
//            unicode = "\uD83D\uDE21";
        }else if(progress >9 && progress <=14){
            tipDescription = "Average";
//            unicode =    "\uD83D\uDE0C";
        }else if(progress >14 && progress <=19){
            tipDescription = "Good";
//            unicode ="\uD83D\uDE00";
        }else if(progress >19 && progress <=24){
            tipDescription = "Great";
//            unicode ="\uD83D\uDE0B";
        }else{
            tipDescription = "Amazing";
//            unicode ="\uD83D\uDE0D";
        }


        if(!unicode.isEmpty()){
            tvTipDescription.setText(tipDescription.toString().replace(tipDescription, unicode));
        }else{
            tvTipDescription.setText(tipDescription);
        }

        ArgbEvaluator argbEvaluator = new ArgbEvaluator();
        int color = (int)(argbEvaluator.evaluate(
                (float)progress/seekBarTip.getMax(),
                ContextCompat.getColor(this,R.color.color_worst_tip),
                ContextCompat.getColor(this,R.color.color_best_tip)));
        tvTipDescription.setTextColor(color);
    }

    private double computeTipAndTotal() {

        if(etBaseAmount.getText().toString().isEmpty()){
            tvTipAmount.setText(String.format("%.2f",0.00));
            tvTotalAmount.setText(String.format("%.2f",0.00));
            return 0;
        }
        double baseAmount = Double.parseDouble(etBaseAmount.getText().toString());
        int tipPercent = seekBarTip.getProgress();
        double tipAmount = baseAmount*tipPercent/100;
        double totalAmount  = tipAmount+baseAmount;

        tvTipAmount.setText(String.format("%.2f",tipAmount));
        tvTotalAmount.setText(String.format("%.2f",totalAmount));

        return totalAmount;
    }

    private void splitAbstraction(int splitNo,double totalAmount){
        double tipPerPerson;

        tvSplitLabel.setText(String.format("Split By %d", splitNo));

        if(etBaseAmount.getText().toString().isEmpty()){
            tvPerPerson.setText(String.format("%.2f",0.00));
        }

        tipPerPerson = totalAmount/splitNo;

        tvPerPerson.setText(String.format("%.2f",tipPerPerson));


    }
}
