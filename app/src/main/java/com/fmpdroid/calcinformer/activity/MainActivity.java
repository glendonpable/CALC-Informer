package com.fmpdroid.calcinformer.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fmpdroid.calcinformer.R;
import com.fmpdroid.calcinformer.model.Subscriber;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText message;
    Button send;
    Subscriber subscriber;
    private List<Subscriber> subscriberList;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        message = (EditText) findViewById(R.id.message);
        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(this);
        subscriberList = new ArrayList<>();
        getSubscribers();
    }

    private void getSubscribers() {
        JsonArrayRequest request = new JsonArrayRequest("http://psite7.org/portal/webservices/get_subscribers.php",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {

                        String access_token;
                        String subscriber_number;

                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                JSONObject object = jsonArray.getJSONObject(i);
                                access_token = object.getString("access_token");
                                subscriber_number = object.getString("subscriber_number");
//                                Log.wtf("result", access_token);
                                subscriber = new Subscriber(access_token, subscriber_number);
                                subscriberList.add(subscriber);
                            } catch (Exception e) {

                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
            }
        });
        Volley.newRequestQueue(this).add(request);
    }

    private void sendMessage(final String message) {
        String access_token;
        final String subscriber_number;
        access_token = subscriberList.get(position).getAccess_token();
        subscriber_number = subscriberList.get(position).getSubscriber_number();
        Log.wtf("token", access_token);
        Log.wtf("subscriber", String.valueOf(subscriber_number));
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.POST, "https://devapi.globelabs.com.ph/" +
                "smsmessaging/v1/outbound/7962/requests?access_token=" + access_token,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        String error;
                        try {
                            JSONObject object = new JSONObject(s.toString());
//                            error = object.getString("error");

                            int j = position;
//                            if(j < subscriberList.size()){
//                                position = position + 1;
//                                sendMessage(message);
//                            }else{
//                                position = 0;
//                            }
                            while(j < subscriberList.size()){
                                position = position + 1;
                                sendMessage(message);
                            }
                            position = 0;

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("address", String.valueOf(subscriber_number));
                params.put("message", message);
                params.put("clientCorrelator", String.valueOf(123));
                return params;
            }
        };
        queue.add(sr);
    }

    @Override
    public void onClick(View v) {
        sendMessage(message.getText().toString());
    }

}
