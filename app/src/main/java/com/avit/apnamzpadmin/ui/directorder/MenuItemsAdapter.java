package com.avit.apnamzpadmin.ui.directorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.avit.apnamzpadmin.R;
import com.avit.apnamzpadmin.models.shop.ShopItemData;
import com.avit.apnamzpadmin.utils.PrettyStrings;

import java.util.List;

public class MenuItemsAdapter extends RecyclerView.Adapter<MenuItemsAdapter.MenuItemsViewHolder>{

    public interface  MenuItemsActions {
        void addToOrderItems(ShopItemData shopItemData);
    }

    private List<ShopItemData> menuItems;
    private Context context;
    private MenuItemsActions menuItemsActions;

    public MenuItemsAdapter(Context context,List<ShopItemData> menuItems,MenuItemsActions menuItemsActions) {
        this.context = context;
        this.menuItems = menuItems;
        this.menuItemsActions = menuItemsActions;
    }

    @NonNull
    @Override
    public MenuItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart,parent,false);
        return new MenuItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemsViewHolder holder, int position) {
        ShopItemData curr = menuItems.get(position);

        holder.priceView.setText(PrettyStrings.getPriceInRupees(curr.getPricings().get(0).getPrice()));
        holder.nameView.setText(curr.getName());

        holder.itemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuItemsActions.addToOrderItems(new ShopItemData(curr));
            }
        });


    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public void replaceItems(List<ShopItemData> newData){
        menuItems = newData;
        notifyDataSetChanged();
    }

    public class MenuItemsViewHolder extends RecyclerView.ViewHolder {

        public TextView nameView, priceView;
        public CardView itemContainer;

        public MenuItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.itemName);
            priceView = itemView.findViewById(R.id.itemPrice);
            itemContainer = itemView.findViewById(R.id.item_container);
        }
    }
}
