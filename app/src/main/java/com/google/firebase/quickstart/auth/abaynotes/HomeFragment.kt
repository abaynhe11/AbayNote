package com.google.firebase.quickstart.auth.abaynotes



import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch
import com.google.firebase.quickstart.auth.abaynotes.R
import com.google.firebase.quickstart.auth.abaynotes.NoteDatabase


class HomeFragment : BaseFragment() {
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        auth = FirebaseAuth.getInstance()
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //recycler_view_notes.setHasFixedSize(true)
        //recycler_view_notes.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)


        launch {
            context?.let {
                val notes = NoteDatabase(it).getNoteDao().getAllNotes()
                recycler_view_notes.adapter = NotesAdapter(notes)
            }
        }


        button_add.setOnClickListener {

            val action = HomeFragmentDirections.actionAddNote()
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.hflogoutmenu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.logout -> {
                auth.signOut()
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
                true
            }

            else -> {
                super.onOptionsItemSelected(item)

            }
        }
    }
}