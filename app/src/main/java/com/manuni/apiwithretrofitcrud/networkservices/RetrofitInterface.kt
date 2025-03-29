package com.manuni.apiwithretrofitcrud.networkservices

import com.manuni.apiwithretrofitcrud.models.Location
import com.manuni.apiwithretrofitcrud.models.ResultModel
import com.manuni.apiwithretrofitcrud.models.UserModel
import com.manuni.apiwithretrofitcrud.models.locations.LocationModel
import okhttp3.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

@JvmSuppressWildcards
interface RetrofitInterface {

    @POST(AllApi.USER)
    suspend fun saveUser(@Body body:Map<String,Any?>) : retrofit2.Response<ResultModel>//return type
    //kon method a --kon endpoint a --body akare--map type akare--response er jonno data class

    //for the end point: https://touhidapps.com/api/v1/user.php
    /*@GET(AllApi.USER)
    suspend fun getUserData(): List<UserModel>*/


    @GET(AllApi.USER)
    suspend fun getUserData(@Query("pageNo")pageNo:Int,@Query("perPageData")perPageData:Int): List<UserModel>

    @PUT(AllApi.USER)
    suspend fun updateUser(@Body body:Map<String,Any?>) : retrofit2.Response<ResultModel>

   /* If your API requires a body, use @HTTP with hasBody = true. ✅
    If your API only needs an ID as a query parameter, use @DELETE with @Query.*/

    /*@DELETE(AllApi.USER)
    suspend fun deleteUser(@Query("id") userId: Int): retrofit2.Response<ResultModel>*/


    @HTTP(method = "DELETE", path = AllApi.USER, hasBody = true)
    suspend fun deleteUser(@Body body: Map<String, Any?>):retrofit2.Response<ResultModel>

    //getting locations
    @GET(AllApi.LOCATION)
    suspend fun getLocations():List<LocationModel>
    //suspend fun getLocations():retrofit2.Response<List<Location>>
}