package com.example.hvportfolio3.ui.webview

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageButton
import com.example.hvportfolio3.R
import com.example.hvportfolio3.databinding.FragmentGalleryBinding
import com.example.hvportfolio3.databinding.FragmentHomeBinding
import com.example.hvportfolio3.databinding.FragmentWebViewBinding

class WebViewFragment : Fragment() {

    companion object {
        fun newInstance() = WebViewFragment()
    }

    private var _binding: FragmentWebViewBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: WebViewViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentWebViewBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val webRender: WebView = root.findViewById<WebView>(R.id.webRender)
        val urlLinkedin: EditText = root.findViewById<EditText>(R.id.urlLikendin)
        val btnWeb= root.findViewById<ImageButton>(R.id.btnWeb)
        var url = urlLinkedin.text.toString()
        webRender.loadUrl(url)
        webRender.webViewClient = WebViewClient()
        webRender.clearCache(false)
        webRender.settings?.javaScriptEnabled = true



        btnWeb.setOnClickListener {
            webRender.loadUrl(urlLinkedin.text.toString())
        }
        return root
    }
}