package com.acelost.inkermandemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.acelost.inkerman.Ink

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Samples().printHelloWorld()
        Samples().printFooBarBaz("Hello, world!", Any(), 42)
        Samples().printFooBarBazVariables("Hello, world!", Any(), 42)
        Samples().printWhereAmIFrom()
        Samples().compoundLetter()
    }
}
