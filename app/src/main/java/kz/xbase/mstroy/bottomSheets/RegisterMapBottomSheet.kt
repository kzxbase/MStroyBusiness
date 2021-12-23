package kz.xbase.mstroy.bottomSheets

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import kotlinx.android.synthetic.main.bottom_sheet_register_map.*
import kz.xbase.mstroy.R
import kz.xbase.mstroy.activity.utils.showMessage
import kz.xbase.mstroy.activity.utils.showToast
import kz.xbase.mstroy.utils.OnLocationPermission

class RegisterMapBottomSheet : Fragment(),OnLocationPermission {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var location : Location?=null
    private var place : Point?=null
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
        getLocationPermission()

    }

    override fun onStop() {
        super.onStop()
        mapview.onStop()
        MapKitFactory.getInstance().onStop()
    }

    override fun onStart() {
        super.onStart()
        mapview.onStart();
        MapKitFactory.getInstance().onStart();
    }
    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    this.location = location
                    if(mapview!=null && location!=null){
                        mapview.map.move(CameraPosition(Point(location.latitude,location.longitude),10f,0f,0f))
                        place = Point(location.latitude,location.longitude)
                        if(place!=null){
                            mapview.map.mapObjects.addPlacemark(place!!)
                        }
                    }
                }
        } else {
            mapview.showMessage("Необходимо дать разрешение на геолокацию")
        }
    }


    companion object {
        fun newInstance(): RegisterMapBottomSheet {
            return RegisterMapBottomSheet()
        }
    }

    override fun onLocationPermissionGranted() {
        showToast("asdx")
    }
}