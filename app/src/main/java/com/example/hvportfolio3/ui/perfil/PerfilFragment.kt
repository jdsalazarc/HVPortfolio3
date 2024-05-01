package com.example.hvportfolio3.ui.perfil

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.hvportfolio3.MainActivity
import com.example.hvportfolio3.R
import com.example.hvportfolio3.databinding.FragmentPerfilBinding

class PerfilFragment : Fragment() {
    private var _binding: FragmentPerfilBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    companion object {
        fun newInstance() = PerfilFragment()
    }

    private val viewModel: PerfilViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val imagePerfil: ImageView = root.findViewById(R.id.photoPerfil)
        val nombre: EditText = root.findViewById(R.id.nombre)
        val email: EditText = root.findViewById(R.id.email)
        val telefono: EditText = root.findViewById(R.id.telefono)
        val edad: EditText = root.findViewById(R.id.edad)
        val tipoSangre: EditText = root.findViewById(R.id.tipoSangre)
        val profesion: EditText = root.findViewById(R.id.profesion)
        val activitymain = (activity as MainActivity)
        binding.btnPickPhoto.setOnClickListener {
            //requestPermission()
           pickPhotoFromGallery()
            //pickImagesFromGallery(imagePerfilNav)

        }

        binding.guardarPerfil.setOnClickListener{
            activitymain.nombre= nombre  // Setter
            activitymain.email = email
            activitymain.telefono = telefono
            activitymain.edad  = edad
            activitymain.tipoSangre = tipoSangre
            activitymain.profesion = profesion


        }
        return root
    }

    private fun pickPhotoFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        //intent.putExtra(Intent., true)
        intent.type = "image/*"
        startForActivityGallery.launch(intent)

    }

    private val startForActivityGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
            result->
        if(result.resultCode== Activity.RESULT_OK){
            val data = result.data?.data
            //(activity as MainActivity).uriProfileImage = data// Setter

           // val id = (activity as MainActivity).uriProfileImage   // Getter
            binding.photoPerfil.setImageURI(data)

        }
    }
}