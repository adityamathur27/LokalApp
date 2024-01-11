package com.aditya.lokalapp.CryptoApi;

import com.aditya.lokalapp.Model.CurrencyExchangeApi;
import com.aditya.lokalapp.Model.CryptoListData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CryptoApiInterface {

    /**
     * This method represents the "/live" endpoint, which is used to get the exchange currency.
     * @return A Call object that can be used to send the API request.
     */
    @GET("/live?access_key=bf018352973adb5196b7e60665d22f22")
    Call<CurrencyExchangeApi> getExchangeCurrency();
    /**
     * This method represents the "/list" endpoint, which is used to get the crypto info.
     * @param symbols A string containing the symbols for which to get the crypto info.
     * @return A Call object that can be used to send the API request.
     */
    @GET("/list?access_key=bf018352973adb5196b7e60665d22f22")
    Call<CryptoListData> getCryptoInfo(@Query("symbols") String symbols);
}
