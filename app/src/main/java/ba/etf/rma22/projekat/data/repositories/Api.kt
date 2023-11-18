package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.*
import retrofit2.Response
import retrofit2.http.*

interface Api {
    @GET("istrazivanje")
    suspend fun getIstrazivanja(
        @Query("api_key") apiKey: String = AccountRepository.getHash()
    ): Response<List<Istrazivanje>>

    @GET("istrazivanje/{id}")
    suspend fun getIstrazivanjeById(@Path("id") id:Int,
                         @Query("api_key") apiKey: String = AccountRepository.getHash()
    ): Response<Istrazivanje>

    @GET("grupa/{gid}/istrazivanje")
    suspend fun getIstrazivanjaZaGrupu(@Path("gid") gid:Int,
        @Query("api_key") apiKey: String = AccountRepository.getHash()
    ): Response<List<Istrazivanje>>

    @GET("anketa/{id}/grupa")
    suspend fun getGrupeZaIstrazivanje(@Path("id") id:Int,
                                       @Query("api_key") apiKey: String = AccountRepository.getHash()
    ): Response<List<Grupa>>

    @FormUrlEncoded
    @POST("grupa/{gid}/student/{id}")
    suspend fun upisiUGrupu(@Path("gid") gid:Int,
        @Path("id") id:String,
        @Query("api_key")apiKey: String = AccountRepository.getHash(),
                            @Field("id") idGrupe: Int,
                            @Field("naziv") naziv: String
    ): Response<Grupa>

    @GET("student/{id}/grupa")
    suspend fun getUpisaneGrupe(@Path("id") id:String,
                                       @Query("api_key") apiKey: String = AccountRepository.getHash()
    ): Response<List<Grupa>>

    @GET("grupa")
    suspend fun getGrupe(
        @Query("api_key") apiKey: String = AccountRepository.getHash()
    ): Response<List<Grupa>>

    @GET("grupa/{id}")
    suspend fun getGrupaById(@Path("id") id:Int,
                                    @Query("api_key") apiKey: String = AccountRepository.getHash()
    ): Response<Grupa>

    @GET("anketa")
    suspend fun getAll(
        @Query("api_key") apiKey: String = AccountRepository.getHash()
    ): Response<List<Anketa>>

    @GET("anketa/{id}")
    suspend fun getById(@Path("id") id:Int,
                             @Query("api_key") apiKey: String = AccountRepository.getHash()
    ): Response<Anketa>

    @GET("grupa/{id}/anketa")
    suspend fun getUpisane(@Path("id") id:Int,
                                @Query("api_key") apiKey: String = AccountRepository.getHash()
    ): Response<List<Anketa>>

    @GET("student/{id}/anketataken/{ktid}/odgovori")
    suspend fun getOdgovoriAnketa(@Path("id") id:String,
                            @Path("ktid") ktid:Int,
                            @Query("api_key")apiKey: String = AccountRepository.getHash()
    ): Response<List<Odgovor>>

    @FormUrlEncoded
    @POST("student/{id}/anketataken/{ktid}/odgovori")
    suspend fun postaviOdgovorAnketa(@Path("id") id:String,
                                  @Path("ktid") ktid:Int,
                                  @Query("api_key")apiKey: String = AccountRepository.getHash(),
                                     @Field("odgovor") odgovor: Int,
                                     @Field("pitanje") pitanje: Int,
                                     @Field("progres") progres: Int
    ): Response<Odgovor>

    @GET("student/{id}/anketataken")
    suspend fun getPoceteAnkete(@Path("id") id:String,
                                  @Query("api_key")apiKey: String = AccountRepository.getHash()
    ): Response<List<AnketaTaken>>

    @FormUrlEncoded
    @POST("student/{id}/anketa/{kid}")
    suspend fun zapocniAnketu(@Path("id") id:String,
                              @Path("kid") ktid:Int,
                                @Query("api_key")apiKey: String = AccountRepository.getHash(),
                                @Field("id") idAnkete: Int,
                                @Field("student") student: String,
                                @Field("progres") progres: Int,
                                @Field("datumRada") datumRada: String,
                                @Field("AnketumId") AnketumId: Int
    ): Response<AnketaTaken>

    @GET("anketa/{id}/pitanja")
    suspend fun getPitanja(@Path("id") id:Int,
                                       @Query("api_key") apiKey: String = AccountRepository.getHash()
    ): Response<List<Pitanje>>



}