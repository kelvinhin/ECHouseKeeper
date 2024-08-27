package com.kelvin.echousekeeper;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.echousekeeper.Object.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kelvin on 2017/2/5.
 */

public class tab3shoppinglist extends Fragment {
    View rootView;

    private ListView listView;
    private JSONArray jsonArray = null;
    private Post post;
    private ArrayList<Post> alPost = new ArrayList<Post>();


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tab3shoppinglist, container, false);
        getProduct();
        while (jsonArray==null){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            //Testing Log
            System.out.println("allProductList resultObject: "+jsonArray.getJSONObject(0));
            if (jsonArray!=null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    Post post = new Post(jsonArray.getJSONObject(i));
                        alPost.add(post);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("alPost " +alPost);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.shoppinglist_recyclerView);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // create an Object for Adapter
        mAdapter = new tab3adapter(Contact.generateSampleList());

        // set the adapter object to the Recyclerview
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }
    public void getProduct(){
        Thread thread = new Thread() {
            public void run() {
                StringBuilder sb = new StringBuilder();
                HttpURLConnection connection = null;

                try {
                    URL url = new URL("https://triple-c.mybluemix.net/api/read");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    connection.setConnectTimeout(20000);
                    connection.setReadTimeout(20000);


                    //Testing Log
                    System.out.println("URL:" + url.toString());


                    int HttpResult = connection.getResponseCode();
                    System.out.println("resopnseCode: " + HttpResult);

                    if (HttpResult == 200) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(
                                connection.getInputStream()));
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        br.close();
                        System.out.println(sb.toString());
                        System.out.println("sb Length: "+ sb.length());

                        String sbStr = "{products:"+sb+"}";

                        JSONObject productsJson = new JSONObject(sbStr);
                        jsonArray = productsJson.getJSONArray("products");
                    }
                    connection.disconnect();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}
