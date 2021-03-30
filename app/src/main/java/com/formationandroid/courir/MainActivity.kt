package com.formationandroid.courir

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.formationandroid.courir.ws.RetrofitSingleton
import com.formationandroid.courir.ws.ReturnWSModel
import com.formationandroid.courir.ws.ReturnWSStock
import com.formationandroid.courir.ws.WSInterface
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.price
import kotlinx.android.synthetic.main.item_stock.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val serviceRetrofit = RetrofitSingleton.retrofit.create(WSInterface::class.java)
    private var modelsAdapter: ModelsAdapter? = null
    private var stocksAdapter: StocksAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun addModel(view: View) {
        val intent = Intent(view.context, ModelActivity::class.java)
        intent.putExtra("Brand", 0);
        intent.putExtra("Id", 0);
        intent.putExtra("IdentificationNumber", "");
        intent.putExtra("Name", "");
        view.context.startActivity(intent)
    }

    fun filter(view: View) {
        if (editText.text.toString() != "") {
            this.callingWebService(serviceRetrofit.filter("http://192.168.1.5:5000/V1/Courir/Models/" + editText.text + "/Filter"), view)
        }
        else {
            this.callingWebService(serviceRetrofit.getModels(), view);
        }
    }

    fun addStock(view: View) {
        val intent = Intent(view.context, StockActivity::class.java)
        intent.putExtra("Price", 0);
        intent.putExtra("Id", 0);
        intent.putExtra("Quantity", 0);
        intent.putExtra("ShoeSize", 0);
        intent.putExtra("IdModel", 0);
        view.context.startActivity(intent)
    }

    fun filterStock(view: View) {
        if (editTextStock.text.toString() != "") {
            this.callingWebServiceReturnWSStock(serviceRetrofit.filterStock("http://192.168.1.5:5000/V1/Courir/Stocks/" + editTextStock.text + "/Filter"), view)
        }
        else {
            this.callingWebServiceReturnWSStock(serviceRetrofit.getStocks(), view);
        }
    }

    private fun callingWebService(call: Call<MutableList<ReturnWSModel>>, root: View) {
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

                    val layoutManager = LinearLayoutManager(root.context)
                    list_model.layoutManager = layoutManager

                    if (returnWSModel != null) {
                        // Settings the list of values
                        modelsAdapter = ModelsAdapter(returnWSModel)
                        list_model.adapter = modelsAdapter

                        // Notify the view to say the list changed (doesn't refresh without this)
                        list_model.adapter?.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<MutableList<ReturnWSModel>>, t: Throwable)
            {
            }
        })
    }

    private fun callingWebServiceReturnWSStock(call: Call<MutableList<ReturnWSStock>>, root: View) {
        call.enqueue(object : Callback<MutableList<ReturnWSStock>>
        {
            override fun onResponse(
                call: Call<MutableList<ReturnWSStock>>,
                response: Response<MutableList<ReturnWSStock>>
            ) {
                // If the http request is successful
                if (response.isSuccessful)
                {
                    // Reading the body
                    val returnWSStock = response.body()

                    val layoutManager = LinearLayoutManager(root.context)
                    list_stock.layoutManager = layoutManager

                    if (returnWSStock != null) {
                        // Settings the list of values
                        stocksAdapter = StocksAdapter(returnWSStock);
                        list_stock.adapter = stocksAdapter

                        // Notify the view to say the list changed (doesn't refresh without this)
                        list_stock.adapter?.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<MutableList<ReturnWSStock>>, t: Throwable)
            {
            }
        })
    }
}