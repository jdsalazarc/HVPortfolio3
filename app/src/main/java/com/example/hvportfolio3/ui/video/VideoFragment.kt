package com.example.hvportfolio3.ui.video

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.hvportfolio3.databinding.FragmentVideoBinding

class VideoFragment : Fragment() {
    private var _binding: FragmentVideoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    companion object {
        fun newInstance() = VideoFragment()
    }

    private val viewModel: VideoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.getVideo.setOnClickListener {
            //requestPermission()
            pickVideoFromGallery()
            //pickImagesFromGallery(imagePerfilNav)

        }
        return root
    }

    private fun pickVideoFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        //intent.putExtra(Intent., true)
        intent.type = "video/*"
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
            binding.videoView.setVideoURI(data)
            val mediaController = MediaController(requireActivity())

            // sets the anchor view
            // anchor view for the videoView
            mediaController.setAnchorView(binding.videoView)

            // sets the media player to the videoView
            mediaController.setMediaPlayer(binding.videoView)

            // sets the media controller to the videoView
            binding.videoView.setMediaController(mediaController);

            // starts the video
            binding.videoView.start();

        }
    }
}