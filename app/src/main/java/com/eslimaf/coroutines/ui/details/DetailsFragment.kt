package com.eslimaf.coroutines.ui.details


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
import com.eslimaf.coroutines.data.marvel.api.model.entity.Comic
import com.eslimaf.coroutines.extensions.load
import kotlinx.android.synthetic.main.fragment_details.*

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class DetailsFragment : Fragment() {
    private lateinit var character: Character
    private val comicsListAdapter = ComicsListAdapter()
    private lateinit var viewModel: DetailsViewModel

    private val characterComicsObserver by lazy {
        Observer<List<Comic>> { comics ->
            comicsListAdapter.setComics(comics)
            detailsComicsRecyclerView.visibility = View.VISIBLE
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)

        viewModel.showLoader { show ->
            detailsComicsProgress.visibility = if (show) View.VISIBLE else View.GONE
        }

        viewModel.loadCharacterComics(character.id).observe(this, characterComicsObserver)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsCharacterName.text = character.name
        detailsDescription.text = character.description
        val imageUrl =
            character.thumbnail.path + "/standard_amazing." + character.thumbnail.extension
        detailsThumbnail.load(imageUrl)
        detailsComicsRecyclerView.layoutManager =
                LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        detailsComicsRecyclerView.adapter = comicsListAdapter
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment DetailsFragment.
         */
        @JvmStatic
        fun newInstance(character: Character): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.character = character
            return fragment
        }
    }
}
