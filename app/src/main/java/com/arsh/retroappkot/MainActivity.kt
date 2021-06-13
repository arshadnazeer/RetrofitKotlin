package com.arsh.retroappkot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var retService: CommentService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retService = RetrofitInstance
            .getRetrofitInstance()
            .create(CommentService::class.java)

//        getAllcommentQueryParams()
//        getCommentPathParam()
        postCommentParam()
    }

    private fun postCommentParam(){
        val commentsItem = CommentsItem("This is Arshad","arsh@gmail.com",0,"Arshad",11)
        val responseLiveData : LiveData<Response<CommentsItem>> = liveData {
            val response = retService.postComment(commentsItem)
            emit(response)
        }

        responseLiveData.observe(this, Observer {
            val commentRecievedItem = it.body()
            val result = " " + "CommentBody: ${commentRecievedItem?.body}" + "\n"+
                    " " + "CommentEmail : ${commentRecievedItem?.email}" + "\n" +
                    " " + "Commentid : ${commentRecievedItem?.id}" + "\n" +
                    " " + "Commentname : ${commentRecievedItem?.name}" + "\n" +
                    " " + "CommentpostId : ${commentRecievedItem?.postId}" + "\n\n\n"
            text_view.text = result
        })

    }

    private fun getCommentPathParam() {

        val responseLiveData : LiveData<Response<CommentsItem>> = liveData {
            val response = retService.getComment(7)
            emit(response)
        }

        responseLiveData.observe(this, Observer {
            val commentItem = it.body()
            val result = " " + "CommentBody: ${commentItem?.body}" + "\n"+
                        " " + "CommentEmail : ${commentItem?.email}" + "\n" +
                        " " + "Commentid : ${commentItem?.id}" + "\n" +
                        " " + "Commentname : ${commentItem?.name}" + "\n" +
                        " " + "CommentpostId : ${commentItem?.postId}" + "\n\n\n"
            text_view.text = result
        })
    }

    private fun getAllcommentQueryParams() {
        val responseLiveData : LiveData<Response<Comments>> = liveData {
            val response = retService.getRequiredComments(4)
            emit(response)
        }
        responseLiveData.observe(this, Observer {
            val commentList = it.body()?.listIterator()
            if (commentList!=null){
                while (commentList.hasNext()){
                    val commentsItem = commentList.next()
                    val result = " " + "CommentBody: ${commentsItem.body}" + "\n"+
                                 " " + "CommentEmail : ${commentsItem.email}" + "\n" +
                                 " " + "Commentid : ${commentsItem.id}" + "\n" +
                                 " " + "Commentname : ${commentsItem.name}" + "\n" +
                                 " " + "CommentpostId : ${commentsItem.postId}" + "\n\n\n"
                    text_view.append(result)
                }
            }
        })
    }
}