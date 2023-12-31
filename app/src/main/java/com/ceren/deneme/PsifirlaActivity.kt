package com.ceren.deneme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.ceren.deneme.databinding.ActivityProfilBinding
import com.ceren.deneme.databinding.ActivityPsifirlaBinding
import com.google.firebase.auth.FirebaseAuth

class PsifirlaActivity : AppCompatActivity() {
    lateinit var binding: ActivityPsifirlaBinding
    private lateinit var  auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPsifirlaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        binding.psifirlamabutton.setOnClickListener{
            var psifilaemail = binding.psifirlaemail.text.toString().trim()
            if (TextUtils.isEmpty(psifilaemail)){
                binding.psifirlaemail.error = "Lütfen e-mail adresinizi yazınız"
            }else {
                auth.sendPasswordResetEmail(psifilaemail)
                    .addOnCompleteListener(this){ sifirlama ->
                        if (sifirlama.isSuccessful){
                            binding.psifirlamesaj.text = "E-mail adresinize sıfırlama bağlantısı gönderildi. Lütfen kontrol ediniz."
                        }else {
                            binding.psifirlamesaj.text = "Sıfırlama işlemi başarızısız."
                        }
                    }
            }
        }
        //Giriş sayfasına gitmek için
        binding.psifirlamagirisyapbutton.setOnClickListener{
            intent = Intent(applicationContext,GirisActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}