package com.example.ocr.GalleryFragment



import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.textract.AmazonTextractClient
import com.example.ocr.databinding.FragmentGalleryBinding
import androidx.lifecycle.lifecycleScope
import com.amazonaws.services.textract.model.DetectDocumentTextRequest
import com.amazonaws.services.textract.model.Document
import com.bumptech.glide.Glide
import com.example.ocr.ChatScreen.ChatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.nio.ByteBuffer

class GalleryFragment : Fragment() {

    private lateinit var binding: FragmentGalleryBinding
    private val credential=BasicAWSCredentials("your_access_key","your_secret_key")

    private val texttract = AmazonTextractClient(credential)

    val galleryLauncher=registerForActivityResult(ActivityResultContracts.GetContent()){
        lifecycleScope.launch(Dispatchers.IO) {
            val gallery_uri=it
            try {

                withContext(Dispatchers.Main) {
                    Glide.with(requireContext())
                        .load(gallery_uri)
                        .into(binding.imageView)
                }

                val inputStream=requireContext().contentResolver.openInputStream(gallery_uri!!)
                val imageBytes= ByteBuffer.wrap(inputStream!!.readBytes())

                val request=DetectDocumentTextRequest().withDocument(Document().withBytes(imageBytes))

                println(request)
                val response=texttract.detectDocumentText(request)

                val intent=Intent(requireContext(),ChatActivity(response.toString())::class.java)
                startActivity(intent)




            }catch (e:Exception){
                print(e.cause)
                print(e.message)
                print(e.stackTrace)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=FragmentGalleryBinding.inflate(layoutInflater)


        binding.galleryButton.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        binding.cameraButton.setOnClickListener {

        }


        return binding.root
    }



}