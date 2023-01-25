package tn.esprit.apppermission

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.pm.PackageManager
import android.os.Bundle
import android.webkit.PermissionRequest
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.security.Permissions
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    private lateinit var permissionLauncher : ActivityResultLauncher<Array<String>>
    private var isReadPermissionGranded = false
    private var isLocationPermissionGranded = false
    private var isRecordPermissionGranded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ permissions ->
            isReadPermissionGranded = permissions[android.Manifest.permission.READ_EXTERNAL_STORAGE] ?: isReadPermissionGranded
            isLocationPermissionGranded = permissions[android.Manifest.permission.ACCESS_FINE_LOCATION] ?: isLocationPermissionGranded
            isRecordPermissionGranded = permissions[android.Manifest.permission.READ_EXTERNAL_STORAGE] ?: isRecordPermissionGranded
        }
        requestPermission()
    }

    private fun requestPermission(){
        isReadPermissionGranded =  ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )  ==  PackageManager.PERMISSION_GRANTED
        isRecordPermissionGranded =  ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )  ==  PackageManager.PERMISSION_GRANTED
        isReadPermissionGranded =  ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.RECORD_AUDIO
        )  ==  PackageManager.PERMISSION_GRANTED
        val permissionRequest : MutableList<String> = ArrayList()

        if (!isReadPermissionGranded){
            permissionRequest.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (!isLocationPermissionGranded){
            permissionRequest.add(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (!isRecordPermissionGranded){
            permissionRequest.add(android.Manifest.permission.RECORD_AUDIO)
        }
        if (permissionRequest.isNotEmpty()){
            permissionLauncher.launch(permissionRequest.toTypedArray())
        }
    }
}