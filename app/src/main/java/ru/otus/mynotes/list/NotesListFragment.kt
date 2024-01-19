package ru.otus.mynotes.list

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import ru.otus.mynotes.R
import ru.otus.mynotes.databinding.FragmentNotesListBinding
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class NotesListFragment : Fragment() {

    private val viewModel: NotesListViewModel by viewModels()
    private var _binding: FragmentNotesListBinding? = null
    private val adapter = NotesListAdapter { note ->
        openDetails(note.id)
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNotesListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.notesRv.adapter = adapter
        binding.addButton.setOnClickListener { openDetails(null) }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { notes ->
                    adapter.submitList(notes)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onViewResumed()
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