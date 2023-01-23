package com.easytools.currencyconverter

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.google.android.gms.ads.*
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var list: ArrayList<Item>
    private lateinit var adapter: SpinnerAdapter
    var from = "AED" //for the selected item
    var to = "AED" //for the selected item
    lateinit var mAdView : AdView
    var adStatus = false //for loading an ad when connecting to internet, if the app started with no internet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this) {}

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        if(checkForInternet(this)) {
            adStatus = true
        }

        //list of objects of type the data class Item  for the AdapterSpinner Class
        list = ArrayList<Item>()

        var codesArray = arrayOf("AED", "AFN", "ALL", "AMD", "ANG", "AOA", "ARS", "AUD", "AWG", "AZN", "BAM", "BBD", "BDT", "BGN", "BHD", "BIF", "BMD", "BND", "BOB", "BRL", "BSD", "BTC", "BTN", "BWP", "BYN", "BZD", "CAD", "CDF", "CHF", "CLP", "CNY", "COP", "CRC", "CUP", "CVE", "CZK", "DJF", "DKK", "DOP", "DZD", "EGP", "ERN", "ETB", "EUR", "FJD", "FKP", "GBP", "GEL", "GGP", "GHS", "GIP", "GMD", "GNF", "GTQ", "GYD", "HKD", "HNL", "HRK", "HTG", "HUF", "IDR", "ILS", "IMP", "INR", "IQD", "IRR", "ISK", "JEP", "JMD", "JOD", "JPY", "KES", "KGS", "KHR", "KMF", "KPW", "KRW", "KWD", "KYD", "KZT", "LAK", "LBP", "LKR", "LRD", "LSL", "LTL", "LYD", "MAD", "MDL", "MGA", "MKD", "MMK", "MNT", "MOP", "MRO", "MUR", "MVR", "MWK", "MXN", "MYR", "MZN", "NAD", "NGN", "NIO", "NOK", "NPR", "NZD", "OMR", "PAB", "PEN", "PGK", "PHP", "PKR", "PLN", "PYG", "QAR", "RON", "RSD", "RUB", "RWF", "SAR", "SBD", "SCR", "SEK", "SGD", "SHP", "SLE", "SOS", "SRD", "STD", "SVC", "SYP", "SZL", "THB", "TJS", "TMT", "TND", "TOP", "TRY", "TTD", "TWD", "TZS", "UAH", "UGX", "USD", "UYU", "UZS", "VEF", "VND", "VUV", "WST", "XAF", "XAU", "XCD", "XOF", "XPF", "YER", "ZAR", "ZMW")
        var flagsArray = arrayOf(R.drawable.aed, R.drawable.afn, R.drawable.all, R.drawable.amd, R.drawable.ang, R.drawable.aoa, R.drawable.ars, R.drawable.aud, R.drawable.awg, R.drawable.azn, R.drawable.bam, R.drawable.bbd, R.drawable.bdt, R.drawable.bgn, R.drawable.bhd, R.drawable.bif, R.drawable.bmd, R.drawable.bnd, R.drawable.bob, R.drawable.brl, R.drawable.bsd, R.drawable.btc, R.drawable.btn, R.drawable.bwp, R.drawable.byn, R.drawable.bzd, R.drawable.cad, R.drawable.cdf, R.drawable.chf, R.drawable.clp, R.drawable.cny, R.drawable.cop, R.drawable.crc, R.drawable.cup, R.drawable.cve, R.drawable.czk, R.drawable.djf, R.drawable.dkk, R.drawable.dop, R.drawable.dzd, R.drawable.egp, R.drawable.ern, R.drawable.etb, R.drawable.eur, R.drawable.fjd, R.drawable.fkp, R.drawable.gbp, R.drawable.gel, R.drawable.ggp, R.drawable.ghs, R.drawable.gip, R.drawable.gmd, R.drawable.gnf, R.drawable.gtq, R.drawable.gyd, R.drawable.hkd, R.drawable.hnl, R.drawable.hrk, R.drawable.htg, R.drawable.huf, R.drawable.idr, R.drawable.ils, R.drawable.imp, R.drawable.inr, R.drawable.iqd, R.drawable.irr, R.drawable.isk, R.drawable.jep, R.drawable.jmd, R.drawable.jod, R.drawable.jpy, R.drawable.kes, R.drawable.kgs, R.drawable.khr, R.drawable.kmf, R.drawable.kpw, R.drawable.krw, R.drawable.kwd, R.drawable.kyd, R.drawable.kzt, R.drawable.lak, R.drawable.lbp, R.drawable.lkr, R.drawable.lrd, R.drawable.lsl, R.drawable.ltl, R.drawable.lyd, R.drawable.mad, R.drawable.mdl, R.drawable.mga, R.drawable.mkd, R.drawable.mmk, R.drawable.mnt, R.drawable.mop, R.drawable.mro, R.drawable.mur, R.drawable.mvr, R.drawable.mwk, R.drawable.mxn, R.drawable.myr, R.drawable.mzn, R.drawable.nad, R.drawable.ngn, R.drawable.nio, R.drawable.nok, R.drawable.npr, R.drawable.nzd, R.drawable.omr, R.drawable.pab, R.drawable.pen, R.drawable.pgk, R.drawable.php, R.drawable.pkr, R.drawable.pln, R.drawable.pyg, R.drawable.qar, R.drawable.ron, R.drawable.rsd, R.drawable.rub, R.drawable.rwf, R.drawable.sar, R.drawable.sbd, R.drawable.scr, R.drawable.sek, R.drawable.sgd, R.drawable.shp, R.drawable.sle, R.drawable.sos, R.drawable.srd, R.drawable.std, R.drawable.svc, R.drawable.syp, R.drawable.szl, R.drawable.thb, R.drawable.tjs, R.drawable.tmt, R.drawable.tnd, R.drawable.top, R.drawable.try_, R.drawable.ttd, R.drawable.twd, R.drawable.tzs, R.drawable.uah, R.drawable.ugx, R.drawable.usd, R.drawable.uyu, R.drawable.uzs, R.drawable.vef, R.drawable.vnd, R.drawable.vuv, R.drawable.wst, R.drawable.xaf, R.drawable.xau, R.drawable.xcd, R.drawable.xof, R.drawable.xpf, R.drawable.yer, R.drawable.zar, R.drawable.zmw)

        //filling that list with objects of type Item
        var i = 0
        while(i < codesArray.size) {
            list.add( Item( codesArray[i], flagsArray[i] ) )
            i++
        }

        adapter = SpinnerAdapter(this, list)
        spnFrom.adapter = adapter
        spnTo.adapter = adapter

        spnFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var clickedItem: Item = parent?.getItemAtPosition(position) as Item
                from = clickedItem.getCode()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        //-----
        spnTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var clickedItem: Item = parent?.getItemAtPosition(position) as Item
                to = clickedItem.getCode()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
        //========================================
        //calculate the result
        btnConvert.setOnClickListener {
            val call = RetrofitInstance.api.getRates()
            if(checkForInternet(this)) {
                if(!adStatus) {
                    mAdView.loadAd(adRequest)
                }
                call.enqueue(object: Callback<JsonObject>{
                    override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                        if(response.isSuccessful) {
                            val rates = response.body()?.getAsJsonObject("rates")
                            val fromValue = rates?.get(from).toString().toDouble()
                            val toValue = rates?.get(to).toString().toDouble()
                            if(etEnterAmount.text.toString().isNotEmpty()) {
                                val amount = etEnterAmount.text.toString().toDouble()
                                val result = (amount * toValue) / fromValue
                                val formattedResult= String.format("%.2f", result)
                                etResult.setText(formattedResult)
                                Toast.makeText(this@MainActivity, "Successful Process", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this@MainActivity, "Error happened, try later", Toast.LENGTH_LONG).show()
                            Log.d("WhatError", "in onResponse: ${response.code()}\n ${response.message()}")
                        }
                    }
                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "Error happened, try later", Toast.LENGTH_LONG).show()
                        Log.d("WhatError", "in onFailure: ${t.message}")
                    }
                } )
            } else {
                Toast.makeText(this@MainActivity, "No Internet", Toast.LENGTH_SHORT).show()
            }
        }

        //------------------------Ad events-------------------------
        mAdView.adListener = object: AdListener() {
            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
            }

            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                adStatus = true //so if the ad loaded after connecting to the internet, don't reload when clicking btnConvert
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
        }
        //------------------------Ad events-------------------------


    }
    //------------------------------------------------------------------
    //check internet for text related in ads-banner
    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
    //-------------------------------------------
}
