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
import com.formationandroid.courir.ws.ReturnWSStock
import com.formationandroid.courir.ws.WSInterface
import kotlinx.android.synthetic.main.activity_stock.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StockActivity : AppCompatActivity() {
    var id: Int = 0;
    var shoeSize: Int = 0;
    private var modelsAdapter: ModelsAdapter? = null;
    lateinit var returnedWSModels : MutableList<ReturnWSModel>;
    var idModel: Int = 0;

    private val serviceRetrofit = RetrofitSingleton.retrofit.create(WSInterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)

        this.callingWebService(serviceRetrofit.getModels(), this);
    }

    fun submit(view: View) {
        if (id > 0) {
            val model = ReturnWSModel(idModel, 0, "", "");
            val stock = ReturnWSStock(id, idModel, model, price.text.toString().toFloat(), quantity_edit_text.text.toString().toInt(), shoeSize)
            this.callPostWebService(serviceRetrofit.putStock(stock, "http://192.168.1.5:5000/V1/Courir/Stocks/" + stock.Id));
            val intent = Intent(view.context, MainActivity::class.java)
            view.context.startActivity(intent)
        }
        else {
            val model = ReturnWSModel(idModel, 0, "", "");
            val stock = ReturnWSStock(id, idModel, model, price.text.toString().toFloat(), quantity_edit_text.text.toString().toInt(), shoeSize)
            this.callPostWebService(serviceRetrofit.postStock(stock));
            val intent = Intent(view.context, MainActivity::class.java)
            view.context.startActivity(intent);
        }
    }

    private fun callingWebService(call: Call<MutableList<ReturnWSModel>>, context: StockActivity) {
        call.enqueue(object : Callback<MutableList<ReturnWSModel>>
        {
            override fun onResponse(
                call: Call<MutableList<ReturnWSModel>>,
                response: Response<MutableList<ReturnWSModel>>
            ) {
                // If the http request is successful
                if (response.isSuccessful)
                {
                    // Reading the body
                    val returnWSModel = response.body()

                    if (returnWSModel != null) {
                        returnedWSModels = returnWSModel;
                        var adapter : ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(context, R.array.shoeSize, android.R.layout.simple_spinner_item)
                        spinner_shoe.adapter = adapter;

                        spinner_shoe.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>, view: View?,
                                position: Int, id: Long
                            ) {
                                shoeSize = position;
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {

                            }
                        })

                        var names : MutableList<String> = mutableListOf();
                        for (i in 0..(returnedWSModels.size - 1)) {
                            names.add(returnedWSModels[i].Name);
                        }


                        var newAdapter : ArrayAdapter<String> = ArrayAdapter<String>(
                            context,
                            android.R.layout.simple_spinner_item,
                            names
                        )

                        spinner_foreign_model.setAdapter(newAdapter)

                        spinner_foreign_model.setOnItemSelectedListener(object : OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>, view: View,
                                position: Int, id: Long
                            ) {
                                idModel = returnedWSModels[position].Id;
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {

                            }
                        })

                        id = intent.getIntExtra("Id", 0);
                        price.setText(intent.getFloatExtra("Price", 0f).toString());
                        quantity_edit_text.setText(intent.getIntExtra("Quantity", 0).toString());
                        shoeSize = intent.getIntExtra("ShoeSize", 0);
                        idModel = intent.getIntExtra("IdModel", 0);
                        spinner_shoe.setSelection(shoeSize);
                        var positionModel = 0;
                        for (i in 0..(returnedWSModels.size - 1)) {
                            if (returnedWSModels[i].Id == idModel) {
                                positionModel = i;
                            }
                        }

                        spinner_foreign_model.setSelection(positionModel);
                    }
                }
            }

            override fun onFailure(call: Call<MutableList<ReturnWSModel>>, t: Throwable)
            {
            }
        })
    }

    private fun callPostWebService(call: Call<ReturnWSStock>) {
        call.enqueue(object : Callback<ReturnWSStock>
        {
            override fun onResponse(
                call: Call<ReturnWSStock>,
                response: Response<ReturnWSStock>
            ) {
                // If the http request is successful
                if (response.isSuccessful)
                {
                }
            }

            override fun onFailure(call: Call<ReturnWSStock>, t: Throwable)
            {
            }
        })
    }
}