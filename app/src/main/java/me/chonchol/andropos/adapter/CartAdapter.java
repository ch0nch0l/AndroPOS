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
import me.chonchol.andropos.model.CartProduct;
import me.chonchol.andropos.model.Product;

/**
 * Created by mehedi.chonchol on 16-Oct-18.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{

    private Context context;
    private List<CartProduct> cartProductList;

    public CartAdapter(Context context, List<CartProduct> cartProductList) {
        this.context = context;
        this.cartProductList = cartProductList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cart_item_view, parent, false);
        CartViewHolder cartViewHolder = new CartViewHolder(view);
        return cartViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartProduct cartProduct = cartProductList.get(position);

        holder.txtCartProductName.setText(cartProduct.getProduct().getProductName().toString());
        holder.txtCartCategory.setText(cartProduct.getProduct().getSubcategory().getCategory().getCatName().toString());
        holder.txtCartUnitPrice.setText(cartProduct.getProduct().getPrice().toString());
        holder.inputCartQuantity.setText(cartProduct.getQuantity().toString());
        holder.txtCartTotalPrice.setText(String.valueOf(cartProduct.getQuantity() * cartProduct.getProduct().getPrice()));

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
