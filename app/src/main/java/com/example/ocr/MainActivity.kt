package com.example.ocr


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.ocr.ImageFragment.ImageFragment
import com.example.ocr.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        replaceFragment(ImageFragment())
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){

                R.id.bottom_nav_image->{
                    replaceFragment(ImageFragment())
                }
            }
            true
        }

    }

    override fun onResume() {
        super.onResume()
        binding.bottomNavigationView.menu.findItem(R.id.bottom_nav_image).isChecked=true
    }
    private fun replaceFragment(fragment: Fragment){

        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container,fragment)
        fragmentTransaction.commit()
    }
}