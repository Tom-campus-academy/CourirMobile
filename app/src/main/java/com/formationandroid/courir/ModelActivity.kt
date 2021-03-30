package com.formationandroid.courir

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.formationandroid.courir.ws.RetrofitSingleton
import com.formationandroid.courir.ws.ReturnWSModel
import com.formationandroid.courir.ws.WSInterface
import kotlinx.android.synthetic.main.activity_model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ModelActivity : AppCompatActivity() {
    var brand: Int = 0;
    var id: Int = 0;
    private val serviceRetrofit = RetrofitSingleton.retrofit.create(WSInterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_model)

        var adapter : ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this, R.array.brand, android.R.layout.simple_spinner_item)
        spinner_model.adapter = adapter;

        spinner_model.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?,
                position: Int, id: Long
            ) {
                brand = position;
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        })

        brand = intent.getIntExtra("Brand", 0);
        spinner_model.setSelection(brand);
        id = intent.getIntExtra("Id", 0).toInt();
        identification_number.setText(intent.getStringExtra("IdentificationNumber"));
        name.setText(intent.getStringExtra("Name"));
    }

    fun submit(view: View) {
        if (id > 0) {
            val model = ReturnWSModel(id, brand, identification_number.text.toString(), name.text.toString())
            this.callPostWebService(serviceRetrofit.putModel(model, "http://192.168.1.5:5000/V1/Courir/Models/" + model.Id));
            val intent = Intent(view.context, MainActivity::class.java)
            view.context.startActivity(intent)
        }
        else {
            val model = ReturnWSModel(0, brand, identification_number.text.toString(), name.text.toString())
            this.callPostWebService(serviceRetrofit.postModel(model));
            val intent = Intent(view.context, MainActivity::class.java)
            view.context.startActivity(intent)
        }
    }

    private fun callPostWebService(call: Call<ReturnWSModel>) {
        call.enqueue(object : Callback<ReturnWSModel>
        {
            override fun onResponse(
                call: Call<ReturnWSModel>,
                response: Response<ReturnWSModel>
            ) {
                // If the http request is successful
                if (response.isSuccessful)
                {
                }
            }

            override fun onFailure(call: Call<ReturnWSModel>, t: Throwable)
            {
            }
        })
    }
}