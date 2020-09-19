package com.plbear.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.plbear.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val sp = getSharedPreferences("default", 0)
        val baseNum = sp.getInt("base_num", 7000)
        binding.etBase.setText(baseNum.toString())
        val point = sp.getFloat("point", 27f)
        binding.etPoint.setText(point.toString())
        binding.tvDesc.text = sp.getString("last_text","")

        binding.btnCal.setOnClickListener {
            val base = binding.etBase.text.toString().toInt()
            val pointNum = binding.etPoint.text.toString().toFloat()
            sp.edit().putInt("base_num", base).apply()
            sp.edit().putFloat("point", pointNum).apply()
            binding.tvDesc.text = text(base, pointNum)
            sp.edit().putString("last_text", binding.tvDesc.text.toString()).apply()
        }
    }

    private fun text(base: Int, tmp: Float): String {
        val cnt = 5
        val point = 100 - tmp
        Log.e("yanlog","point $point")
        val scale = if (point >= 95) 60f
        else if (point >= 90) 40f
        else if (point >= 80) 15f
        else if (point >= 70) 8f
        else if (point >= 60) 5f
        else if (point >= 50) 3f
        else if (point >= 40) 1.5f
        else if (point >= 30) 0.8f
        else if (point >= 20) 0.3f
        else if (point >= 15) -0.5f
        else if (point >= 10) -5f
        else if (point >= 5) -30f
        else -100f

        if (scale > 0) {
            val echoCnt = (base * scale / (20 * 5) / cnt).toInt()
            return "每天需要定投: $echoCnt"
        }
        val echoCnt = (base * scale / 20).toInt()
        return "每天需要卖出: ${-echoCnt}"
    }
}