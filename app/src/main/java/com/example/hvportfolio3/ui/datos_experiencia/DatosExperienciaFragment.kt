package com.example.hvportfolio3.ui.datos_experiencia

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hvportfolio3.R

class DatosExperienciaFragment : Fragment() {

    companion object {
        fun newInstance() = DatosExperienciaFragment()
    }

    private val viewModel: DatosExperienciaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_datos_experiencia, container, false)
    }
}