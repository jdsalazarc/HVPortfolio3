package com.example.hvportfolio3.ui.buttons

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.hvportfolio3.MainActivity
import com.example.hvportfolio3.databinding.FragmentButtonsBinding

class ButtonsFragment : Fragment() {

    val positiveButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(requireActivity(),
            android.R.string.yes, Toast.LENGTH_SHORT).show()
    }
    val negativeButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(requireActivity(),
            android.R.string.no, Toast.LENGTH_SHORT).show()
    }
    val neutralButtonClick = { dialog: DialogInterface, which: Int ->
        Toast.makeText(requireActivity(),
            "Maybe", Toast.LENGTH_SHORT).show()
    }

    private var _binding: FragmentButtonsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    companion object {
        fun newInstance() = ButtonsFragment()
    }

    private val viewModel: ButtonsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentButtonsBinding.inflate(inflater, container, false)
        binding.getPerfilInfo.setOnClickListener {
                alertHV(requireView())
        }
        val root: View = binding.root
        return root
    }


    fun alertHV(view: View){
        val activitymain = (activity as MainActivity)
        val items = arrayOf(activitymain.nombre.toString(), activitymain.email.toString() )
        val builder = AlertDialog.Builder(requireActivity())
        with(builder)
        {
            setTitle("HV")
            setItems(items) { dialog, which ->
                Toast.makeText(requireActivity(), items[which] + " is clicked", Toast.LENGTH_SHORT).show()
            }

            setPositiveButton("OK", positiveButtonClick)
            show()
        }


    }
}
