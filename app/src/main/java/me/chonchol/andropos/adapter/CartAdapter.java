package me.chonchol.andropos.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import me.chonchol.andropos.R;
import me.chonchol.andropos.model.Product;

/**
 * Created by mehedi.chonchol on 16-Oct-18.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{

    private List<Product> cartProductList;
    private Integer quantity;

    public CartAdapter(List<Product> cartProductList, Integer quantity) {
        this.cartProductList = cartProductList;
        this.quantity = quantity;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cart_item_view, parent, false);
        CartViewHolder cartViewHolder = new CartViewHolder(view);
        return cartViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = cartProductList.get(position);

        TextView txtCartProductName = holder.txtCartProductName;
        TextView txtCartCategory = holder.txtCartCategory;
        TextView txtCartTotalPrice = holder.txtCartTotalPrice;
        EditText inputCartQuantity = holder.inputCartQuantity;
        EditText txtCartUnitPrice = holder.txtCartUnitPrice;

        txtCartProductName.setText(product.getProductName().toString());
        txtCartCategory.setText(product.getSubcategory().getCategory().getCatName().toString());
        txtCartUnitPrice.setText(product.getPrice().toString());
        inputCartQuantity.setText(quantity);
        txtCartTotalPrice.setText(String.valueOf(quantity * product.getPrice()));

    }

    @Override
    public int getItemCount() {
        return cartProductList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private TextView txtCartProductName, txtCartCategory, txtCartTotalPrice;
        private EditText inputCartQuantity, txtCartUnitPrice;

        public CartViewHolder(View itemView) {
            super(itemView);

            txtCartProductName = itemView.findViewById(R.id.txtCartProductName);
            txtCartCategory = itemView.findViewById(R.id.txtCartCategory);
            txtCartTotalPrice = itemView.findViewById(R.id.txtCartTotalPrice);
            inputCartQuantity = itemView.findViewById(R.id.inputCartQuantity);
            txtCartUnitPrice = itemView.findViewById(R.id.txtCartUnitPrice);

        }
    }
}
