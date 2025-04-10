package com.manuni.apiwithretrofitcrud.models.fileupload

import com.google.gson.annotations.SerializedName


data class FileUpload (

  @SerializedName("result"     ) var result     : String? = null,
  @SerializedName("name"       ) var name       : String? = null,
  @SerializedName("photoTitle" ) var photoTitle : String? = null,
  @SerializedName("photoUrl"   ) var photoUrl   : String? = null

)