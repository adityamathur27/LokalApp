package com.aditya.lokalapp.Model;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
public class CryptoListData {
    @SerializedName("success")
    private boolean success;
    @SerializedName("crypto")
    private HashMap<String , CryptoInfo> crypto;
    @SerializedName("fiat")
    private HashMap<String , String> fiat;
    public CryptoListData(boolean success, HashMap<String, CryptoInfo> crypto, HashMap<String, String> fiat) {
        this.success = success;
        this.crypto = crypto;
        this.fiat = fiat;
    }

    public HashMap<String, String> getFiat() {
        return fiat;
    }

    public void setFiat(HashMap<String, String> fiat) {
        this.fiat = fiat;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public HashMap<String, CryptoInfo> getCrypto() {
        return crypto;
    }

    public void setCrypto(HashMap<String, CryptoInfo> crypto) {
        this.crypto = crypto;
    }
}
