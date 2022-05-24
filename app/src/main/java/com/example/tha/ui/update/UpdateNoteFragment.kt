package com.example.tha.ui.update

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tha.R
import com.example.tha.data.models.Note
import com.example.tha.ui.viewmodels.NotesViewModel
import com.example.tha.databinding.DialogChoosePictureBinding
import com.example.tha.databinding.FragmentUpdateNoteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateNoteFragment : Fragment() {
    private val args by navArgs<UpdateNoteFragmentArgs>()

    private val mViewModel: NotesViewModel by viewModels()
    private lateinit var binding: FragmentUpdateNoteBinding
    private lateinit var dialogBinding:DialogChoosePictureBinding
    private lateinit var dialog:Dialog
    private lateinit var image: Bitmap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateNoteBinding.inflate(layoutInflater)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete -> confirmItemRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.currentTitleEt.setText(args.currentItem.title)
        binding.currentDescriptionEt.setText(args.currentItem.description)
        binding.currentTitleEt.setText(args.currentItem.title)
        binding.btnAction.text= activity?.resources?.getString(R.string.update) ?: ""

        binding.image.setImageBitmap(args.currentItem.image)
        initClickListners()
    }

    private fun initClickListners() {
        binding.btnAction.setOnClickListener {
            updateItem()
        }
        binding.image.setOnClickListener {
            showImageChooserDialog()
        }
    }
    private fun confirmItemRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mViewModel.deleteItem(args.currentItem)
            Toast.makeText(
                requireContext(),
                "Successfully Removed: ${args.currentItem.title}",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateNoteFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete '${args.currentItem.title}'?")
        builder.setMessage("Are you sure you want to remove '${args.currentItem.title}'?")
        builder.create().show()
    }

    private fun updateItem() {
        val title = binding.currentTitleEt.text.toString()
        val description = binding.currentDescriptionEt.text.toString()
        if (description.length<100 || description.length>1000){
            Toast.makeText(requireContext(), getString(R.string.description_length_toast), Toast.LENGTH_SHORT).show()
        }else if (title.length<5 ||title.length>100){
            Toast.makeText(requireContext(), getString(R.string.title_length_toast), Toast.LENGTH_SHORT).show()
        }else{
            if (!(title.isEmpty()||description.isEmpty())) {
                if (image!=null){
                    val updatedItem = Note(
                        args.currentItem.id,
                        title,
                        description,
                        image
                    )
                    mViewModel.updateData(updatedItem)
                    Toast.makeText(requireContext(), getString(R.string.succes_updated), Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_updateNoteFragment_to_listFragment)
                }else{
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.error_select_image),
                        Toast.LENGTH_SHORT
                    ).show()
                }

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