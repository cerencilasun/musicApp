package com.ceren.deneme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import com.ceren.deneme.databinding.ActivityProfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfilActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfilBinding
    private lateinit var  auth: FirebaseAuth
    var databaseReference: DatabaseReference?=null
    var database: FirebaseDatabase?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")
        var currentUser = auth.currentUser
        binding.Profilemail.text = "E-mail: ${currentUser?.email}".toEditable()

        //realtime - database deki id ye ulaşıp chil ların içindeki veriyi sayfaya aktaracağız
        var userReferance = databaseReference?.child(currentUser?.uid!!)
        userReferance?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.Profiladsoyad.text = "Tam adınız: ${snapshot.child("adisoyadi").value.toString()}".toEditable()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        //Çıkış yap butonu
        binding.Profilcikisyapbutton.setOnClickListener{
            auth.signOut()
            startActivity(Intent(this@ProfilActivity,GirisActivity::class.java))
            finish()
        }
        //Hesabımı sil
        binding.Profilhesabimisilbutton.setOnClickListener{
            currentUser?.delete()?.addOnCompleteListener{
                if (it.isSuccessful){
                    Toast.makeText(applicationContext,"Hesabınız Silindi",
                        Toast.LENGTH_LONG).show()
                    auth.signOut()
                    startActivity(Intent(this@ProfilActivity,GirisActivity::class.java))
                    finish()
                    var currentUsdDb = currentUser?.let { it1 -> databaseReference?.child(it1.uid)}
                    currentUsdDb?.removeValue()
                    Toast.makeText(applicationContext,"AdiSoyadi Silindi",
                        Toast.LENGTH_LONG).show()
                }
            }

        }
        //Guncelle butonu
        binding.ProfilbilgilerimigNcellebutton.setOnClickListener{
            startActivity(Intent(this@ProfilActivity,GuncelleActivity::class.java))
            finish()
        }



    }
    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)


}