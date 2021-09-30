package org.techtown.networkretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import org.techtown.networkretrofit.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

//레드토핏 인터페이스는 호출방식,주소,데이터 등을 지정합니다. Retrofit 라이브러리는 인터페이스를 해석해 HTTP통신을 처리합니다.
class MainActivity : AppCompatActivity(){
    val binding by lazy{ActivityMainBinding.inflate(layoutInflater)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        with(binding){
            val customAdapter = CustomAdapter()
            recyclerView.adapter = customAdapter
            recyclerView.layoutManager = LinearLayoutManager(baseContext)

            val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.github.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            val githubService = retrofit.create(GithubService::class.java)

            buttonRequest.setOnClickListener {
                githubService.users().enqueue(object : Callback<Repository> {
                    override fun onFailure(call: Call<Repository>, t: Throwable) {
                        Log.e("매인 액티비티","${t.localizedMessage}")
                    }

                    override fun onResponse(call: Call<Repository>, response: Response<Repository>) {
                        response.body()?.let {  result ->
                            customAdapter.userList = result
                            customAdapter.notifyDataSetChanged()
                        }
                    }
                })
            }
        }
    }
}
interface GithubService{
    @GET("users/Kotlin/repos")
    fun users(): Call<Repository>
}