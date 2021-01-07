package app.grocerystore.fragments

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.grocerystore.R
import app.grocerystore.model.GroceryModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.lang.reflect.Type


class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    var latlngList = ArrayList<GroceryModel.GroceryModelItem>()

    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val permissionCode = 101
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.map_fragment, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

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

        latlngList = properties as ArrayList<GroceryModel.GroceryModelItem>
        for (item in latlngList) {
            println("XXXXXDATA: ${item.latitude}")
        }

        // fetchLocation()
        return view
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!
        /*  val latlng = LatLng(currentLocation.latitude, currentLocation.longitude)
          mMap.addMarker(MarkerOptions().position(latlng).title("My Location Here"))
          mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng))*/
        for (item in latlngList) {
            val latlng = LatLng(item.latitude, item.longitude)
            mMap.addMarker(MarkerOptions().position(latlng).title(item.storeName))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng))
        }
    }

    /* private fun fetchLocation() {
         if (ActivityCompat.checkSelfPermission(
                 activity!!, Manifest.permission.ACCESS_FINE_LOCATION) !=
             PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                 activity!!, Manifest.permission.ACCESS_COARSE_LOCATION) !=
             PackageManager.PERMISSION_GRANTED) {
             ActivityCompat.requestPermissions(
                 activity!!,
                 arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), permissionCode)
             return
         }
         val task = fusedLocationProviderClient.lastLocation
         task.addOnSuccessListener { Location->
             if (Location != null) {
                 currentLocation = Location
                 Toast.makeText(activity, currentLocation.latitude.toString() + "" +
                         currentLocation.longitude, Toast.LENGTH_SHORT).show()
                *//* val supportMapFragment = (supportFragmentManager.findFragmentById(R.id.myMap) as
                        SupportMapFragment?)!!
                supportMapFragment.getMapAsync(this@MainActivity)*//*
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>,
                                            grantResults: IntArray) {
        when (requestCode) {
            permissionCode -> if (grantResults.isNotEmpty() && grantResults[0] ==
            PackageManager.PERMISSION_GRANTED) {
            fetchLocation()
        }
        }
    }*/
}