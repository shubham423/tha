package com.example.tha.ui.noteslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.tha.R
import com.example.tha.ui.viewmodels.NotesViewModel
import com.example.tha.databinding.FragmentNotesListBinding
import com.example.tha.ui.noteslist.adapter.ListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesListFragment : Fragment(){

    private lateinit var binding: FragmentNotesListBinding
    private val adapter: ListAdapter by lazy { ListAdapter(requireActivity()) }

    private val whenPasswordIsValid: NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotesListBinding.inflate(layoutInflater)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerview()
        whenPasswordIsValid.getAllData.observe(viewLifecycleOwner, { data ->
            if (data.isEmpty()){
                binding.noDataImageView.visibility=View.VISIBLE
                binding.noDataTextView.visibility=View.VISIBLE
            }else{
                binding.noDataImageView.visibility=View.GONE
                binding.noDataTextView.visibility=View.GONE
            }
            adapter.setData(data)
        })

        initClickListeners()
    }

    private fun initClickListeners() {
        binding.floatingActionButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_listFragment_to_addNoteFragment)
        }
    }

    private fun setupRecyclerview() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
    }



}
