package com.example.hvportfolio3.ui.perfil

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.hvportfolio3.MainActivity
import com.example.hvportfolio3.Perfil
import com.example.hvportfolio3.R
import com.example.hvportfolio3.databinding.FragmentPerfilBinding
import com.google.gson.Gson
import java.io.File

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
            activitymain.nombre= nombre.toString()  // Setter
            activitymain.email = email.toString()
            activitymain.telefono = telefono.toString()
            activitymain.edad  = edad.toString()
            activitymain.tipoSangre = tipoSangre.toString()
            activitymain.profesion = profesion.toString()


            val perfilInput = arrayOf(
                Perfil(nombre.toString(), email.toString(), telefono.toString(), edad.toString(), tipoSangre.toString(), profesion.toString() )
            )

            val rutaArchivo = "app/src/main/assets/data.json"

            val gson = Gson()
            val json = gson.toJson(perfilInput)
            File(rutaArchivo).writeText(json)
            File(rutaArchivo).writeText("Buenas")

            try {
                File(rutaArchivo).writeText(json)
                Toast.makeText(requireActivity(), "Guardado", Toast.LENGTH_SHORT).show()
            }catch (e: FileSystemException){
                Toast.makeText(requireActivity(), "Hubo un error al guardar", Toast.LENGTH_SHORT).show()
            }



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