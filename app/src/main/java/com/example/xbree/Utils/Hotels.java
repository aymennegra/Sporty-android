package com.example.xbree.Utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.xbree.HomeAdapter.BarsAdapter;
import com.example.xbree.HomeAdapter.BarsHelperClass;
import com.example.xbree.HomeAdapter.HotelsAdapter;
import com.example.xbree.HomeAdapter.HotelsHelperClass;
import com.example.xbree.R;

import java.util.ArrayList;

public class Hotels extends AppCompatActivity {
    RecyclerView HotelsRecycler;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels);
        HotelsRecycler = findViewById(R.id.hotels_ryc);
        HotelsRecycler();
    }

    private void HotelsRecycler() {
        HotelsRecycler.setHasFixedSize(true);
        HotelsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        ArrayList<HotelsHelperClass> RestaurantsLocations = new ArrayList<>();
        RestaurantsLocations.add(new HotelsHelperClass(R.drawable.basket1, "Salle couverte Rades", "Rades", "infoline : 55206020 "));
        RestaurantsLocations.add(new HotelsHelperClass(R.drawable.basket2, "Salle couverte menzah", "menzah 6", "infoline : 55206020 "));
        RestaurantsLocations.add(new HotelsHelperClass(R.drawable.basket3, "Salle couverte ariana", "Ariana", "infoline : 55206020 "));
        RestaurantsLocations.add(new HotelsHelperClass(R.drawable.basket1, "Salle couverte CV", "Centre Ville", "infoline : 55206020 "));

        adapter = new HotelsAdapter(RestaurantsLocations);
        HotelsRecycler.setAdapter(adapter);
    }
}