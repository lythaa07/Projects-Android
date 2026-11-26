package com.kelasp1.quizmobile;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @GET("api/profil")
    Call<ProfilResponse> getProfil();

    @POST("api/profil")
    Call<Profil> saveProfil(@Body Profil profil);

    @PUT("api/profil/{kode}")
    Call<Profil> updateProfil(@Path("kode") String kode, @Body Profil profil);

    @DELETE("api/profil/{kode}")
    Call<Void> deleteProfil(@Path("kode") String kode);
}
