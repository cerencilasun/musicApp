package com.ceren.deneme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.ceren.deneme.databinding.ActivityGirisBinding
import com.google.firebase.auth.FirebaseAuth

class GirisActivity : AppCompatActivity() {
    lateinit var  auth: FirebaseAuth
    lateinit var binding: ActivityGirisBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityGirisBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        //Kullanıcının oturum açıp açmadığını kontrol edelim
        var currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this@GirisActivity,ProfilActivity::class.java))
            finish()
        }
        //Giriş yap butonuna tıklandığında
        binding.girisyapbutton.setOnClickListener {
            var girisemail = binding.girisemail.text.toString()
            var girisparola = binding.girisparola.text.toString()
            if (TextUtils.isEmpty(girisemail)) {
                binding.girisemail.error = "Lütfen e-mail adresinizi yazınız."
                return@setOnClickListener
            } else if (TextUtils.isEmpty(girisparola)) {
                binding.girisparola.error = "Lütfen parolanızı yazınız."
                return@setOnClickListener
            }
            //Giriş bilgilerimizi doğrulayıp giriş yapıyoruz
            auth.signInWithEmailAndPassword(girisemail,girisparola)
                .addOnCompleteListener(this){
                    if (it.isSuccessful){
                        intent = Intent(applicationContext,ProfilActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else {
                        Toast.makeText(applicationContext,"Giriş hatalı, lütfen tekrar deneyiniz.",
                            Toast.LENGTH_LONG).show()
                    }
                }
        }
        //Yeni üyelik sayfasına gitmek için
        binding.girisYeniUyelik.setOnClickListener{
            intent = Intent(applicationContext,UyeActivity::class.java)
            startActivity(intent)
            finish()
        }
        //Parolamı unuttum sayfasına gitmrek için
        binding.girisparolaunuttum.setOnClickListener{
            intent = Intent(applicationContext,PsifirlaActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}