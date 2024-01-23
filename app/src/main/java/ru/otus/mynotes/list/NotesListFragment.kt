package ru.otus.mynotes.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlinx.coroutines.launch
import ru.otus.mynotes.R
import ru.otus.mynotes.databinding.FragmentNotesListBinding

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

    private val menuProvider = object : MenuProvider {

        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.menu_main, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            if (menuItem.itemId == R.id.action_settings) {
                AlertDialog.Builder(requireActivity())
                    .setTitle("Choose colum count")
                    .setNegativeButton("One") { _, _ -> viewModel.onColumnCountChanged(1) }
                    .setPositiveButton("Two") { _, _ -> viewModel.onColumnCountChanged(2) }
                    .show()
            }
            return false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNotesListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().addMenuProvider(menuProvider)

        binding.notesRv.adapter = adapter
        binding.addButton.setOnClickListener { openDetails(null) }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { state ->
                    adapter.submitList(state.notes)
                    (binding.notesRv.layoutManager as StaggeredGridLayoutManager).spanCount = state.columnCount
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