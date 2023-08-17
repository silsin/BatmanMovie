package com.fakhari.batmanmovie.ui.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.fakhari.batmanmovie.R
import com.fakhari.batmanmovie.data.model.MovieModel
import com.fakhari.batmanmovie.utility.ConstValue.MOVIE_ID
import com.bumptech.glide.Glide
import com.fakhari.batmanmovie.base.BaseFragment
import com.fakhari.batmanmovie.databinding.DetailFragmentBinding
import com.fakhari.batmanmovie.databinding.MovieListFragmentBinding
import com.fakhari.batmanmovie.ui.movielist.MovieListVieModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class MovieDetailFragment : BaseFragment<MovieDetailViewModel>()  {

    override val vm: MovieDetailViewModel by inject()
    private lateinit var frgBinding : DetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        frgBinding = DetailFragmentBinding.inflate(inflater , container , false)
        return frgBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = arguments?.getString(MOVIE_ID) ?: ""
        if (movieId == "")
            errorOperation()

        manageData(movieId)
    }

    private fun manageData(movieId: String) {
        lifecycleScope.launch {
            vm.fetchData(movieId)
        }
        vm.movieDetailLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                showData(it)

            }
        }
        vm.movieDetailError.observe(viewLifecycleOwner){

        }

    }

    private fun showData(model: MovieModel) {
        Glide.with(this).load(model.poster)
            .into(img_poster)

        movie_content.visibility = View.VISIBLE

        tv_movie_name.text = model.title

        tv_language.text = model.language

        tv_overview.text = model.plot

        tv_status.text = model.released

        progress_vote.progress = model.ratings[0].value.split("/")[0].toFloat().toInt()

        tv_progress_text.text = model.ratings[0].value.split("/")[0]
    }

    private fun errorOperation() {
        Toast.makeText(requireContext(), R.string.error_occurred, Toast.LENGTH_SHORT).show()
        activity?.onBackPressed()
    }
}