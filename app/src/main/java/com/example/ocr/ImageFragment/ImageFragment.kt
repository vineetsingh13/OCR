package com.example.ocr.ImageFragment



import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.textract.AmazonTextractClient
import androidx.lifecycle.lifecycleScope
import com.amazonaws.services.textract.model.DetectDocumentTextRequest
import com.amazonaws.services.textract.model.Document
import com.bumptech.glide.Glide
import com.example.ocr.CameraActivity.CameraActivity
import com.example.ocr.ChatScreen.ChatActivity
import com.example.ocr.LoadingDialog
import com.example.ocr.databinding.FragmentGalleryBinding
import com.example.ocr.utils.az
import com.example.ocr.utils.az_secret

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.nio.ByteBuffer

class ImageFragment : Fragment() {

    private lateinit var binding: FragmentGalleryBinding

    private val credential=BasicAWSCredentials(az, az_secret)

    private val texttract = AmazonTextractClient(credential)


    val galleryLauncher=registerForActivityResult(ActivityResultContracts.GetContent()){
        val gallery_uri=it
        try {
            Glide.with(requireContext())
                .load(gallery_uri)
                .into(binding.imageView)


            if (gallery_uri != null) {
                extract(gallery_uri)
            }

        }catch (e:Exception){
            print(e.cause)
            print(e.message)
            print(e.stackTrace)
        }
    }

    val imageLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){


        if(it.resultCode== AppCompatActivity.RESULT_OK){

            val uri=it.data?.getStringExtra("image")?.toUri()


            binding.imageView2.visibility=View.GONE
            binding.addImageText.visibility=View.GONE
            binding.cameraButton.visibility=View.GONE
            binding.galleryButton.visibility=View.GONE
            binding.imageView.visibility=View.VISIBLE

            binding.imageView.setImageURI(uri)

            if (uri != null) {
                extract(uri)
            }
        }

    }


    private fun extract(uri:Uri){

        val loading= LoadingDialog(requireActivity())
        loading.startLoading()

        lifecycleScope.launch(Dispatchers.IO) {
            async {
                val inputStream=requireContext().contentResolver.openInputStream(uri)
                val imageBytes= ByteBuffer.wrap(inputStream!!.readBytes())

                val request=DetectDocumentTextRequest().withDocument(Document().withBytes(imageBytes))

                println(request)
                val response=texttract.detectDocumentText(request)

                println(response)

                var answer=""
                for(blocks in response.blocks){
                    if(blocks.blockType=="LINE"){
                        answer+=blocks.text.toString()
                    }
                }


                withContext(Dispatchers.Main){
                    loading.isDismiss()
                    val intent=Intent(requireContext(),ChatActivity()::class.java)
                    intent.putExtra("extractedText",answer)
                    startActivity(intent)
                }
            }
        }
    }


    override fun onStart() {
        super.onStart()

        binding.imageView2.visibility=View.VISIBLE
        binding.addImageText.visibility=View.VISIBLE
        binding.cameraButton.visibility=View.VISIBLE
        binding.galleryButton.visibility=View.VISIBLE
        binding.imageView.visibility=View.GONE
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding=FragmentGalleryBinding.inflate(layoutInflater)


        binding.galleryButton.setOnClickListener {
            galleryLauncher.launch("image/*")
            binding.imageView2.visibility=View.GONE
            binding.addImageText.visibility=View.GONE
            binding.cameraButton.visibility=View.GONE
            binding.galleryButton.visibility=View.GONE
            binding.imageView.visibility=View.VISIBLE
        }

        binding.cameraButton.setOnClickListener {

            val i=Intent(requireContext(),CameraActivity::class.java)
            imageLauncher.launch(i)
        }


        return binding.root
    }

}