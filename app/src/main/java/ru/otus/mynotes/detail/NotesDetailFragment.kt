package ru.otus.mynotes.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import ru.otus.mynotes.databinding.FragmentNotesDetailBinding
import ru.otus.mynotes.detail.NotesDetailViewModel
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class NotesDetailFragment : Fragment() {

    private var _binding: FragmentNotesDetailBinding? = null
    private val noteId: Long? by lazy {  arguments?.getLong("noteId") }
    private val viewModel by viewModels<NotesDetailViewModel> { NotesDetailViewModel.Factory(noteId) }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { note ->
                    _binding?.titleEt?.setText(note.title)
                    _binding?.noteEt?.setText(note.text)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val binding = _binding ?: return
        val title = binding.titleEt.text.toString()
        val note = binding.noteEt.text.toString()
        viewModel.onScreenClose(title, note)

        _binding = null
    }
}