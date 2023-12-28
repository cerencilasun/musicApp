package com.ceren.deneme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ceren.deneme.databinding.ActivityGuncelleBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class GuncelleActivity : AppCompatActivity() {
    lateinit var binding:ActivityGuncelleBinding
    private lateinit var  auth: FirebaseAuth
    var databaseReference: DatabaseReference?=null
    var database: FirebaseDatabase?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityGuncelleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        var currentUser= auth.currentUser
        binding.guncelleemail.setText(currentUser?.email)
        // realtime-database de bulunan kullanıcının idsine erişip adını soyadını alalım
        var userReference=databaseReference?.child(currentUser?.uid!!)
        userReference?.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.guncelleadsoyad.setText(snapshot.child("adisoyadi").value.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        // Bilgilerimi güncelle button
        binding.guncellebilgilerimibutton.setOnClickListener{
            var guncelleemail = binding.guncelleemail.text.toString().trim()
            currentUser!!.updateEmail(guncelleemail)
                .addOnCompleteListener{task->
                    if(task.isSuccessful){
                        Toast.makeText(applicationContext,"E-mail güncellendi",
                            Toast.LENGTH_LONG).show()
                    }else {
                        Toast.makeText(applicationContext,"E-mail başarısız",
                            Toast.LENGTH_LONG).show()
                    }
                }
            //Parola Güncelleme
            var guncelleparola = binding.guncelleparola.text.toString().trim()
            currentUser!!.updatePassword(guncelleparola)
                .addOnCompleteListener{task->
                    if(task.isSuccessful){
                        Toast.makeText(applicationContext,"Parola güncellendi",
                            Toast.LENGTH_LONG).show()
                    }else {
                        Toast.makeText(applicationContext,"Parola başarısız",
                            Toast.LENGTH_LONG).show()
                    }
                }
            //Ad soyad güncelleme
            var currentUsdDb = currentUser?.let { it1 -> databaseReference?.child(it1.uid)}
            currentUsdDb?.removeValue()
            currentUsdDb?.child("adisoyadi")?.setValue(binding.guncelleadsoyad.text.toString())
            Toast.makeText(applicationContext,"AdiSoyadi Güncellendi",
                Toast.LENGTH_LONG).show()

        }
        //Giriş sayfasına gitmek için
        binding.guncellegirisyapbutton.setOnClickListener{
            intent = Intent(applicationContext,GirisActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}