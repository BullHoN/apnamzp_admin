package com.avit.apnamzpadmin.ui.deliverysathistatus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.avit.apnamzpadmin.R;
import com.avit.apnamzpadmin.models.deliverysathi.DeliverySathiStatus;

import java.util.ArrayList;
import java.util.List;

public class DeliverySathisStatusActivity extends AppCompatActivity {

    private String TAG = "DeliverySathisStatusActivity";
    private RecyclerView deliverySathisRecyclerView;
    private DeliverySathisStatusViewModel viewModel;
    private DeliverySathiStatusAdapter deliverySathiStatusAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_sathis_status);

        viewModel = new ViewModelProvider(this).get(DeliverySathisStatusViewModel.class);
        viewModel.getDeliverySathisStatus(getApplicationContext());
        deliverySathisRecyclerView = findViewById(R.id.delivery_sathis);
        deliverySathisRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        deliverySathiStatusAdapter = new DeliverySathiStatusAdapter(new ArrayList<>(),this);
        deliverySathisRecyclerView.setAdapter(deliverySathiStatusAdapter);

        viewModel.getMutableLiveDeliverySathiStatusData().observe(this, new Observer<List<DeliverySathiStatus>>() {
            @Override
            public void onChanged(List<DeliverySathiStatus> deliverySathiStatuses) {
                deliverySathiStatusAdapter.updateItems(deliverySathiStatuses);
            }
        });

    }
}