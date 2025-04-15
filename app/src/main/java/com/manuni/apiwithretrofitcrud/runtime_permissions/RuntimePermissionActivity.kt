package com.manuni.apiwithretrofitcrud.runtime_permissions

import android.Manifest
import android.content.DialogInterface
import android.content.DialogInterface.OnClickListener
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.manuni.apiwithretrofitcrud.R
import com.manuni.apiwithretrofitcrud.databinding.ActivityRuntimePermissionBinding

class RuntimePermissionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRuntimePermissionBinding
    private val PERMISSION_REQ_CODE = 1110
    private val myPermission = arrayOf(Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRuntimePermissionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.permissionBtn.setOnClickListener {
            requestPermission()
        }

    } // onCreate

    private fun requestPermission(){
        if (isAllGranted()){
            // code continue
            Toast.makeText(this,"All granted",Toast.LENGTH_SHORT).show()
        }else if (shouldShowRationale()){
            AlertDialog.Builder(this).apply {
                setTitle("Alert!")
                setMessage("You need to allow permission to use this feature")
                setCancelable(false)
                setNegativeButton("Cancel",object : OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        dialog?.dismiss()
                    }
                })
                setPositiveButton("Allow",object :OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        ActivityCompat.requestPermissions(this@RuntimePermissionActivity,myPermission,PERMISSION_REQ_CODE)
                    }
                })
            }.show()
        }else{
            ActivityCompat.requestPermissions(this@RuntimePermissionActivity,myPermission,PERMISSION_REQ_CODE)
        }
    } // requestPermission

    private fun isAllGranted():Boolean{
        val p = myPermission.filter { ContextCompat.checkSelfPermission(this,it)!=PackageManager.PERMISSION_GRANTED }
        return p.isEmpty()//if p is empty that means all permissions are granted, if any one of them or both are granted then p will get value then the p will not be empty
    } // isAllGranted

    //if not one of them accepted by user then should show the rationale
    private fun shouldShowRationale():Boolean{
        val p = myPermission.filter { ActivityCompat.shouldShowRequestPermissionRationale(this,it) }
        return p.isNotEmpty()//that means p is obtained permission that is not accepted so we need to show the rationale that the user
    } // shouldShowRationale

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQ_CODE){
            if (grantResults.contains(PackageManager.PERMISSION_DENIED)){
                //user denied one or more permissions
                AlertDialog.Builder(this).apply {
                    setTitle("Alert!")
                    setMessage("Please go to settings and allow permissions")
                    setCancelable(false)
                    setNegativeButton("Cancel",object : OnClickListener{
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            dialog?.dismiss()
                        }
                    })
                    setPositiveButton("Settings",object :OnClickListener{
                        override fun onClick(dialog: DialogInterface?, which: Int) {
                            gotoSettings()
                        }
                    })
                }.show()
            }else{
                requestPermission()
            }
        }
    }
    //this function is for the settings option
    private fun gotoSettings(){
        val intent = Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package",packageName,null)
        }
        startActivity(intent)
    }
}