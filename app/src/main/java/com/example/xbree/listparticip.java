package com.example.xbree;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.xbree.HomeAdapter.EvenementAdapter;
import com.example.xbree.HomeAdapter.EvenementUserAdapter;
import com.example.xbree.Entities.Evenement;
import com.example.xbree.Retrofit.INodeJS;
import com.example.xbree.Retrofit.RetrofitClient;
import com.example.xbree.Utils.database.AppDataBase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class listparticip extends AppCompatActivity {

    ImageView acceuil1, profileuser, favori;
    List<Evenement> evenementsList;

    RecyclerView recyclerView;
    public static INodeJS iNodeJS;
    Context mContext;
    SharedPreferences sharedPreferencesU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part);
        recyclerView = findViewById(R.id.eventss);
        favori = findViewById(R.id.favori);
        iNodeJS = RetrofitClient.getInstance().create(INodeJS.class);
        GetListEvenement();
    }

    public void GetListEvenement() {
        sharedPreferencesU = getApplicationContext().getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
        int idu = sharedPreferencesU.getInt("idUser", 0);
        System.out.println(idu);
        Call<List<Evenement>> call = iNodeJS.getparticipList(idu);
        call.enqueue(new Callback<List<Evenement>>() {
            @Override
            public void onResponse(Call<List<Evenement>> call, Response<List<Evenement>> response) {
                evenementsList = response.body();
                Log.d("test2", String.valueOf(response.body()));
                EvenementUserAdapter adapter = new EvenementUserAdapter(mContext, evenementsList);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

            }

            @Override
            public void onFailure(Call<List<Evenement>> call, Throwable t) {

            }
        });
    }

}
