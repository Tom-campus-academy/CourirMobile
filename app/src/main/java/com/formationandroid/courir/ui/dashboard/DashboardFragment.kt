package com.formationandroid.courir.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.formationandroid.courir.ModelActivity
import com.formationandroid.courir.ModelsAdapter
import com.formationandroid.courir.R
import com.formationandroid.courir.StocksAdapter
import com.formationandroid.courir.ui.home.HomeViewModel
import com.formationandroid.courir.ws.RetrofitSingleton
import com.formationandroid.courir.ws.ReturnWSModel
import com.formationandroid.courir.ws.ReturnWSStock
import com.formationandroid.courir.ws.WSInterface
import kotlinx.android.synthetic.main.fragment_dashboard.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private val serviceRetrofit = RetrofitSingleton.retrofit.create(WSInterface::class.java)
    private var modelsAdapter: ModelsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val models: RecyclerView = root.findViewById(R.id.list_model)

        this.callingWebService(serviceRetrofit.getModels(), root)
        return root
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

                    val listModel: RecyclerView = root.findViewById(R.id.list_model)

                    val layoutManager = LinearLayoutManager(root.context)
                    listModel.layoutManager = layoutManager

                    // Settings the list of values
                    modelsAdapter = returnWSModel?.let { ModelsAdapter(it) }
                    listModel.adapter = modelsAdapter

                    // Notify the view to say the list changed (doesn't refresh without this)
                    modelsAdapter?.notifyDataSetChanged()

                }
            }

            override fun onFailure(call: Call<MutableList<ReturnWSModel>>, t: Throwable)
            {
            }
        })
    }
}