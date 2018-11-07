package com.eslimaf.coroutines.ui.timeline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.eslimaf.coroutines.R
import com.eslimaf.coroutines.data.marvel.api.model.entity.Character
import com.eslimaf.coroutines.ui.details.DetailsFragment
import kotlinx.android.synthetic.main.timeline_fragment.*

class TimelineFragment : Fragment(), TimelineListAdapter.OnItemClickListener {

    companion object {
        const val FRAG_ID = "TimelineFragment"
        fun newInstance() = TimelineFragment()
    }

    private val adapter: TimelineListAdapter by lazy {
        TimelineListAdapter().apply {
            onItemClickListener = this@TimelineFragment
        }
    }
    private lateinit var viewModel: TimelineViewModel
    private val characterObserver by lazy {
        Observer<List<Character>> {
            updateCharacterList(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.timeline_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TimelineViewModel::class.java)
        timelineRecyclerView.layoutManager = LinearLayoutManager(this.context)
        timelineRecyclerView.adapter = adapter
        viewModel.loadWithClass(4).observe(this, characterObserver)
    }

    override fun onItemClick(character: Character) {
        val fragment = DetailsFragment.newInstance(character)
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun updateCharacterList(characters: List<Character>) {
        adapter.setCharacters(characters)
    }

}
