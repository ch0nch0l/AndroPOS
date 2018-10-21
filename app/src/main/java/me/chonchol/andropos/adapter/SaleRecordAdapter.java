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
import me.chonchol.andropos.model.QuotationList;
import me.chonchol.andropos.model.Stock;

public class SaleRecordAdapter extends RecyclerView.Adapter<SaleRecordAdapter.StockItemViewHolder> {

    private Context context;
    private List<QuotationList> quotationLists;

    public SaleRecordAdapter(Context context, List<QuotationList> quotationLists) {
        this.context = context;
        this.quotationLists = quotationLists;
    }

    @NonNull
    @Override
    public StockItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sale_record_listview, parent, false);
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

        holder.txtSaleSerial.setText(serial);
        holder.txtSaleCustomerName.setText(quotationLists.get(position).getQuotation().getCustomer().getCustomerName());
        holder.txtSaleProductName.setText(quotationLists.get(position).getProduct().getProductName());
        holder.txtSaleQuantity.setText(String.valueOf(quotationLists.get(position).getQuantity()));
        holder.txtSaleAmount.setText(String.valueOf(quotationLists.get(position).getTotalPrice()));

    }

    @Override
    public int getItemCount() {
        return quotationLists.size();
    }

    public class StockItemViewHolder extends RecyclerView.ViewHolder {

        public TextView txtSaleSerial, txtSaleCustomerName, txtSaleProductName, txtSaleQuantity, txtSaleAmount;

        public StockItemViewHolder(View itemView) {
            super(itemView);
            txtSaleSerial = itemView.findViewById(R.id.txtSaleSerial);
            txtSaleCustomerName = itemView.findViewById(R.id.txtSaleCustomerName);
            txtSaleProductName = itemView.findViewById(R.id.txtSaleProductName);
            txtSaleQuantity = itemView.findViewById(R.id.txtSaleQuantity);
            txtSaleAmount = itemView.findViewById(R.id.txtSaleAmount);
        }
    }
}
