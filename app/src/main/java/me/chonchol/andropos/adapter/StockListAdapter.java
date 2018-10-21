package me.chonchol.andropos.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.chonchol.andropos.R;
import me.chonchol.andropos.model.Stock;

public class StockListAdapter extends RecyclerView.Adapter<StockListAdapter.StockItemViewHolder> {

    private Context context;
    private List<Stock> stockList;

    public StockListAdapter(Context context, List<Stock> stockList) {
        this.context = context;
        this.stockList = stockList;
    }

    @NonNull
    @Override
    public StockItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_listview, parent, false);
        return new StockItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StockItemViewHolder holder, int position) {
        String serial;
        if (String.valueOf(position).isEmpty()){
            serial = "000";
        } else if ((String.valueOf(position)).length() == 1){
            serial = "00" + String.valueOf(position + 1);
        } else if (String.valueOf(position).length() == 2){
            serial = "0" + String.valueOf(position + 1);
        } else {
            serial = String.valueOf(position + 1);
        }

        holder.txtStockSerial.setText(serial);
        holder.txtStockProductName.setText(stockList.get(position).getProduct().getProductName());
        holder.txtStockQuantity.setText(String.valueOf(stockList.get(position).getQuantity()));

    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }

    public class StockItemViewHolder extends RecyclerView.ViewHolder {

        public TextView txtStockSerial, txtStockProductName, txtStockQuantity;

        public StockItemViewHolder(View itemView) {
            super(itemView);
            txtStockSerial = itemView.findViewById(R.id.txtStockSerial);
            txtStockProductName = itemView.findViewById(R.id.txtStockProductName);
            txtStockQuantity = itemView.findViewById(R.id.txtStockQuantity);
        }
    }
}
