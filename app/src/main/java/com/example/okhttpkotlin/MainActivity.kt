package com.example.okhttpkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_account.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rec_account.setHasFixedSize(true)
        rec_account.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        fetchJSON()
    }

    private fun fetchJSON() {
        val URL = "https://demomb.vnpay.vn:20157/train_src_acc.json"
        val request = Request.Builder().url(URL).build()
        val client = OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.SECONDS)
            .writeTimeout(2, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                println(body)
                val accountArray = ArrayList<Account>()
                val jsonObject = JSONObject(body)
                val jsonArray = jsonObject.optJSONArray("accounts")
                for (i in 0 until jsonArray.length()) {
                    var o : JSONObject = jsonArray.getJSONObject(i)
                    val accountNo = o.optString("accountNo").toString()
                    val availableBalance = o.optString("availableBalance").toString()
                    val currency = o.optString("currency").toString()
                    accountArray.add(Account(accountNo, availableBalance, currency))
                }
                runOnUiThread{
                    val adapter = AccountAdapter(accountArray)
                    rec_account.adapter = adapter
                }
            }
        })
    }

    inner class Account(val accountNo : String, val currentBalance: String, val currency :String)

}

