package com.example.tha.ui.add

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tha.R
import com.example.tha.data.models.Note
import com.example.tha.data.viewmodels.NotesViewModel
import com.example.tha.databinding.DialogChoosePictureBinding
import com.example.tha.databinding.FragmentAddNoteBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddNoteFragment : Fragment() {

    private val mNotesViewModel: NotesViewModel by viewModels()
    private lateinit var binding: FragmentAddNoteBinding
    private lateinit var dialogBinding:DialogChoosePictureBinding
    private lateinit var dialog:Dialog
    private var image:Bitmap?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddNoteBinding.inflate(layoutInflater)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListeners()
    }

    private fun initClickListeners() {

        binding.btnAction.setOnClickListener{
            insertDataToDb()
        }
        binding.image.setOnClickListener {
            showImageChooserDialog()
        }
    }

    private fun insertDataToDb() {
        val title = binding.currentTitleEt.text.toString()
        val description = binding.currentDescriptionEt.text.toString()
        if (description.length<100 || description.length>1000){
            Toast.makeText(requireContext(), getString(R.string.description_length_toast), Toast.LENGTH_SHORT).show()
        }else if (title.length<5 ||title.length>100){
            Toast.makeText(requireContext(), getString(R.string.title_length_toast), Toast.LENGTH_SHORT).show()
        }else if (image==null){
            Toast.makeText(requireContext(), getString(R.string.error_select_image), Toast.LENGTH_SHORT).show()
        }else{
            if (!(title.isEmpty() || description.isEmpty())) {
                if (image!=null){
                    val newData = Note(
                        0,
                        title,
                        description,
                        image!!
                    )
                    mNotesViewModel.insertData(newData)
                    Toast.makeText(requireContext(), getString(R.string.succes_add), Toast.LENGTH_SHORT).show()
                }
                findNavController().navigate(R.id.action_addNoteFragment_to_listFragment)
            } else {
                Toast.makeText(requireContext(), getString(R.string.fill_all_fields), Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }


    private fun showImageChooserDialog() {
        dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogBinding = DialogChoosePictureBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.setCanceledOnTouchOutside(true)
        dialogBinding.llGallery.setOnClickListener {
            takePictureFromGallery()
            dialog.dismiss()
        }
        dialogBinding.llCamera.setOnClickListener {
            if (checkAndRequestPermissions()) {
                takePictureFromCamera()
                dialog.dismiss()
            }
        }
        dialogBinding.tvCancel.setOnClickListener { dialog.dismiss() }
        dialog.show()
        dialog.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }
    private fun takePictureFromGallery() {
        val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickPhoto.type = "image/*"
        pickPhoto.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        pickPhoto.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(pickPhoto, 1)
    }

    private fun takePictureFromCamera() {
        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePicture, 2)
    }

    private fun checkAndRequestPermissions(): Boolean {
        val cameraPermission =
            ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA)
        if (cameraPermission == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                3
            )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            takePictureFromCamera()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== Activity.RESULT_OK){
            when(requestCode){
                1->{
                    val selectedImage: Uri? = data?.data
                    val bitmap =
                        MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, selectedImage)
                    image=bitmap
                    binding.image.setImageBitmap(bitmap)
                }

                2->{
                    val bundle = data!!.extras
                    val bitmapImage = bundle!!["data"] as Bitmap?
                    if (bitmapImage != null) {
                        image=bitmapImage
                    }
                    binding.image.setImageBitmap(bitmapImage)

                }
            }

        }
    }

}