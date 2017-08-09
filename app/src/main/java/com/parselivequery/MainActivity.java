package com.parselivequery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.parse.Parse;
import com.parse.ParseLiveQueryClient;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SubscriptionHandling;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    private final String LIVE_QUERY_HOSTING = "Your id from web hosting parse";
    private final String SERVER_URL = "wss://"+LIVE_QUERY_HOSTING+".back4app.io";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Back4App's Parse setup
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("Your App Id")
                .clientKey("Client_Key")
                .server("https://parseapi.back4app.com/").build()
        );
    }

    public void buttonClick(View view) {
        // Parse.initialize should be called first
        try {
            URI uri = new URI(SERVER_URL);
            ParseLiveQueryClient parseLiveQueryClient = ParseLiveQueryClient.Factory.getClient(uri);
            ParseQuery parseQuery = ParseQuery.getQuery("Project"); // your clas name, in my case I have a table name Project on server

            SubscriptionHandling<ParseObject> subscriptionHandling = parseLiveQueryClient.subscribe(parseQuery);
            subscriptionHandling.handleEvents(new SubscriptionHandling.HandleEventsCallback<ParseObject>() {
                @Override
                public void onEvents(ParseQuery<ParseObject> query, SubscriptionHandling.Event event, ParseObject object) {
                    // HANDLING all events
                    Log.d("Prints Object changed",object.toString());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
