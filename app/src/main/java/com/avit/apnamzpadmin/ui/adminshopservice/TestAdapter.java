package com.avit.apnamzpadmin.ui.adminshopservice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avit.apnamzpadmin.R;
import com.avit.apnamzpadmin.models.order.OrderItem;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestAdapterViewHolder>{

    private Context context;
    private List<String> orderItemList;

    public TestAdapter(Context context, List<String> orderItemList) {
        this.context = context;
        this.orderItemList = orderItemList;
    }

    @NonNull
    @Override
    public TestAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.test_layout,parent,false);
        return new TestAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestAdapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return orderItemList.size();
    }

    public class  TestAdapterViewHolder extends RecyclerView.ViewHolder {

        public TestAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
