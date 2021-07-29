package com.example.xbree.HomeAdapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xbree.DetailsActivity;
import com.example.xbree.Entities.Evenement;
import com.example.xbree.Entities.User;
import com.example.xbree.R;
import com.example.xbree.Retrofit.INodeJS;
import com.example.xbree.Retrofit.RetrofitClient;
import com.example.xbree.Utils.database.AppDataBase;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EvenementAdapter extends RecyclerView.Adapter<EvenementAdapter.myViewHolder> {

    Context mContext;
    private List<Evenement> mData;
    private List<Evenement> event_list = new ArrayList<>();
    SharedPreferences sharedPreferences, sharedPreferencesUE, sharedPreferencesU,sharedPreferencesV;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Button participer;
    RoundedImageView bk;
    int idus;
    TextView titleCamp, dateDebut, dateFin, price, location, username,nbplace;
    private AppDataBase database;
    public static INodeJS iNodeJS;


    public EvenementAdapter(Context mContext, List<Evenement> mDataa) {
        this.mContext = mContext;
        this.mData = mDataa;
    }

    @NonNull
    @Override
    public EvenementAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.card_item, parent, false);
        com.example.xbree.HomeAdapter.EvenementAdapter.myViewHolder vv = new com.example.xbree.HomeAdapter.EvenementAdapter.myViewHolder(v);

        sharedPreferencesUE = parent.getContext().getSharedPreferences("UserEvent", Context.MODE_PRIVATE);
        sharedPreferencesU = parent.getContext().getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
        idus = sharedPreferencesU.getInt("idUser", 0);

        database = AppDataBase.getAppDatabase(context);
        event_list = database.produitDao().getAll();

        return vv;
    }

    @Override
    public void onBindViewHolder(@NonNull EvenementAdapter.myViewHolder holder, int position) {
        Evenement evenement = mData.get(position);

        titleCamp.setText(evenement.getNom());
        dateDebut.setText(evenement.getDateDebut());
        dateFin.setText(evenement.getDateFin());
        price.setText(evenement.getPrix() + " DT");
        location.setText(evenement.getLieu());
        if (evenement.getNbPlace()==0)
        {
            nbplace.setText("Event Full");}
        else {
            nbplace.setText("Places " + evenement.getNbPlace());
        }


        Call<User> call = iNodeJS.getUserE(evenement.getId_user());
        System.out.println(call.toString() + "callllllllllll");
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();

                try {
                    SharedPreferences.Editor editor = sharedPreferencesUE.edit();
                    editor.putInt("idUser", user.getId());
                    editor.putInt("telUser", user.getPhone());
                    editor.putString("nomUser", user.getName());
                    editor.putString("prenomUser", user.getLname());
                    editor.putString("EmailUser", user.getEmail());
                    editor.putString("imageUser", user.getImage());
                    username.setText(user.getName() + " " + user.getLname());
                    editor.apply();
                } catch (NullPointerException ignored) {
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });


        bk = holder.background_img;
        Call<ResponseBody> calll = iNodeJS.getImage(evenement.getImage_eve());
        calll.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> calll, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                        bk.setImageBitmap(bmp);
                    } else {
                        // TODO
                    }
                } else {
                    // TODO
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> calll, Throwable t) {
                // TODO
            }
        });
        TextView titre = holder.pr_title;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class myViewHolder extends RecyclerView.ViewHolder {
        public TextView pr_title;
        public RoundedImageView background_img;
        public ImageView favori;

        public myViewHolder(@NonNull final View itemView) {
            super(itemView);
            sharedPreferencesV = itemView.getContext().getSharedPreferences("Evenement", Context.MODE_PRIVATE);
            final int id_ev = sharedPreferencesV.getInt("id_evenement", 0);
            iNodeJS = RetrofitClient.getInstance().create(INodeJS.class);
            background_img = itemView.findViewById(R.id.background_img);
            dateDebut = itemView.findViewById(R.id.dateDebut);
            dateFin = itemView.findViewById(R.id.dateFin);
            favori = itemView.findViewById(R.id.favori);
            participer = itemView.findViewById(R.id.participer);
            participer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    compositeDisposable.add(iNodeJS.addParticipant(idus, id_ev)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<String>() {
                                @Override
                                public void accept(String s)
                                        throws Exception {
                                    Call<Evenement>call=iNodeJS.updateEv(id_ev);
                                    call.enqueue(new Callback<Evenement>() {
                                        @Override
                                        public void onResponse(Call<Evenement> call, Response<Evenement> response) {

                                        }

                                        @Override
                                        public void onFailure(Call<Evenement> call, Throwable t) {

                                        }
                                    });
                                    participer.setText("deja participer!");
                                    System.out.println("evenement ajouté");
                                }
                            })
                    );

                }


            });


            price = itemView.findViewById(R.id.price);
            location = itemView.findViewById(R.id.location);
            titleCamp = itemView.findViewById(R.id.titleCamp);
            nbplace = itemView.findViewById(R.id.nbplacee);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    database = AppDataBase.getAppDatabase(v.getContext());
                    int position = getAdapterPosition(); // gets item position
                    if (position != RecyclerView.NO_POSITION) {
                        sharedPreferences = v.getContext().getSharedPreferences("Evenement", Context.MODE_PRIVATE);
                        sharedPreferencesUE = v.getContext().getSharedPreferences("UserEvent", Context.MODE_PRIVATE);
                        Evenement ce = mData.get(position);


                        System.out.println();
                        int aa = ce.getId_user();
                        System.out.println(aa + "aaaaaaa");
                        // test(aa);


                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("evenement_nom", ce.getNom());
                        editor.putString("evenement_type", ce.getType());
                        editor.putInt("evenement_prix", ce.getPrix());

                        editor.putString("evenement_date", ce.getDateDebut());
                        editor.putInt("id_evenement", ce.getId());
                        editor.putInt("id_user", ce.getId_user());
                        editor.putInt("evenement_infoline", ce.getInfoline());
                        editor.putInt("evenement_nbplace", ce.getNbPlace());


                        editor.apply();
                        //det.setVisibility(View.VISIBLE);
                        //test.setText(ce.getNom_evenement());


                    }


                    itemView.getContext().startActivity(new Intent(itemView.getContext(), DetailsActivity.class));


                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    return false;
                }
            });

        }
    }

}