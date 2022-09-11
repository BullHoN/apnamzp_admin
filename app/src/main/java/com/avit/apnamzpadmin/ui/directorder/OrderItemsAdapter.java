package com.avit.apnamzpadmin.ui.directorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avit.apnamzpadmin.R;
import com.avit.apnamzpadmin.models.order.OrderItem;
import com.avit.apnamzpadmin.models.shop.ShopItemData;
import com.avit.apnamzpadmin.utils.PrettyStrings;

import java.util.List;

public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.OrderItemsViewHolder>{

    private List<ShopItemData> orderItems;
    private Context context;

    public OrderItemsAdapter(List<ShopItemData> orderItems, Context context) {
        this.orderItems = orderItems;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart,parent,false);
        return new OrderItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemsViewHolder holder, int position) {
        ShopItemData curr = orderItems.get(position);
        holder.nameView.setText(curr.getName() + "(" + curr.getPricings().get(0).getType() + ")");
        holder.priceView.setText(PrettyStrings.getPriceInRupees(curr.getPricings().get(0).getPrice()));

        holder.quantityView.setText(String.valueOf(curr.getQuantity()));

        holder.increaseQuantityView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curr.setQuantity(curr.getQuantity()+1);
                notifyDataSetChanged();
            }
        });

        holder.decreaseQuantityView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curr.setQuantity(curr.getQuantity()-1);
                notifyDataSetChanged();
            }
        });

        holder.deleteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderItems.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public void replaceItems(List<ShopItemData> newOrderItems){
        orderItems = newOrderItems;
        notifyDataSetChanged();
    }

    public class OrderItemsViewHolder extends RecyclerView.ViewHolder {

        public TextView nameView, priceView, quantityView;
        public LinearLayout increaseQuantityView, decreaseQuantityView;
        public ImageButton deleteView;

        public OrderItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.itemName);
            priceView = itemView.findViewById(R.id.itemPrice);
            quantityView = itemView.findViewById(R.id.quantityText);

            increaseQuantityView = itemView.findViewById(R.id.increaseQuantity);
            decreaseQuantityView = itemView.findViewById(R.id.decreaseQuantity);
            deleteView = itemView.findViewById(R.id.remove);

        }
    }
}
