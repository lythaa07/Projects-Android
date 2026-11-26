package com.kelasp1.quizmobile;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ProfilResponse {
    @SerializedName("data")
    private List<Profil> data;

    public List<Profil> getData() {
        return data;
    }

    public void setData(List<Profil> data) {
        this.data = data;
    }
}
