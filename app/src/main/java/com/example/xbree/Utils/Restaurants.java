package com.example.xbree.Utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.xbree.HomeAdapter.RestaurantsAdapter;
import com.example.xbree.HomeAdapter.RestaurantsHelperClass;
import com.example.xbree.R;

import java.util.ArrayList;

public class Restaurants extends AppCompatActivity {
    RecyclerView RestaurantsRecycler;
    RecyclerView.Adapter adapter;
    View.OnClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        RestaurantsRecycler = findViewById(R.id.restau_ryc);
        RestaurantsRecycler();
    }

    private void RestaurantsRecycler() {
        RestaurantsRecycler.setHasFixedSize(true);
        RestaurantsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        ArrayList<RestaurantsHelperClass> RestaurantsLocations = new ArrayList<>();
        RestaurantsLocations.add(new RestaurantsHelperClass(R.drawable.stade1, "Stade el menzah", "Manzah 1", "Infoline : 55206020 "));
        RestaurantsLocations.add(new RestaurantsHelperClass(R.drawable.stade2, "Stade el manar", "Manar 2", "Infoline : 55206020 "));
        RestaurantsLocations.add(new RestaurantsHelperClass(R.drawable.stade3, "Stade ennasr", "Ennasr 1", "Infoline : 55206020 "));
        RestaurantsLocations.add(new RestaurantsHelperClass(R.drawable.stade1, "Stade CV", "Centre Ville", "Infoline : 55206020 "));

        adapter = new RestaurantsAdapter(RestaurantsLocations);
        RestaurantsRecycler.setAdapter(adapter);

    }
}