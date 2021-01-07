package app.grocerystore.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.grocerystore.R
import app.grocerystore.adapters.GroceryAdapter
import app.grocerystore.model.GroceryModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.lang.reflect.Type


class GroceryFragment : Fragment() {
    private lateinit var groceryAdapter: GroceryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.grocery_fragment, container, false)
        /*viewPager = view.findViewById(R.id.viewpager_main)
        tabs = view.findViewById(R.id.tabs_main)

        val fragmentAdapter = MyPagerAdapter(childFragmentManager)
        viewPager.adapter = fragmentAdapter
        tabs.setupWithViewPager(viewPager)*/


        fun getAssetJsonData(context: Context): String? {
            val json: String
            try {
                val inputStream = context.getAssets().open("jsonDataGroceryStore.json")
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.use { it.read(buffer) }
                json = String(buffer)
            } catch (ioException: IOException) {
                ioException.printStackTrace()
                return null
            }
            // print the data
            Log.i("data", json)
            return json
        }


        val data = activity?.let { getAssetJsonData(it) }
        val type: Type = object : TypeToken<List<GroceryModel.GroceryModelItem?>?>() {}.type
        val properties: MutableList<GroceryModel.GroceryModelItem> =
            Gson().fromJson<MutableList<GroceryModel.GroceryModelItem>>(data, type)

        val recyclerView: RecyclerView = view.findViewById(R.id.grocery_rv)
        groceryAdapter = GroceryAdapter(properties)
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = groceryAdapter

        val grocery_search: SearchView = view.findViewById(R.id.grocery_search)


        grocery_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                Log.d("onQueryTextChange", "query: " + query)
                groceryAdapter?.filter?.filter(query)
                return true
            }
        })

        return view
    }
}