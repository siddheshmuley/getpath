package com.example.android.getpath;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {
    String foreignLanguage="",target="",targetLanguage="";
    Spinner language;
    Button startButton,survey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        language = findViewById(R.id.language_select);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(language);

            // Set popupWindow height to 500px
            popupWindow.setHeight(500);
        }
        catch (Exception e) {
            // silently fail...
        }
        startButton= findViewById(R.id.button);
        survey = findViewById(R.id.survey_button);

        survey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url="https://ufl.qualtrics.com/jfe/form/SV_79FMvp0ULaLU2wJ";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });

        language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Context context;
                Resources resources;

                switch(i){
                    case 0:
                        startButton.setEnabled(false);
                        startButton.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        targetLanguage="zh-CN zh_CN";
                        startButton.setEnabled(true);
                        startButton.setVisibility(View.VISIBLE);
                        foreignLanguage="简体中文";
                        target="zh-Hans";
                        break;
                    case 2:
                        targetLanguage="en en_US";
                        startButton.setEnabled(true);
                        startButton.setVisibility(View.VISIBLE);
                        foreignLanguage="English";
                        target="en";
                        break;
                    case 3:
                        targetLanguage="fr fr_FR";
                        startButton.setEnabled(true);
                        startButton.setVisibility(View.VISIBLE);
                        foreignLanguage="Français";
                        target="fr";
                        break;
                    case 4:
                        targetLanguage="de de_DE";
                        startButton.setEnabled(true);
                        startButton.setVisibility(View.VISIBLE);
                        foreignLanguage="Deutsche";
                        target="de";
                        break;
                    case 5:
                        targetLanguage="hi hi_IN";
                        startButton.setEnabled(true);
                        startButton.setVisibility(View.VISIBLE);
                        foreignLanguage="हिंदी";
                        target="hi";
                        break;
                    case 6:
                        targetLanguage="it it_IT";
                        startButton.setEnabled(true);
                        startButton.setVisibility(View.VISIBLE);
                        foreignLanguage="Italiano";
                        target="it";
                        break;
                    case 7:
                        targetLanguage="fa fa_";
                        startButton.setEnabled(true);
                        startButton.setVisibility(View.VISIBLE);
                        foreignLanguage="فارسی";
                        target="fa";
                        break;
                    case 8:
                        targetLanguage="ru ru_RU";
                        startButton.setEnabled(true);
                        startButton.setVisibility(View.VISIBLE);
                        foreignLanguage="русский";
                        target="ru";
                        break;
                    case 9:
                        targetLanguage="es es_ES";
                        startButton.setEnabled(true);
                        startButton.setVisibility(View.VISIBLE);
                        foreignLanguage="Español";
                        target="es";
                        break;
                    case 10:
                        targetLanguage="ta ta_IN";
                        startButton.setEnabled(true);
                        startButton.setVisibility(View.VISIBLE);
                        foreignLanguage="தமிழ்";
                        target="ta";
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
                intent.putExtra("targetLanguage",targetLanguage);
                intent.putExtra("foreignLanguage",foreignLanguage);
                intent.putExtra("wrong",getString(R.string.wrong_button));
                intent.putExtra("target",target);
                startActivity(intent);
                survey.setVisibility(View.VISIBLE);
            }
        });
    }
}
