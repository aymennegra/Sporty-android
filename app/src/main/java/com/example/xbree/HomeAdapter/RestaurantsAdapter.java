package com.example.xbree.HomeAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xbree.R;

import java.util.ArrayList;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.RestaurantsViewHolder> {
    private LayoutInflater layoutInflater;

    static ArrayList<RestaurantsHelperClass> RestaurantsLocations;

    public RestaurantsAdapter(ArrayList<RestaurantsHelperClass> RestaurantsLocations) {
        this.RestaurantsLocations = RestaurantsLocations;

    }

    @NonNull
    @Override
    public RestaurantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurants_card_design, parent, false);
        RestaurantsViewHolder RestaurantsViewHolder = new RestaurantsViewHolder(view);

        return RestaurantsViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RestaurantsViewHolder holder, int position) {
        RestaurantsHelperClass title = RestaurantsLocations.get(position);
        RestaurantsHelperClass RestaurantsHelperClass = RestaurantsLocations.get(position);
        holder.image.setImageResource(RestaurantsHelperClass.getImage());
        holder.title.setText(RestaurantsHelperClass.getTitle());
        holder.descrip.setText(RestaurantsHelperClass.getDescrip());
        holder.location.setText(RestaurantsHelperClass.getLocation());

    }

    @Override
    public int getItemCount() {
        return RestaurantsLocations.size();
    }


    public class RestaurantsViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView descrip;
        TextView location;

        public RestaurantsViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.Restaurants_image);
            title = itemView.findViewById(R.id.Restaurants_title);
            descrip = itemView.findViewById(R.id.Restaurants_descrip);
            location = itemView.findViewById(R.id.Restaurants_location);

        }

    }
}
