package com.malibin.study.github.presentation.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.malibin.study.github.databinding.ActivitySampleBinding

class SampleActivity : AppCompatActivity() {

    private var appendedString = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button1.setOnClickListener {
            appendedString += "1"
            binding.textView.text = appendedString
        }

        binding.button2.setOnClickListener {
            appendedString += "2"
            binding.textView.text = appendedString
        }

        binding.button3.setOnClickListener {
            appendedString += "3"
            binding.textView.text = appendedString
        }

        binding.button4.setOnClickListener {
            appendedString += "4"
            binding.textView.text = appendedString
        }
    }
}
