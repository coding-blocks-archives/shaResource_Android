package com.codingblocks.sharesource

import android.app.Service
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseSettings
import android.content.Intent
import android.os.IBinder
import android.util.Log
import org.altbeacon.beacon.*
import org.altbeacon.beacon.utils.UrlBeaconUrlCompressor
import java.util.*

class BeaconTransmitService : Service()  {

    lateinit var beaconTransmitter: BeaconTransmitter

    override fun onBind(intent: Intent): IBinder? {
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("B", "START")
        val urlBytes = UrlBeaconUrlCompressor.compress("http://cb.lk/a/b/c")
        val beacon = Beacon.Builder()
                .setTxPower(-56)
                .setIdentifiers(
                        listOf(Identifier.fromBytes(urlBytes, 0, urlBytes.size, false))
                )
                .build()

        val beaconParser = BeaconParser()
                .setBeaconLayout(BeaconParser.EDDYSTONE_URL_LAYOUT)

        beaconTransmitter = BeaconTransmitter(this, beaconParser)
        beaconTransmitter.startAdvertising(beacon, object: AdvertiseCallback() {
            override fun onStartSuccess(settingsInEffect: AdvertiseSettings?) {
                Log.i("BEACON", "Advertisement start succeeded.");
                super.onStartSuccess(settingsInEffect)
            }

            override fun onStartFailure(errorCode: Int) {
                Log.e("BEACON", "Advertisement start failed with code: $errorCode");
                super.onStartFailure(errorCode)
            }
        })


        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.d("B", "DESTROY")
        beaconTransmitter.stopAdvertising()
        super.onDestroy()
    }
}
