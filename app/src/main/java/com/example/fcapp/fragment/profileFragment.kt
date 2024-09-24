package com.example.fcapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.fcapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProfileFragment : Fragment() {
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        database = FirebaseDatabase.getInstance().reference

        //РОСТО ПРИМЕР ДОЗЕНТ ТАЧ ТЗИС
        val name = "John Doe"
        val email = "johndoe@example.com"

        saveUserProfile(name, email)

        return view
    }

    private fun saveUserProfile(name: String, email: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "default_user"
        val userProfile = UserProfile(name, email)

        database.child("users").child(userId).setValue(userProfile)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Profile saved", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Error saving profile", Toast.LENGTH_SHORT).show()
                }
            }
    }
}

data class UserProfile(val name: String, val email: String)
