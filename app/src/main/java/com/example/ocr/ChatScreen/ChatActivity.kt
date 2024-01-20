package com.example.ocr.ChatScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.example.ocr.databinding.ActivityChatBinding
import retrofit2.*

class ChatActivity(ExtractedText: String) : AppCompatActivity() {

    lateinit var binding: ActivityChatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editText.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->

            if(actionId==EditorInfo.IME_ACTION_SEND){
                binding.response.text="Please wait...."

                if(binding.editText.text.toString().isNotEmpty()){
                    getResponse(binding.editText.toString())
                }else{

                    Toast.makeText(this,"enter text",Toast.LENGTH_SHORT).show()
                }
                return@OnEditorActionListener true
            }
            false
        })


    }


    private fun getResponse(query: String){

        val service=RetrofitService.buildService(APIRetrofitInterface::class.java)

        val request= requestData(
            model = "gpt-3.5-turbo",
            message = listOf(
                messages(role = "system", content = "You are a helpful assistant."),
                messages(role = "user", content = query)
            )
        )

        val call=service.respone(request)
        call.enqueue(object : Callback<responseData>{
            override fun onResponse(call: Call<responseData>, response: Response<responseData>) {

                if(response.code()==200){
                    binding.response.text= response.body()!!.choices[0].message.content
                }
            }

            override fun onFailure(call: Call<responseData>, t: Throwable) {
                t.printStackTrace()
            }

        })

    }
}