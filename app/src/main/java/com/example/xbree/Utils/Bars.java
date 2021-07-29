package com.example.xbree.Utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.xbree.HomeAdapter.BarsAdapter;
import com.example.xbree.HomeAdapter.BarsHelperClass;
import com.example.xbree.HomeAdapter.RestaurantsAdapter;
import com.example.xbree.HomeAdapter.RestaurantsHelperClass;
import com.example.xbree.R;

import java.util.ArrayList;

public class Bars extends AppCompatActivity {
    RecyclerView BarsRecycler;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bars);
        BarsRecycler = findViewById(R.id.bars_ryc);
        BarsRecycler();
    }

    private void BarsRecycler() {
        BarsRecycler.setHasFixedSize(true);
        BarsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        ArrayList<BarsHelperClass> RestaurantsLocations = new ArrayList<>();
        RestaurantsLocations.add(new BarsHelperClass(R.drawable.tennis1, "Terrain El Manar", "Manar 2", "Infoline : 55206020"));
        RestaurantsLocations.add(new BarsHelperClass(R.drawable.tennis2, "Terrain Ariana", "Ariana", "Infoline : 55206020"));
        RestaurantsLocations.add(new BarsHelperClass(R.drawable.tennis3, "Terrain Menzah", "Menzah 1", "Infoline : 55206020"));

        adapter = new BarsAdapter(RestaurantsLocations);
        BarsRecycler.setAdapter(adapter);
    }
}