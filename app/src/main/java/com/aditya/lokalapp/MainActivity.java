package com.aditya.lokalapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import com.aditya.lokalapp.Adapter.CryptoDataRecyclerViewAdapter;
import com.aditya.lokalapp.CryptoApi.CryptoApiInterface;
import com.aditya.lokalapp.Model.CryptoInfo;
import com.aditya.lokalapp.Model.CurrencyExchangeApi;
import com.aditya.lokalapp.Model.CryptoListData;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    public static String target = " ";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.coinlayer.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private Handler handler = new Handler();
    private CountDownTimer countDownTimer;
    private Runnable runnableCode = new Runnable() {
        @Override
        public void run() {
            HashMap<String ,Double> rates = new HashMap<String,Double>();
            makeFirstApiCall(rates);

            handler.postDelayed(this, 180000);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize RecyclerView with an empty adapter
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CryptoDataRecyclerViewAdapter(new ArrayList<>()));
        HashMap<String,Double> rates = new HashMap<String,Double>();
        makeFirstApiCall(rates);
        handler.post(runnableCode);
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                HashMap<String,Double> rates = new HashMap<String,Double>();
                makeFirstApiCall(rates);
                swipeRefreshLayout.setRefreshing(false);
                startTimer();

//                Calendar calendar = Calendar.getInstance();
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a" , Locale.getDefault());
//                String currentTime = simpleDateFormat.format(calendar.getTime());
//                TextView timeTextView = findViewById(R.id.lastRefreshTimeTextView);
//                timeTextView.setText("Last Refreshed: " + currentTime);
            }
        });
        startTimer();
    }
    // This method starts the timer
    // It is called when the app is first opened and when the user clicks the refresh button
    // It is also called when the countdown timer finishes
    // The countdown timer is set to 3 minutes
    // When the countdown timer finishes, the timer is restarted
    private void startTimer() {
            if(countDownTimer != null){
                countDownTimer.cancel();
            }
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a" , Locale.getDefault());
            String currentTime = simpleDateFormat.format(calendar.getTime());
            TextView timeTextView = findViewById(R.id.lastRefreshTimeTextView);
            timeTextView.setText("Last "+"\n"+"Refreshed: " + currentTime);
            countDownTimer = new CountDownTimer(180000, 1000) {
            TextView countDownText = findViewById(R.id.countdownTextView);
            public void onTick(long millisUntilFinished) {
                countDownText.setText("Seconds "+"\n" +"Remaining: "+ millisUntilFinished / 1000);
            }
            public void onFinish() {
                countDownText.setText(R.string.refreshing);
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a" , Locale.getDefault());
                String currentTime = simpleDateFormat.format(calendar.getTime());
                TextView timeTextView = findViewById(R.id.lastRefreshTimeTextView);
                timeTextView.setText("Last"+"\n" +"Refreshed: " + currentTime);
                start();
            }

        }.start();
    }
    // This method makes the first API call
    // It is called when the app is first opened and when the user clicks the refresh button
    // It is also called when the countdown timer finishes
    // The countdown timer is set to 3 minutes
    // When the countdown timer finishes, the timer is restarted
    private void makeFirstApiCall(Map<String,Double> rates){
        CryptoApiInterface cryptoApi = retrofit.create(CryptoApiInterface.class);
        Call<CurrencyExchangeApi> firstApiCall = cryptoApi.getExchangeCurrency();
        firstApiCall.enqueue(new Callback<CurrencyExchangeApi>() {
            @Override
            public void onResponse(Call<CurrencyExchangeApi> call, Response<CurrencyExchangeApi> response) {
                if (!response.isSuccessful()) {
                    Log.d("FirstApiCall", "Code: " + response.code());
                    return;
                }
                CurrencyExchangeApi currencyExchangeApi = response.body();
                if(currencyExchangeApi != null && currencyExchangeApi.isSuccess()){
                    Map<String,Double> rates = currencyExchangeApi.getRates();
                    Log.d("FirstApiCall", "Success: " + currencyExchangeApi.isSuccess());
                    Log.d("FirstApiCall", "Target: " + currencyExchangeApi.getTarget());
                    Log.d("FirstApiCall", "Rates: " + currencyExchangeApi.getRates());
                    target = currencyExchangeApi.getTarget();
                    makeSecondApiCall(currencyExchangeApi.getRates() , currencyExchangeApi.getTarget());
                }
            }
            @Override
            public void onFailure(Call<CurrencyExchangeApi> call, Throwable t) {
                if (t instanceof IOException) {
                    Log.d("FirstApiCall", "An error occurred: " + t.getMessage());
                } else {
                    Log.d("FirstApiCall", "An error occurred: " + t.getMessage());
                }
            }
        });

    }
    // This method makes the second API call
    // It is called when the first API call is successful
    // It is also called when the user clicks the refresh button
    // The second API call is made with the symbols from the first API call

    private void makeSecondApiCall(Map<String,Double> rates , String target){
        CryptoApiInterface cryptoApi = retrofit.create(CryptoApiInterface.class);
        Call<CryptoListData> secondApiCall = cryptoApi.getCryptoInfo(TextUtils.join(",", rates.keySet()));
        secondApiCall.enqueue(new Callback<CryptoListData>() {
            @Override
            public void onResponse(Call<CryptoListData> call, Response<CryptoListData> response) {
                if (!response.isSuccessful()) {
                    Log.d("SecondApiCall", "Code: " + response.code());
                    return;
                }
                CryptoListData cryptoListData = response.body();
                if(cryptoListData != null && cryptoListData.isSuccess()){
                    HashMap<String, CryptoInfo> cryptoInfoMap = cryptoListData.getCrypto();
                    // Now you can use the cryptoInfoMap
                    Log.d("SecondApiCall", "Success: " + cryptoListData.isSuccess());
                    displayData(rates,cryptoInfoMap,target);
                }
            }
            @Override
            public void onFailure(Call<CryptoListData> call, Throwable t) {
                Log.d("SecondApiCall", "An error occurred: " + t.getMessage());
            }
        });
    }
    // This method displays the data in the RecyclerView
    // It is called when the second API call is successful
    // It is also called when the user clicks the refresh button
    // The data is displayed in the RecyclerView
    private void displayData(Map<String, Double> rates , Map<String, CryptoInfo> cryptoInfoMap,String target) {
        ArrayList<CryptoData> cryptoDataList = new ArrayList<>();
        for(String key: rates.keySet()) {
            if (cryptoInfoMap.containsKey(key)) {
                if (rates == null) {
                    rates = new HashMap<>();
                }
                double rate = rates.get(key);
                CryptoInfo cryptoInfo = cryptoInfoMap.get(key);

                CryptoData combinedata = new CryptoData();
                combinedata.setsymbol(key);
                combinedata.setName_full(cryptoInfo.getName_full());
                combinedata.setName(cryptoInfo.getName());
                combinedata.setMax_supply(cryptoInfo.getMax_supply());
                combinedata.setIcon_url(cryptoInfo.getIcon_url());
                combinedata.setRates(rate);
                cryptoDataList.add(combinedata);
            }

        }
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        CryptoDataRecyclerViewAdapter adapter = new CryptoDataRecyclerViewAdapter(cryptoDataList);
        recyclerView.setAdapter(adapter);
    }
}