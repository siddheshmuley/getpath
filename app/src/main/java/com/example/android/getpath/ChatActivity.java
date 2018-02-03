package com.example.android.getpath;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class ChatActivity extends AppCompatActivity {
    ListView lv;
    String url="https://translation.googleapis.com/language/translate/v2?key=AIzaSyDcv4o1yc9WNI6L_U1L8nHxwEna1XQ5zvQ";
    RequestQueue q;
    FloatingActionButton fab;
    ArrayList<String>al=new ArrayList<>();
    JsonObjectRequest request;
    ArrayAdapter<String> adapter;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        lv=findViewById(R.id.chat_view);
        q= Volley.newRequestQueue(this);
        String query="";
        fab=findViewById(R.id.floatingActionButton);
        intent = getIntent();
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                al);
        lv.setAdapter(adapter);
        Toast.makeText(getApplicationContext(),intent.getStringExtra("targetLanguage"),Toast.LENGTH_SHORT).show();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeRequest();
                Toast.makeText(getApplicationContext(),"requesting",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void makeRequest(){
        String query="";
        try{
            query=URLEncoder.encode("one + two + three","UTF-8");
        }
        catch(Exception e){

        }
        url+="&q="+query+"&target="+intent.getStringExtra("targetLanguage");
        Log.println(Log.VERBOSE,"success","");

        request=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        al.add("thunder");
                        adapter.notifyDataSetChanged();
                        Log.println(Log.VERBOSE,"success","");

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        al.add("lightening");
                        Log.println(Log.VERBOSE,"fail","");
                    }
        });
        q.add(request);
    }
}
