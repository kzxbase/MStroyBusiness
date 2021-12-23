package kz.xbase.mstroy.bottomSheets

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.location.FilteringMode
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.location.LocationManager
import com.yandex.mapkit.location.LocationStatus
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CameraUpdateReason
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.search.*
import com.yandex.runtime.Error
import com.yandex.runtime.image.ImageProvider
import com.yandex.runtime.ui_view.ViewProvider
import kotlinx.android.synthetic.main.bottom_sheet_register_map.*
import kotlinx.android.synthetic.main.fragment_register_business.*
import kz.xbase.mstroy.R
import kz.xbase.mstroy.activity.utils.showMessage
import kz.xbase.mstroy.activity.utils.showToast
import kz.xbase.mstroy.fragments.login.RegisterBusinessFragment
import kz.xbase.mstroy.utils.GpsEnabled
import kz.xbase.mstroy.utils.OnLocationPermission

class RegisterMapBottomSheet : Fragment(),OnLocationPermission,GpsEnabled,CameraListener {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var location  = Location("")
    private var place : Point?=null
    private lateinit var image:View
    private lateinit var locationManager:LocationManager
    private lateinit var locationListener: LocationListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        return inflater.inflate(R.layout.bottom_sheet_register_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userLocationLayer = MapKitFactory.getInstance().createUserLocationLayer(mapview.mapWindow);
        userLocationLayer.isVisible=true
        userLocationLayer.isHeadingEnabled=true
        image = View(requireContext()).apply {
            background = requireContext().getDrawable(R.drawable.ic_pinned)
        }
        mapview.map.addCameraListener (this)
        getLocationPermission()
        cv_close.setOnClickListener {
            requireActivity().onBackPressed()
        }
        btn_set_location.setOnClickListener {
            RegisterBusinessFragment.location = location
            RegisterBusinessFragment.locationName = tv_adress.text.toString()
            requireActivity().onBackPressed()
        }
    }

    override fun onStop() {
        super.onStop()
//        if(this::locationListener.isInitialized && this::locationManager.isInitialized){
//            locationManager.unsubscribe(locationListener)
//        }
        mapview.onStop()
        MapKitFactory.getInstance().onStop()
    }


    override fun onStart() {
        super.onStart()
        mapview.onStart();
        MapKitFactory.getInstance().onStart();
    }
    private fun checkGps(){

        val builder = LocationSettingsRequest.Builder()
        val client: SettingsClient = LocationServices.getSettingsClient(requireContext())
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener { locationSettingsResponse ->
            if(locationSettingsResponse.locationSettingsStates.isGpsUsable){
                requestLocation()
            }

        }
        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException){
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    exception.startResolutionForResult(requireActivity(),
                        2)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }
    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            checkGps()
        } else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1)
        }
    }
    @SuppressLint("MissingPermission")
    private fun requestLocation(){
        locationManager = MapKitFactory.getInstance().createLocationManager()
        locationListener = object : LocationListener {
            override fun onLocationUpdated(location: com.yandex.mapkit.location.Location) {
                if (mapview != null && place == null) {
                    mapview.map.move(
                        CameraPosition(
                            Point(
                                location.position.latitude,
                                location.position.longitude
                            ), 15f, 0f, 0f
                        )
                    )
                    place = Point(location.position.latitude, location.position.longitude)
                    if (place != null) {
                        mapview.map.mapObjects.addPlacemark(place!!, ViewProvider(image))
                    }
                }
            }

            override fun onLocationStatusUpdated(p0: LocationStatus) {

            }
        }
        locationManager.subscribeForLocationUpdates(0.0,0,0.0,false,FilteringMode.OFF,locationListener)
    }


    companion object {
        fun newInstance(): RegisterMapBottomSheet {
            return RegisterMapBottomSheet()
        }
    }

    override fun onLocationPermissionGranted() {
        checkGps()
    }

    override fun gpsEnabled() {
        requestLocation()
    }

    override fun onCameraPositionChanged(
        p0: Map,
        p1: CameraPosition,
        p2: CameraUpdateReason,
        p3: Boolean
    ) {

            mapview.map.mapObjects.clear()
            mapview.map.mapObjects.addPlacemark(p1.target, ViewProvider(image,true))
            location.latitude=p1.target.latitude
            location.longitude=p1.target.longitude
            SearchFactory.getInstance().createSearchManager(SearchManagerType.COMBINED).submit(p1.target,16,
                SearchOptions(),object : Session.SearchListener {
                    override fun onSearchResponse(p0: Response) {
                        tv_adress.text = if(p0.collection.children.size>0){
                            p0.collection.children[0].obj?.name
                        }else{""}
                    }

                    override fun onSearchError(p0: Error) {
                        TODO("Not yet implemented")
                    }

                }
            )
    }
}