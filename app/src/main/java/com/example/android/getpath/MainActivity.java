package com.example.android.getpath;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    String foreignLanguage="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Spinner language = findViewById(R.id.language_select);
        final Button startButton= findViewById(R.id.button);
        final TextView tv=findViewById(R.id.textView7);;
        language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Context context;
                Resources resources;

                switch(i){
                    case 0:
                        tv.setText("");
                        startButton.setEnabled(false);
                        startButton.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        tv.setText("zh-CN zh_CN");
                        startButton.setEnabled(true);
                        startButton.setVisibility(View.VISIBLE);
                        foreignLanguage="简体中文";
                        break;
                    case 2:
                        tv.setText("en en_US");
                        startButton.setEnabled(true);
                        startButton.setVisibility(View.VISIBLE);
                        foreignLanguage="English";
                        break;
                    case 3:
                        tv.setText("hi hi_IN");
                        startButton.setEnabled(true);
                        startButton.setVisibility(View.VISIBLE);
                        foreignLanguage="हिंदी";
                        break;
                    case 4:
                        tv.setText("es es_ES");
                        startButton.setEnabled(true);
                        startButton.setVisibility(View.VISIBLE);
                        foreignLanguage="Español";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                startButton.setEnabled(false);
                startButton.setVisibility(View.INVISIBLE);
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),ChatActivity.class);
                intent.putExtra("targetLanguage",tv.getText());
                intent.putExtra("foreignLanguage",foreignLanguage);
                intent.putExtra("wrong",getString(R.string.wrong_button));
                startActivity(intent);
            }
        });
    }
}
