package com.formationandroid.courir.ui.home

import android.os.Bundle
import android.provider.DocumentsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.formationandroid.courir.MainActivity
import com.formationandroid.courir.R
import com.formationandroid.courir.StocksAdapter
import com.formationandroid.courir.ws.RetrofitSingleton
import com.formationandroid.courir.ws.ReturnWSStock
import com.formationandroid.courir.ws.WSInterface
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private val serviceRetrofit = RetrofitSingleton.retrofit.create(WSInterface::class.java)
    private var stocksAdapter: StocksAdapter? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val listStock: RecyclerView = root.findViewById(R.id.list_stock)

        this.callingWebService(serviceRetrofit.getStocks(), root)
        return root
    }

    private fun callingWebService(call: Call<MutableList<ReturnWSStock>>, root: View) {
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
                    val returnWSStocks = response.body()

                    val listStock: RecyclerView = root.findViewById(R.id.list_stock)

                    val layoutManager = LinearLayoutManager(root.context)
                    listStock.layoutManager = layoutManager

                    // Settings the list of values
                    stocksAdapter = returnWSStocks?.let { StocksAdapter(it) }
                    listStock.adapter = stocksAdapter

                    // Notify the view to say the list changed (doesn't refresh without this)
                    stocksAdapter?.notifyDataSetChanged()

                }
            }

            override fun onFailure(call: Call<MutableList<ReturnWSStock>>, t: Throwable)
            {
            }
        })
    }
}