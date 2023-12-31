package com.ceren.deneme


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.ceren.deneme.databinding.ActivityUyeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UyeActivity : AppCompatActivity() {
    lateinit var binding : ActivityUyeBinding
    private lateinit var  auth:FirebaseAuth
    var databaseReference:DatabaseReference?=null
    var database:FirebaseDatabase?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityUyeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        //Kaydet butonu ile kaydetme adımları
        binding.uyekaydetbutton.setOnClickListener{
            var uyeadsoyad = binding.uyeadsoyad.text.toString()
            var uyeemail = binding.uyeemail.text.toString()
            var uyeparola = binding.uyeparola.text.toString()
            if (TextUtils.isEmpty(uyeadsoyad)) {
                binding.uyeadsoyad.error = "Lütfen adınızı ve soyadınızı giriniz!"
                return@setOnClickListener
            }else if (TextUtils.isEmpty(uyeemail)) {
                binding.uyeemail.error = "Lütfen e-mail adresinizi giriniz giriniz!"
                return@setOnClickListener
            }else if (TextUtils.isEmpty(uyeparola)) {
                binding.uyeparola.error = "Lütfen parolanızı giriniz!"
                return@setOnClickListener
        }
            //Email, parola ve kıllanıcı bilgilerini veritabanına ekleme
            auth.createUserWithEmailAndPassword(binding.uyeemail.text.toString(),binding.uyeparola.text.toString())
                .addOnCompleteListener(this){ task ->
                    if (task.isSuccessful){
                        // Şuanki kullanıcı bilgilerini alalım
                        var currentUser = auth.currentUser
                        //Kullanıcı id sini alıp o id adı altında adımızı ve soyadımızı kaydedelim
                        var currentUsdDb = currentUser?.let { it1 -> databaseReference?.child(it1.uid)}
                        currentUsdDb?.child("adisoyadi")?.setValue(binding.uyeadsoyad.text.toString())
                        Toast.makeText(this@UyeActivity,"Kayıt Başarılı",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this@UyeActivity,"Kayıt Hatalı", Toast.LENGTH_LONG).show()
                }
                }


    }
        //Giriş sayfasına gitmek için
        binding.uyegirisyapbutton.setOnClickListener{
            intent = Intent(applicationContext,GirisActivity::class.java)
            startActivity(intent)
            finish()
        }
}
}