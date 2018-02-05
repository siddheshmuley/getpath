package com.example.android.getpath;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {
    ListView lv;
    String url="https://translation.googleapis.com/language/translate/v2?key=AIzaSyDcv4o1yc9WNI6L_U1L8nHxwEna1XQ5zvQ";
    String query="",encodedQuery="",foreignLanguage="",wrong="";

    RequestQueue q;
    FloatingActionButton fab,fab2;
    ArrayList<ChatMessage>messageList;
    JsonObjectRequest request;
    Intent intent;
    JSONObject resp;
    SpeechRecognizer recognizer,recognizer2;
    Intent recognitionIntent,recognitionIntent2;
    boolean isListening=false,isListening2=false;
    ProgressBar loop, loop2, line;
    int count=0;
    TextView foreign,myLanguage;
    Switch langSwitch;
    MessageAdapter movieAdapter;
    Button wrongTranslation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        lv=findViewById(R.id.chat_view);
        q= Volley.newRequestQueue(this);
        fab=findViewById(R.id.floatingActionButton);
        fab2=findViewById(R.id.floatingActionButton2);
        intent = getIntent();
        foreignLanguage=intent.getStringExtra("foreignLanguage");
        wrong=intent.getStringExtra("wrong");
        foreign=findViewById(R.id.foreign_language);
        myLanguage=findViewById(R.id.device_language);
        foreign.setText(foreignLanguage);
        myLanguage.setText(Locale.getDefault().getDisplayLanguage());
        messageList=new ArrayList<ChatMessage>();
        movieAdapter=new MessageAdapter(this,R.layout.my_message,messageList);
        lv.setAdapter(movieAdapter);
        wrongTranslation=findViewById(R.id.wrong);
        wrongTranslation.setText(wrong);
        wrongTranslation.setVisibility(View.INVISIBLE);
        loop=findViewById(R.id.loop);
        loop2=findViewById(R.id.loop2);
        line=findViewById(R.id.flatLine);

        //recognizer start
        recognizer=SpeechRecognizer.createSpeechRecognizer(this);
        recognitionIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognitionIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognitionIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        recognitionIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        recognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {
                isListening=true;
                flipFAB(1);
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {
                isListening=false;
                flipFAB(1);
            }

            @Override
            public void onError(int i) {
                isListening=false;
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();

                flipFAB(1);
            }

            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                query=matches.get(0);

                makeRequest(intent.getStringExtra("targetLanguage").split(" ")[0]);
            }

            @Override
            public void onPartialResults(Bundle bundle) {
                ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });


        recognizer2=SpeechRecognizer.createSpeechRecognizer(this);
        recognitionIntent2 = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognitionIntent2.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        String s=intent.getStringExtra("targetLanguage").split(" ")[1];
        recognitionIntent2.putExtra(RecognizerIntent.EXTRA_LANGUAGE, intent.getStringExtra("targetLanguage").split(" ")[1]);
        recognitionIntent2.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        recognitionIntent2.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        recognizer2.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {
                isListening2=true;
                flipFAB(2);
            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {
                isListening2=false;
                flipFAB(2);
            }

            @Override
            public void onError(int i) {
                isListening2=false;
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();

                flipFAB(2);
            }

            @Override
            public void onResults(Bundle bundle) {
                ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                query=matches.get(0);
                makeRequest(Locale.getDefault().getLanguage());
            }

            @Override
            public void onPartialResults(Bundle bundle) {
                ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });
        //recognizer end

        wrongTranslation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(messageList.size()>0 && messageList.get(messageList.size()-1).getId()==1){
                    messageList.remove(messageList.size()-1);
                    movieAdapter.notifyDataSetChanged();
                    wrongTranslation.setClickable(false);
                    wrongTranslation.setVisibility(View.INVISIBLE);
                }
            }
        });

        Toast.makeText(getApplicationContext(),intent.getStringExtra("targetLanguage"),Toast.LENGTH_SHORT).show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isListening=true;
                flipFAB(1);
                recognizer.startListening(recognitionIntent);
                String s="";
                Toast.makeText(getApplicationContext(),"requesting",Toast.LENGTH_SHORT).show();
            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isListening2=true;
                flipFAB(2);
                recognizer2.startListening(recognitionIntent2);
                String s="";
                Toast.makeText(getApplicationContext(),"requesting",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void makeRequest(String target){
        line.setVisibility(View.VISIBLE);
        try{
            encodedQuery=(!target.equals(Locale.getDefault().getLanguage()))?URLEncoder.encode(query,"UTF-8"):query;
        }
        catch(Exception e){
            line.setVisibility(View.INVISIBLE);
        }
        Log.println(Log.VERBOSE,"success","");
        final String zzz=target;
        request=new JsonObjectRequest(Request.Method.GET, url+"&q="+encodedQuery+"&target="+target, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            if(zzz.equals(Locale.getDefault().getLanguage())){
                                wrongTranslation.setClickable(true);
                                wrongTranslation.setVisibility(View.VISIBLE);
                                updateListView(1,query,response.getJSONObject("data").getJSONArray("translations").getJSONObject(0).getString("translatedText"));
                            }
                            else{
                                wrongTranslation.setClickable(false);
                                wrongTranslation.setVisibility(View.INVISIBLE);
                                updateListView(2,query,response.getJSONObject("data").getJSONArray("translations").getJSONObject(0).getString("translatedText"));
                            }
                        }catch (Exception e){

                        }
                        movieAdapter.notifyDataSetChanged();
                        line.setVisibility(View.INVISIBLE);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        line.setVisibility(View.INVISIBLE);
                    }
        });
        q.add(request);
    }

    public void flipFAB(int x){
        if(x==1){
            fab2.setVisibility(View.INVISIBLE);
            loop2.setVisibility(View.INVISIBLE);
            fab.setVisibility(View.VISIBLE);
            fab2.setClickable(false);
            if(isListening){
                fab.setClickable(false);
                fab.setImageResource(0);
                loop.setVisibility(View.VISIBLE);
                loop.bringToFront();
            }
            else{
                fab.setClickable(true);
                fab.setImageResource(R.drawable.ic_mic_white_24dp);
                loop.setVisibility(View.INVISIBLE);
                fab.bringToFront();
                fab.setClickable(true);
            }
            fab2.setVisibility(View.VISIBLE);
            fab2.setClickable(true);
        }
        else{
            fab.setVisibility(View.INVISIBLE);
            loop.setVisibility(View.INVISIBLE);
            fab2.setVisibility(View.VISIBLE);
            fab.setClickable(false);
            if(isListening2){
                fab2.setClickable(false);
                fab2.setImageResource(0);
                loop2.setVisibility(View.VISIBLE);
                loop2.bringToFront();
            }
            else{
                fab2.setClickable(true);
                fab2.setImageResource(R.drawable.ic_mic_white_24dp);
                loop2.setVisibility(View.INVISIBLE);
                fab2.bringToFront();
                fab2.setClickable(true);
            }
            fab.setVisibility(View.VISIBLE);
            fab.setClickable(true);
        }
    }

    public void updateListView(int pos,String str,String str2){
        messageList.add(new ChatMessage(pos,str,str2));
        movieAdapter.notifyDataSetChanged();
    }
}
