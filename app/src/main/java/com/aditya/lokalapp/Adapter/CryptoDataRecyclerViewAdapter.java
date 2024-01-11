package com.aditya.lokalapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.aditya.lokalapp.MainActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.aditya.lokalapp.CryptoData;
import com.aditya.lokalapp.R;
import com.bumptech.glide.Glide;

import java.util.List;
/**
 * This method represents the "/list" endpoint, which is used to get the crypto info.
 * @return A Call object that can be used to send the API request.
 */
public class CryptoDataRecyclerViewAdapter extends RecyclerView.Adapter<CryptoDataRecyclerViewAdapter.ViewHolder>{
    private List<CryptoData> cryptoDataList;

    /**
     * Constructor for the RecylerAdapter.
     * @param cryptoDataList The list of CryptoData objects to display in the RecyclerView.
     */
    public CryptoDataRecyclerViewAdapter(List<CryptoData> cryptoDataList) {
        this.cryptoDataList = cryptoDataList;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iconImageView;
        public TextView nameTextView;
        public TextView rateTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.iconImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            rateTextView = itemView.findViewById(R.id.rateTextView);
        }
    }

    /**
     * This method inflates the layout for a single item in the RecyclerView.
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_items, parent, false);
        return new ViewHolder(view);
    }
    /**
     * This method binds the data from a CryptoData object to the views in the ViewHolder.
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String target = MainActivity.target;

        CryptoData cryptoData = cryptoDataList.get(position);

        holder.nameTextView.setText(cryptoData.getName_full());
        double val = cryptoData.getRates();
        String rates = String.format("%.6f", val);
        holder.rateTextView.setText(String.valueOf(target+" "+ rates));
        Glide.with(holder.itemView.getContext())
                .load(cryptoData.getIcon_url())
                .into(holder.iconImageView);

    }

    /**
     * This method returns the total number of items in the data set held by the adapter.
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return cryptoDataList.size();
    }



}
