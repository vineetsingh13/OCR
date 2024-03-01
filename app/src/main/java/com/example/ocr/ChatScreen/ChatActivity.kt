package com.example.ocr.ChatScreen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ocr.MainActivity
import com.example.ocr.databinding.ActivityChatBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.*
import kotlin.system.measureTimeMillis

class ChatActivity : AppCompatActivity() {

    lateinit var binding: ActivityChatBinding
    private lateinit var madapter: ChatAdapter
    private val messages: MutableList<responseData> = mutableListOf()

    private val chats: MutableList<messageData> = mutableListOf()

    private val messageList: MutableList<messageMap> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        chats.add(messageData(role = "system", content = "You are a helpful assistant."))

        val value = intent.getStringExtra("extractedText")
        if (!value.isNullOrEmpty()) {

            lifecycleScope.launch(Dispatchers.IO) {
                val time= measureTimeMillis {
                    async {
                        getResponse(value)
                    }
                }
            }

        }

        binding.backButton.setOnClickListener {
            val intent=Intent(this@ChatActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }


        binding.sendButton.setOnClickListener {


            if(binding.editText.text!!.isNotEmpty()){


                binding.sendButton.isClickable=false

                lifecycleScope.launch(Dispatchers.IO) {
                    try {

                        val time= measureTimeMillis {
                            async {
                                getResponse(binding.editText.text.toString())
                            }
                        }

                    }finally {

                        withContext(Dispatchers.Main) {
                            binding.sendButton.isClickable = true
                        }
                    }
                }
            }else{

                Toast.makeText(this,"enter text",Toast.LENGTH_SHORT).show()
            }

            hideKeyboard()
        }

        binding.chatRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        madapter = ChatAdapter(messageList)
        binding.chatRecyclerView.adapter = madapter

    }


    private fun hideKeyboard() {

        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            currentFocus?.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
        binding.editText.text!!.clear()
    }


    private fun getResponse(query: String){

        runOnUiThread {
            binding.progressbar.visibility= View.VISIBLE
        }
        val service=RetrofitService.buildService(APIRetrofitInterface::class.java)

//        val request= requestData(
//            model = "gpt-3.5-turbo",
//            messages = listOf(
//                messageData(role = "system", content = "You are a helpful assistant."),
//                messageData(role = "user", content = query)
//            )
//        )

        val request= requestData(
            model = "gpt-3.5-turbo",
            messages = chats + messageData(role = "user", content = query)
        )

        val call=service.respone(request)


        call.enqueue(object : Callback<responseData>{
            override fun onResponse(call: Call<responseData>, response: Response<responseData>) {

                if(response.code()==200){
                    println(response.body().toString())

                    //binding.response.text= response.body()!!.choices[0].message.content
                    messages.add(response.body()!!)

                    chats.add(response.body()!!.choices[0].message)

                    val messageMap=messageMap(query,response.body()!!.choices[0].message.content.toString())
                    messageList.add(messageMap)
                    madapter.notifyDataSetChanged()

                    runOnUiThread {
                        binding.progressbar.visibility=View.GONE
                    }

                }else{
                    print(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<responseData>, t: Throwable) {
                t.printStackTrace()
            }

        })

    }

    override fun onStop() {
        super.onStop()

        messageList.clear()
    }

//    private fun updateRecyclerView(msg:String) {
//        if (!::madapter.isInitialized) {
//            binding.chatRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
//            madapter = ChatAdapter(messages, msg)
//            binding.chatRecyclerView.adapter = madapter
//        } else {
//            madapter.notifyDataSetChanged()
//            madapter.userText=msg
//
//        }
//    }
}