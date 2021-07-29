package com.example.xbree.Utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.xbree.HomeAdapter.BarsAdapter;
import com.example.xbree.HomeAdapter.BarsHelperClass;
import com.example.xbree.HomeAdapter.CafeAdapter;
import com.example.xbree.HomeAdapter.CafeHelperClass;
import com.example.xbree.R;

import java.util.ArrayList;

public class Cafee extends AppCompatActivity {
    RecyclerView CafeRecycler;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafee);
        CafeRecycler = findViewById(R.id.cafe_ryc);
        CafeRecycler();

    }

    private void CafeRecycler() {
        CafeRecycler.setHasFixedSize(true);
        CafeRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        ArrayList<CafeHelperClass> RestaurantsLocations = new ArrayList<>();
        RestaurantsLocations.add(new CafeHelperClass(R.drawable.vol1, "Salle Couverte el Menzah", "Menzah 1", "Infoline : 55206020"));
        RestaurantsLocations.add(new CafeHelperClass(R.drawable.vol2, "Salle Couverte Manar", "Manar 2", "Infoline : 55206020"));
        RestaurantsLocations.add(new CafeHelperClass(R.drawable.vol3, "Salle Couverte Centre ville", "Centre ville", "Infoline : 55206020"));

        adapter = new CafeAdapter(RestaurantsLocations);
        CafeRecycler.setAdapter(adapter);
    }
}