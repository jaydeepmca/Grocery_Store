package app.grocerystore.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import app.grocerystore.R
import app.grocerystore.model.GroceryModel
import kotlinx.android.synthetic.main.grocery_item.view.*

internal class GroceryAdapter(private var groceryList: MutableList<GroceryModel.GroceryModelItem>) :
    RecyclerView.Adapter<GroceryAdapter.MyViewHolder>(), Filterable {

    var gorceryFilterList = ArrayList<GroceryModel.GroceryModelItem>()

    init {
        gorceryFilterList = groceryList as ArrayList<GroceryModel.GroceryModelItem>
    }

    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(model: GroceryModel.GroceryModelItem): Unit {
            itemView.mTvAdd1.text = model.addressLine1
            itemView.mTvAdd2.text = model.addressLine2
            itemView.mTvStore.text = model.storeName
            itemView.mButtonStatus.text = model.status
            itemView.mTvDistance.text = model.distance
            /*itemView.mTvAdd1.text = model.addressLine1
            itemView.mTvAdd1.text = model.addressLine1
            itemView.mTvAdd1.text = model.addressLine1*/
        }

        var mTvAdd1: TextView = view.findViewById(R.id.mTvAdd1)
        var mTvAdd2: TextView = view.findViewById(R.id.mTvAdd2)
        var mTvStore: TextView = view.findViewById(R.id.mTvStore)
        var mButtonStatus: Button = view.findViewById(R.id.mButtonStatus)
        var mTvDistance: TextView = view.findViewById(R.id.mTvDistance)
        var mImgVWhatsapp: ImageView = view.findViewById(R.id.mImgVWhatsapp)
        var mImgVMap: ImageView = view.findViewById(R.id.mImgVMap)
        var mLinearBorder: LinearLayout = view.findViewById(R.id.mLinearBorder)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.grocery_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val grocery = groceryList[position]
        (holder as MyViewHolder).bind(gorceryFilterList.get(position));
        /*holder.mTvAdd1.text = grocery.addressLine1
        holder.mTvAdd2.text = grocery.addressLine2
        holder.mTvStore.text = grocery.storeName*/
        if (grocery.status.equals("open")) {
            holder.mButtonStatus.setBackgroundResource(R.drawable.rounded_corner_green)
            holder.mLinearBorder.setBackgroundResource(R.color.green)
        } else if (grocery.status.equals("close")) {
            holder.mButtonStatus.setBackgroundResource(R.drawable.rounded_corner_blue)
            holder.mLinearBorder.setBackgroundResource(R.color.blue)
        } else if (grocery.status.equals("no")) {
            holder.mButtonStatus.setBackgroundResource(R.drawable.rounded_corner_gray)
            holder.mLinearBorder.setBackgroundResource(R.color.gray)
        }

        if (grocery.status.equals("no")) {
            holder.mButtonStatus.text = "No Status"
        } else if (grocery.status.equals("open")) {
            holder.mButtonStatus.text = "Open"
        } else if (grocery.status.equals("close")) {
            holder.mButtonStatus.text = "Close"
        }

        //holder.mTvDistance.text = grocery.distance
    }

    override fun getItemCount(): Int {
        return gorceryFilterList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    gorceryFilterList = groceryList as ArrayList<GroceryModel.GroceryModelItem>
                } else {
                    val resultList = ArrayList<GroceryModel.GroceryModelItem>()
                    for (row in groceryList) {
                        if (row.storeName.toLowerCase()
                                .contains(constraint.toString().toLowerCase())
                        ) {
                            resultList.add(row)
                        }
                    }
                    gorceryFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = gorceryFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                gorceryFilterList = results?.values as ArrayList<GroceryModel.GroceryModelItem>
                notifyDataSetChanged()
            }
        }
    }
}
