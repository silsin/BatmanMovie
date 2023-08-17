package com.fakhari.batmanmovie.ui.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.fakhari.batmanmovie.App
import com.fakhari.batmanmovie.R
import com.fakhari.batmanmovie.base.BaseFragment
import com.fakhari.batmanmovie.databinding.MovieListFragmentBinding
import com.fakhari.batmanmovie.utility.ConstValue.MOVIE_ID
import kotlinx.android.synthetic.main.movie_list_fragment.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MovieListFragment : BaseFragment<MovieListVieModel>() {
    override val vm: MovieListVieModel by inject()
    private lateinit var frgBinding : MovieListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        frgBinding = MovieListFragmentBinding.inflate(inflater , container , false)
        return frgBinding.root
    }

    private lateinit var movieAdapter: MovieListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObj()

        getData()

        observeLiveData()
    }


    private fun initObj() {
        Thread {
            val data =  App.db?.getMovieSearchDao()?.getAll()
            activity?.runOnUiThread {
                movieAdapter.submitList(data)
                progress_bar.visibility = View.GONE
            }
        }.start()
        movieAdapter = MovieListAdapter {
            val movieId = Bundle()
            movieId.putString(MOVIE_ID, it)
            findNavController().navigate(R.id.action_movieListFragment_to_movieFragment, movieId)
        }
        rec_movies.apply {
            hasFixedSize()
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = movieAdapter
        }
    }


    private fun getData() {
        lifecycleScope.launch {
            vm.fetchData()
        }
    }

    private fun observeLiveData() {
        vm.movieLiveData.observe(viewLifecycleOwner) {
            Thread {
                if (it != null) {
                    App.db?.getMovieSearchDao()?.insertAll(it.searchMovieModels)
                    activity?.runOnUiThread {
                        movieAdapter.submitList(it?.searchMovieModels)
                        progress_bar.visibility = View.GONE
                    }
                }
            }.start()
        }
        vm.movieErrorData.observe(viewLifecycleOwner){

        }
    }
}