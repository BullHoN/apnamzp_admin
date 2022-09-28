package com.avit.apnamzpadmin.ui.servicestatus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;

import com.avit.apnamzpadmin.R;
import com.avit.apnamzpadmin.models.admin.ServiceStatus;
import com.avit.apnamzpadmin.models.network.NetworkResponse;
import com.avit.apnamzpadmin.network.NetworkAPI;
import com.avit.apnamzpadmin.network.RetrofitClient;
import com.avit.apnamzpadmin.utils.ErrorUtils;
import com.google.android.material.textfield.TextInputEditText;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ServiceStatusActivity extends AppCompatActivity {

    private String statusTypes[] = {"close","rain","occasion"};
    private Spinner statusTypeSpinner;
    private Switch serviceSwitch;
    private TextInputEditText messageView;
    private ServiceStatusViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_status);

        viewModel = new ViewModelProvider(this).get(ServiceStatusViewModel.class);
        viewModel.getStatus(this);

        statusTypeSpinner = findViewById(R.id.animation_type_spinner);
        serviceSwitch = findViewById(R.id.service_status);
        messageView = findViewById(R.id.status_message);

        ArrayAdapter statusSpinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, statusTypes);

        statusSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusTypeSpinner.setAdapter(statusSpinnerAdapter);

        viewModel.getMutableServiceStatusLiveData().observe(this, new Observer<ServiceStatus>() {
            @Override
            public void onChanged(ServiceStatus serviceStatus) {
                serviceSwitch.setChecked(serviceStatus.isServiceOpen());
                messageView.setText(serviceStatus.getMessage());

                if(serviceStatus.getType().equals("close")){
                    statusTypeSpinner.setSelection(0);
                }
                else if(serviceStatus.getType().equals("rain")){
                    statusTypeSpinner.setSelection(1);
                }
                else {
                    statusTypeSpinner.setSelection(2);
                }

            }
        });

        findViewById(R.id.update_status_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceStatus serviceStatus = new ServiceStatus(serviceSwitch.isChecked()
                        ,messageView.getText().toString(), statusTypes[statusTypeSpinner.getSelectedItemPosition()]);

                updateStatus(serviceStatus);
            }
        });

    }

    private void updateStatus(ServiceStatus serviceStatus){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkAPI networkAPI = retrofit.create(NetworkAPI.class);

        Call<NetworkResponse> call = networkAPI.changeServiceStatus(serviceStatus);
        call.enqueue(new Callback<NetworkResponse>() {
            @Override
            public void onResponse(Call<NetworkResponse> call, Response<NetworkResponse> response) {
                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    Toasty.error(getApplicationContext(),errorResponse.getDesc(),Toasty.LENGTH_LONG)
                            .show();
                    return;
                }

                Toasty.success(getApplicationContext(),"Changed Successfully",Toasty.LENGTH_LONG)
                        .show();

                finish();

            }

            @Override
            public void onFailure(Call<NetworkResponse> call, Throwable t) {
                Toasty.error(getApplicationContext(),t.getMessage(),Toasty.LENGTH_LONG)
                        .show();
            }
        });

    }

}