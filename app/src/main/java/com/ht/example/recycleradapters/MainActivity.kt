package com.ht.example.recycleradapters

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sectionAdapterBtn.setOnClickListener {
            val intent = Intent(this, SectionAdapterActivity::class.java)
            startActivity(intent)
        }

        expandableAdapterBtn.setOnClickListener {
            val intent = Intent(this, ExpandableActivity::class.java)
            startActivity(intent)
        }
    }
}
