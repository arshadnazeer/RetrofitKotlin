package com.arsh.retroappkot

import retrofit2.Response
import retrofit2.http.*

interface CommentService {

    // specify the end point
    @GET("/comments")
    suspend fun getComments() : Response<Comments>

    @GET("/comments")
    suspend fun getRequiredComments(@Query("postId") postId:Int) : Response<Comments>

    @GET("/comments/{id}")
    suspend fun getComment(@Path("id") id:Int) :Response<CommentsItem>

    @POST("/comments")
    suspend fun postComment(@Body commentsItem: CommentsItem) : Response<CommentsItem>
}