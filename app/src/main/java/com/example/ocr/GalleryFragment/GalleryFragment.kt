package com.example.ocr.GalleryFragment

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.textract.AmazonTextractClient
import com.amazonaws.services.textract.model.StartDocumentAnalysisRequest
import com.example.ocr.databinding.FragmentGalleryBinding
import android.util.Base64
import androidx.lifecycle.lifecycleScope
import com.amazonaws.services.textract.model.DetectDocumentTextRequest
import com.amazonaws.services.textract.model.Document
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.nio.ByteBuffer

/**
 * A simple [Fragment] subclass.
 * Use the [GalleryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GalleryFragment : Fragment() {

    private lateinit var binding: FragmentGalleryBinding
    val credential=BasicAWSCredentials("AKIAXHXRS4F57R7AM6NO","s5pIyHieLnI7j7QzPw/ndLdCKCSYFkiIDrJYpEix")

    val texttract = AmazonTextractClient(credential)

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
                println(response)


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
    ): View? {
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