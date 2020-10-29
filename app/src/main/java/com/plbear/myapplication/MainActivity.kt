package com.plbear.myapplication

import android.os.Bundle
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
        binding.tvDesc.text = sp.getString("last_text", "")

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
        val point = tmp
        val scale = if (point >= 95) 140f
        else if (point >= 90) 100f
        else if (point >= 80) 50f
        else if (point >= 70) 20f
        else if (point >= 60) 7f
        else if (point >= 50) 5f
        else if (point >= 40) 4f
        else if (point >= 30) 3f
        else if (point >= 20) 2f
        else if (point >= 15) 1f
        else if (point >= 10) 0.5f
        else if (point >= 5) 0.3f
        else if (point >= 4) -80f
        else if (point >= 3) -90f
        else if (point >= 2) -100f
        else if (point >= 1) -200f
        else -400f

        if (scale > 0) {
            val echoCnt = (base * scale / 20).toInt()
            return "每天需要定投: $echoCnt"
        }
        val echoCnt = (base * scale / 20).toInt()
        return "每天需要卖出: ${-echoCnt}"
    }
}