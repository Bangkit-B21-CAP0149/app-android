package com.arjuna.capstoneproject.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arjuna.capstoneproject.R
import com.arjuna.capstoneproject.databinding.FragmentProfileBinding
import com.arjuna.capstoneproject.ui.auth.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private val binding: FragmentProfileBinding by lazy {
        FragmentProfileBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firebaseAuth = FirebaseAuth.getInstance()
        val user = firebaseAuth.currentUser

        binding.apply {
            tvEmail.text = user?.email
            tvUserName.text = user?.displayName

            btnLogout.setOnClickListener {
                firebaseAuth.signOut()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }
}