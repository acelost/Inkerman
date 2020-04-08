package com.acelost.inkermandemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.acelost.inkerman.Ink

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Ink.trace(5).trace(4)
    }
}
