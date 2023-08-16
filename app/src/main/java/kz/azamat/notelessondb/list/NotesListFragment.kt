package kz.azamat.notelessondb.list

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import kz.azamat.notelessondb.R
import kz.azamat.notelessondb.databinding.FragmentNotesListBinding
import kz.azamat.notelessondb.pref.PreferencesProvider

class NotesListFragment : Fragment() {

    private val viewModel: NotesListViewModel by viewModels()
    private var _binding: FragmentNotesListBinding? = null
    private val adapter = NotesListAdapter { note ->
        openDetails(note.id)
    }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNotesListBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.notesRv.adapter = adapter
        binding.addButton.setOnClickListener { openDetails(null) }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { notes ->
                    PreferencesProvider.setNoteCount(notes.size)
                    adapter.submitList(notes)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                PreferencesProvider.getNoteCount().collect { count ->
                    binding.noteCountTv.text = "Note count $count"
                }
            }
        }
    }


    private fun openDetails(noteId: Long?) {
        val args = noteId?.let { bundleOf("noteId" to noteId) }
        findNavController().navigate(R.id.action_NotesListFragment_to_NotesDetailFragment, args)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}