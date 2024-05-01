package com.example.hvportfolio3.ui.gallery

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hvportfolio3.R
import com.example.hvportfolio3.databinding.FragmentGalleryBinding
import org.apache.commons.io.FileUtils
import java.io.File


class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textGallery
        galleryViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
/*
        binding.btnGallery.setOnClickListener {
            //requestPermission()
           //pickPhotoFromGallery()
            pickImagesFromGallery()

        }

 */

        val selectedPaths = mutableListOf<String>()


        val btnSelectImages: Button = root.findViewById(R.id.btnGallery)
        val rvImages: RecyclerView = root.findViewById(R.id.galleryGrid)
        val layoutManager = GridLayoutManager(requireActivity(), 2)

        rvImages.layoutManager = layoutManager
        val x = (resources.displayMetrics.density * 4).toInt() //converting dp to pixels
        rvImages.addItemDecoration(SpacingItemDecorator(x)) //setting space between items in RecyclerView
        val imageAdapter = ImageAdapter(selectedPaths)




        val selectImagesActivityResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    Log.d("result !!!!! = ", result.resultCode.toString())
                    val data: Intent? = result.data
                    //If multiple image selected
                    if (data?.clipData != null) {
                        val count = data.clipData?.itemCount ?: 0

                        for (i in 0 until count) {
                            val imageUri: Uri? = data.clipData?.getItemAt(i)?.uri
                            val file = getImageFromUri(imageUri)
                            file?.let {
                                selectedPaths.add(it.absolutePath)
                                Log.d("ARRAY = ", selectedPaths[i])
                            }
                        }
                        imageAdapter.addSelectedImages(selectedPaths)

                    }
                    //If single image selected
                    else if (data?.data != null) {
                        val imageUri: Uri? = data.data
                        val file = getImageFromUri(imageUri)
                        file?.let {
                            selectedPaths.add(it.absolutePath)
                        }
                        Log.d("result !!!!! = ", selectedPaths.toString())
                        imageAdapter.addSelectedImages(selectedPaths)
                    }


                }

            }


        rvImages.adapter = imageAdapter
        Log.d("imageAdapter = ", imageAdapter.toString())

        btnSelectImages.setOnClickListener {

            val intent = Intent(ACTION_GET_CONTENT)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.type = "image/*"
            selectImagesActivityResult.launch(intent)
            Log.d("click", selectImagesActivityResult.launch(intent).toString())
        }
        /*
        try {
            deleteTempFiles()
        } catch (e: Exception) {

        }

         */




        return root
    }

    private fun pickImagesFromGallery() {
        val selectImagesActivityResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {

                }
            }
        val intent = Intent(ACTION_GET_CONTENT)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.type = "*/*"
        selectImagesActivityResult.launch(intent)

    }


/*
    private fun requestPermission(){
        when{
            ContextCompat.checkSelfPermission(
              requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED->{
                pickPhotoFromGallery()
            }

            else-> requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ){isGranted->
        if(isGranted){
            pickPhotoFromGallery()
        }else{
            Toast.makeText(requireActivity(), "Debes conceder permisos para escoger las imágenes",Toast.LENGTH_SHORT).show()
        }

    }
    */


    private fun getImageFromUri(imageUri: Uri?) : File? {
        imageUri?.let { uri ->
            val mimeType = getMimeType(requireActivity(), uri)
            mimeType?.let {
                val file = createTmpFileFromUri(requireActivity(), imageUri,"temp_image", ".$it")
                file?.let { Log.d("image Url = ", file.absolutePath) }
                return file
            }
        }
        return null
    }


    private fun getMimeType(context: Context, uri: Uri): String? {
        //Check uri format to avoid null
        val extension: String? = if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            //If scheme is a content
            val mime = MimeTypeMap.getSingleton()
            mime.getExtensionFromMimeType(context.contentResolver.getType(uri))
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(uri.path)).toString())
        }
        return extension
    }

    private fun createTmpFileFromUri(context: Context, uri: Uri, fileName: String, mimeType: String): File? {
        return try {
            val outputDir = context.cacheDir
            val stream = context.contentResolver.openInputStream(uri)
            val file = File.createTempFile(fileName, mimeType, outputDir)

            FileUtils.copyInputStreamToFile(stream,file)

            file

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
/*
    private fun deleteTempFiles(file: File = context?.cacheDir ): Boolean {
        if (file.isDirectory) {
            val files = file.listFiles()
            if (files != null) {
                for (f in files) {
                    if (f.isDirectory) {
                        deleteTempFiles(f)
                    } else {
                        f.delete()
                    }
                }
            }
        }
        return file.delete()
    }

*/
/*
    private fun pickPhotoFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.type = "image/*"
        startForActivityGallery.launch(intent)

    }

    private val startForActivityGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        result->
        if(result.resultCode== Activity.RESULT_OK){
            val data = result.data?.data
            binding.galleryView.setImageURI(data)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    */
 */
}