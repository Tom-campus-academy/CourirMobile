package com.formationandroid.courir

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.formationandroid.courir.ws.RetrofitSingleton
import com.formationandroid.courir.ws.ReturnWSModel
import com.formationandroid.courir.ws.ReturnWSStock
import com.formationandroid.courir.ws.WSInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val serviceRetrofit = RetrofitSingleton.retrofit.create(WSInterface::class.java)

class ModelsAdapter(private var models: MutableList<ReturnWSModel>) :

    RecyclerView.Adapter<ModelsAdapter.ModelViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val viewCourse = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_model, parent, false)
        return ModelViewHolder(viewCourse)
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        holder.textName.text = models[position].Name;
        holder.textIdentificationNumber.text = models[position].IdentificationNumber;
        holder.textBrand.text = holder.itemView.resources.getStringArray(R.array.brand)[models[position].Brand];
    }

    fun removeItem(position: Int, it: View) {
        val model = models[position];
        this.callDeleteWebService(serviceRetrofit.deleteModel(model, "http://192.168.1.5:5000/V1/Courir/Models/" + model.Id), position)
    }

    override fun getItemCount(): Int = models.size

    inner class ModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textName: TextView = itemView.findViewById(R.id.name);
        var textBrand: TextView = itemView.findViewById(R.id.brand);
        var textIdentificationNumber: TextView = itemView.findViewById(R.id.identification_number);
        var deleteButton: ImageButton = itemView.findViewById(R.id.deleteItemCountry);

        init {
            textName.setOnClickListener(){
                val intent = Intent(it.context, ModelActivity::class.java)
                intent.putExtra("Brand", models[adapterPosition].Brand);
                intent.putExtra("Id", models[adapterPosition].Id);
                intent.putExtra("IdentificationNumber", models[adapterPosition].IdentificationNumber);
                intent.putExtra("Name", models[adapterPosition].Name);
                it.context.startActivity(intent)
            }
            textBrand.setOnClickListener(){
                val intent = Intent(it.context, ModelActivity::class.java)
                intent.putExtra("Brand", models[adapterPosition].Brand);
                intent.putExtra("Id", models[adapterPosition].Id);
                intent.putExtra("IdentificationNumber", models[adapterPosition].IdentificationNumber);
                intent.putExtra("Name", models[adapterPosition].Name);
                it.context.startActivity(intent)
            }
            textIdentificationNumber.setOnClickListener(){
                val intent = Intent(it.context, ModelActivity::class.java)
                intent.putExtra("Brand", models[adapterPosition].Brand);
                intent.putExtra("Id", models[adapterPosition].Id);
                intent.putExtra("IdentificationNumber", models[adapterPosition].IdentificationNumber);
                intent.putExtra("Name", models[adapterPosition].Name);
                it.context.startActivity(intent)
            }

            deleteButton.setOnClickListener() {
                removeItem(adapterPosition, it);
            }
        }
    }

    private fun callDeleteWebService(call: Call<ReturnWSModel>, position: Int) {
        call.enqueue(object : Callback<ReturnWSModel>
        {
            override fun onResponse(
                call: Call<ReturnWSModel>,
                response: Response<ReturnWSModel>
            ) {
                // If the http request is successful
                if (response.isSuccessful)
                {
                    models.removeAt(position);
                    notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ReturnWSModel>, t: Throwable)
            {
            }
        })
    }
}