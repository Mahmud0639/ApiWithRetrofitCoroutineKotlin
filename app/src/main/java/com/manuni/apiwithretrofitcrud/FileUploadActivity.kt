package com.manuni.apiwithretrofitcrud

import android.annotation.SuppressLint
import android.app.Activity
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.dhaval2404.imagepicker.ImagePicker
import com.manuni.apiwithretrofitcrud.adapters.FileUploadsAdapter
import com.manuni.apiwithretrofitcrud.databinding.ActivityFileUploadBinding
import com.manuni.apiwithretrofitcrud.models.fileupload.FileUpload
import com.manuni.apiwithretrofitcrud.models.fileupload.FileUploadModel
import com.manuni.apiwithretrofitcrud.networkservices.ProgressRequestBody
import com.manuni.apiwithretrofitcrud.networkservices.RetrofitClient
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.io.File

class FileUploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFileUploadBinding
    private val items: ArrayList<FileUploadModel> = arrayListOf()
    private val adapter: FileUploadsAdapter = FileUploadsAdapter(items)
    private var mProfileUri:String? = null


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFileUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter = adapter

        getFileUploadData()

        binding.progressBar.visibility = View.GONE
        binding.uploadingTxt.visibility = View.GONE

        binding.addPhoto.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(512, 512)//for profile photo we can use 512x512
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }

        binding.uploadBtn.setOnClickListener {
            val name = binding.etUserName.text.toString()
            val phTitle = binding.etPhotoTitle.text.toString()
            if (mProfileUri == null){
                Toast.makeText(this,"Choose a photo",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            userDataUploadWithImage(mProfileUri,name,phTitle){ res ->
               // println("Resp: ${res.result}")
                runOnUiThread {
                    Toast.makeText(this,"Result: ${res.result}",Toast.LENGTH_SHORT).show()
                    //now we want to refresh our recycler view again
                    getFileUploadData()
                }
            }
        }

    }

    private fun getFileUploadData() {
        lifecycleScope.launch {
            try {
                val resp: List<FileUploadModel> = RetrofitClient.retrofit.getUploadedImageData()
                println("FileResponses: $resp")
                items.clear()
                items.addAll(resp)
                adapter.notifyDataSetChanged()
            } catch (e: Exception) {
                e.printStackTrace()
            }


        }
    }

    private fun userDataUploadWithImage(mFileUrl:String?,mName:String,mPhotoTitle:String,success: (FileUpload) -> Unit){

        binding.progressBar.visibility = View.VISIBLE
        binding.uploadingTxt.visibility = View.VISIBLE

        val requestBody = MultipartBody.Builder().apply {
            setType(MultipartBody.FORM)
            addFormDataPart("name",mName)
            addFormDataPart("photoTitle",mPhotoTitle)

//            val profilePicFile = mFileUrl?.let { File(it) }
            val profilePicFile = mFileUrl?.let { File(it) }

            if (profilePicFile != null) {
                addPart(MultipartBody.Part.createFormData("my_profile_pic",profilePicFile.name,ProgressRequestBody(profilePicFile,object :ProgressRequestBody.UploadCallBack{
                    override fun onProgressUpdate(percentage: Int) {
                        runOnUiThread {
                            binding.progressBar.progress = percentage
                            binding.uploadingTxt.text = "Progress: ${percentage}%"
                            Log.d("MyTag", "onProgressUpdate: ${percentage}")
                        }
                    }

                    override fun onError() {
                        runOnUiThread {

                        }
                    }

                    override fun onFinish() {
                        runOnUiThread {
                            binding.progressBar.visibility = View.GONE
                            binding.uploadingTxt.visibility = View.GONE

                            binding.etUserName.text?.clear()
                            binding.etPhotoTitle.text?.clear()

                            mProfileUri = null
                            binding.ivUser.setImageResource(R.drawable.baseline_image_24)
                        }
                    }
                })))
            }
        }.build()

        lifecycleScope.launch {
            try {
                val res: FileUpload = RetrofitClient.retrofit.uploadPhotoData(requestBody.parts)
                println("Response: $res")
                success(res)
            }catch (e:Exception){
                e.printStackTrace()
            }

        }
    }


   /* @RequiresApi(Build.VERSION_CODES.O)
    private val startForProfileImageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!

                mProfileUri = getRealPathFromUri(fileUri)
                binding.ivUser.setImageURI(fileUri)
                //imgProfile.setImageURI(fileUri)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                //Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getRealPathFromUri(contentUri:Uri):String?{
        var result:String? = null
        val cursor = contentResolver.query(contentUri,null,null,null,null)
        if (cursor==null){
            contentUri.path
        }else{
            cursor.moveToFirst()
           val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
           result = cursor.getString(idx)
            cursor.close()
        }

    return result
    }*/

    @RequiresApi(Build.VERSION_CODES.O)
    private val startForProfileImageResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        val resultCode = result.resultCode
        val data = result.data

        if (resultCode == Activity.RESULT_OK) {
            // Image Uri will not be null for RESULT_OK
            val fileUri = data?.data
            //this below code portion was not given in the above same function for that we faced error
            if (fileUri != null) {
                mProfileUri = getRealPathFromUri(fileUri)
                if (mProfileUri != null) {
                    binding.ivUser.setImageURI(fileUri)
                } else {
                    Toast.makeText(this, "Failed to get image path", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getRealPathFromUri(contentUri: Uri): String? {
        var result: String? = null
        val cursor = contentResolver.query(contentUri, null, null, null, null)
        if (cursor == null) {
            result = contentUri.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }

        // Debug the result
        Log.d("FileUploadActivity", "Real Path: $result")
        return result
    }


}