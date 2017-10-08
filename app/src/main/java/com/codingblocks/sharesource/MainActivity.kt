package com.codingblocks.sharesource

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStart.setOnClickListener({
            startService(Intent(this@MainActivity, BeaconTransmitService::class.java))
        })

        btnStop.setOnClickListener({
            stopService(Intent(this@MainActivity, BeaconTransmitService::class.java))
        })
    }
}
