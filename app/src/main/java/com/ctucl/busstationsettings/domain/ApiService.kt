package com.ctucl.busstationsettings.domain
import com.ctucl.busstationsettings.domain.model.DbResponse
import retrofit2.Call
import com.ctucl.busstationsettings.domain.model.DeviceParamsPost
import com.ctucl.busstationsettings.domain.model.MecanismResponse
import com.ctucl.busstationsettings.domain.model.apiActionResponse
import com.ctucl.busstationsettings.domain.model.apiDbResponse
import com.ctucl.busstationsettings.domain.model.apiPost
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface MyApiService {
    @GET("api/mecanism")
    suspend fun getParams(): MecanismResponse
    @POST("api/mecanism")
    suspend fun doSomething(
        @Body data:apiPost
    ): apiActionResponse
    @GET("api/mecanism")
    suspend fun getDbParams(
        @Body data:apiPost
    ): DbResponse
    @POST("/api/database")
    fun postParams(
        @Body data: DeviceParamsPost
    ): Call<Void>
    @GET("/api/database")
    suspend fun getLastTransactions(
        @Query("operation") operation: String
    ): apiDbResponse

}

object RetrofitInstance {
    private var baseUrl: String = "http://192.168.1.81:5000/"

    @Volatile
    private var retrofit: Retrofit? = null

    // Cambiar la base URL y actualizar la instancia de Retrofit
    fun setBaseUrl(newUrl: String) {
        baseUrl = "http://$newUrl:5000/"
        retrofit = createRetrofitInstance()
    }

    // Crear una instancia de Retrofit
    private fun createRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    // Obtener la API actualizada
    val api: MyApiService
        get() {
            if (retrofit == null) {
                synchronized(this) {
                    if (retrofit == null) {
                        retrofit = createRetrofitInstance()
                    }
                }
            }
            return retrofit!!.create(MyApiService::class.java)
        }

}
//object RetrofitInstance {
//    private const val BASE_URL = "http://192.168.1.81:5000/"
//
//    val api: MyApiService by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(MyApiService::class.java)
//    }
//}