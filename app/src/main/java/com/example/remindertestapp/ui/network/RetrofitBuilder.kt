package com.example.remindertestapp.ui.network


class RetrofitBuilder {

    //lazy: define heavy variable as lazy to execute it when call it only

    private val apiService : ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://40.115.6.93:4525/")   // Replace with the actual API base URL  // base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }


}