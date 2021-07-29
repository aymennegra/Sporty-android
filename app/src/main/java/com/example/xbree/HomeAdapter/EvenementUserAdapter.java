package com.example.xbree.HomeAdapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xbree.Entities.Evenement;
import com.example.xbree.Entities.Participants;
import com.example.xbree.R;
import com.example.xbree.Retrofit.INodeJS;
import com.example.xbree.Retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//import com.example.coretal.home.ModifierEvenement;

public class EvenementUserAdapter extends RecyclerView.Adapter<com.example.xbree.HomeAdapter.EvenementUserAdapter.myViewHolder> {

    Context mContext;
    private List<Evenement> mData;
    FragmentManager fragmentManager;
    SharedPreferences sharedPreferences, sharedPreferencesUE,sharedPreferencesV;
    TextView titre, type, location, prix, date;
    ImageView bk;
    INodeJS myAPI;
    public static INodeJS iNodeJS;
    Button annuler;


    public EvenementUserAdapter(Context mContext, List<Evenement> mDataa) {
        this.mContext = mContext;
        this.mData = mDataa;
    }
    public void notifyChange(List<Evenement> users){
        this.mData = users;
        this.notifyDataSetChanged();
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.card_eventuser, parent, false);
        myViewHolder vv = new myViewHolder(v);
        return vv;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        final Evenement evenement = mData.get(position);
        titre.setText(evenement.getNom());
        type.setText(evenement.getType());
        location.setText(evenement.getLieu());
        prix.setText(evenement.getPrix() + "DT");
        date.setText(evenement.getDateDebut());

        //titre.setText(evenement.getNom_evenement());


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class myViewHolder extends RecyclerView.ViewHolder {
        public TextView pr_title;
        public ImageView background_img;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            background_img = itemView.findViewById(R.id.background_img);
            titre = itemView.findViewById(R.id.title_p);
            type = itemView.findViewById(R.id.type_p);
            location = itemView.findViewById(R.id.location_p);
            prix = itemView.findViewById(R.id.price_p);
            date = itemView.findViewById(R.id.date_p);
            annuler=itemView.findViewById(R.id.annuler);
            iNodeJS = RetrofitClient.getInstance().create(INodeJS.class);

            sharedPreferences = itemView.getContext().getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
            int idUser = sharedPreferences.getInt("idUser", 0);
            sharedPreferencesUE = itemView.getContext().getSharedPreferences("UserEvent", Context.MODE_PRIVATE);
            int idUE = sharedPreferencesUE.getInt("idUser", 0);
            sharedPreferencesV = itemView.getContext().getSharedPreferences("Evenement", Context.MODE_PRIVATE);
            final int id_ev = sharedPreferencesV.getInt("id_evenement", 0);



            annuler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Retrofit.Builder builder = new Retrofit.Builder()
                            .baseUrl("http://172.16.198.175:3000")
                            .addConverterFactory(GsonConverterFactory.create());
                    Retrofit retrofit = builder.build();
                    myAPI = retrofit.create(INodeJS.class);
                    Call<Participants>call=myAPI.deleteparticipant(id_ev);
                    call.enqueue(new Callback<Participants>() {
                        @Override
                        public void onResponse(Call<Participants> call, Response<Participants> response) {
                            Toast.makeText(itemView.getContext(), "U re not subscribed anymore", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Participants> call, Throwable t) {

                        }
                    });

                }
            });

            if (idUser == idUE) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition(); // gets item position
                        if (position != RecyclerView.NO_POSITION) {
                            sharedPreferences = v.getContext().getSharedPreferences("Evenement", Context.MODE_PRIVATE);
                            Evenement ce = mData.get(position);
                            String aa = ce.getNom();
                            int a = ce.getId();
                            System.out.println(a + "aaaa");


                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("evenement_nom", ce.getNom());
                            editor.putString("evenement_type", ce.getType());
                            editor.putInt("evenement_prix", ce.getPrix());
                            editor.putString("evenement_date", ce.getDateDebut());
                            editor.putString("location_evenement", ce.getLieu());
                            editor.putInt("evenement_infoline", ce.getInfoline());
                            editor.putInt("userIdE", ce.getId_user());
                            editor.putInt("eventId", ce.getId());


                            editor.apply();
                            //det.setVisibility(View.VISIBLE);
                            //test.setText(ce.getNom_evenement());


                            // Fragment fragg = new ModifierEvenement();
                            // ((AppCompatActivity) v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.details, fragg).commit();


                        }

                    }
                });
            }


        }
    }
}
