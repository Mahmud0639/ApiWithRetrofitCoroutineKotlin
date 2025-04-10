package com.manuni.apiwithretrofitcrud.models.fileupload

import com.google.gson.annotations.SerializedName


data class FileUploadModel (

  @SerializedName("id"       ) var id       : String? = null,
  @SerializedName("name"     ) var name     : String? = null,
  @SerializedName("title"    ) var title    : String? = null,
  @SerializedName("imageUrl" ) var imageUrl : String? = null

)