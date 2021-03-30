package com.formationandroid.courir

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.formationandroid.courir.ws.RetrofitSingleton
import com.formationandroid.courir.ws.ReturnWSStock
import com.formationandroid.courir.ws.ShoeSize
import com.formationandroid.courir.ws.WSInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

private val serviceRetrofit = RetrofitSingleton.retrofit.create(WSInterface::class.java)

class StocksAdapter(private var stocks: MutableList<ReturnWSStock>) :

    RecyclerView.Adapter<StocksAdapter.StockViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val viewCourse = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_stock, parent, false)
        return StockViewHolder(viewCourse)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        holder.textModelName.text = stocks[position].Model.Name;
        holder.textPrice.text = stocks[position].Price.toString();
        holder.textQuantity.text = stocks[position].Quantity.toString();
        holder.textShoeSize.text = holder.itemView.resources.getStringArray(R.array.shoeSize)[stocks[position].ShoeSize];
    }

    fun removeItem(position: Int, it: View) {
        val stock = stocks[position];
        this.callDeleteWebService(serviceRetrofit.deleteStock(stock, "http://192.168.1.5:5000/V1/Courir/Stocks/" + stock.Id), position)
    }

    override fun getItemCount(): Int = stocks.size

    inner class StockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textModelName: TextView = itemView.findViewById(R.id.model_name);
        var textPrice: TextView = itemView.findViewById(R.id.price);
        var textQuantity: TextView = itemView.findViewById(R.id.quantity);
        var textShoeSize: TextView = itemView.findViewById(R.id.shoe_size);
        var deleteButton: ImageButton = itemView.findViewById(R.id.deleteItemCountry);

        init {
            textModelName.setOnClickListener(){
                val intent = Intent(it.context, StockActivity::class.java)
                intent.putExtra("Price", stocks[adapterPosition].Price);
                intent.putExtra("Id", stocks[adapterPosition].Id);
                intent.putExtra("Quantity", stocks[adapterPosition].Quantity);
                intent.putExtra("ShoeSize", stocks[adapterPosition].ShoeSize);
                intent.putExtra("IdModel", stocks[adapterPosition].IdModel);
                it.context.startActivity(intent)
            }
            textPrice.setOnClickListener(){
                val intent = Intent(it.context, StockActivity::class.java)
                intent.putExtra("Price", stocks[adapterPosition].Price);
                intent.putExtra("Id", stocks[adapterPosition].Id);
                intent.putExtra("Quantity", stocks[adapterPosition].Quantity);
                intent.putExtra("ShoeSize", stocks[adapterPosition].ShoeSize);
                intent.putExtra("IdModel", stocks[adapterPosition].IdModel);
                it.context.startActivity(intent)
            }
            textQuantity.setOnClickListener(){
                val intent = Intent(it.context, StockActivity::class.java)
                intent.putExtra("Price", stocks[adapterPosition].Price);
                intent.putExtra("Id", stocks[adapterPosition].Id);
                intent.putExtra("Quantity", stocks[adapterPosition].Quantity);
                intent.putExtra("ShoeSize", stocks[adapterPosition].ShoeSize);
                intent.putExtra("IdModel", stocks[adapterPosition].IdModel);
                it.context.startActivity(intent)
            }
            textShoeSize.setOnClickListener(){
                val intent = Intent(it.context, StockActivity::class.java)
                intent.putExtra("Price", stocks[adapterPosition].Price);
                intent.putExtra("Id", stocks[adapterPosition].Id);
                intent.putExtra("Quantity", stocks[adapterPosition].Quantity);
                intent.putExtra("ShoeSize", stocks[adapterPosition].ShoeSize);
                intent.putExtra("IdModel", stocks[adapterPosition].IdModel);
                it.context.startActivity(intent)
            }
            deleteButton.setOnClickListener() {
                removeItem(adapterPosition, it);
            }
        }
    }

    private fun callDeleteWebService(call: Call<ReturnWSStock>, position: Int) {
        call.enqueue(object : Callback<ReturnWSStock>
        {
            override fun onResponse(
                call: Call<ReturnWSStock>,
                response: Response<ReturnWSStock>
            ) {
                // If the http request is successful
                if (response.isSuccessful)
                {
                    stocks.removeAt(position);
                    notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ReturnWSStock>, t: Throwable)
            {
            }
        })
    }
}