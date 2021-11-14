package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    private var asteroidAdapter: AsteroidAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentMainBinding.inflate(inflater)
        val application = requireNotNull(this.activity).application
        val viewModelFactory = MainViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        asteroidAdapter = AsteroidAdapter(AsteroidAdapter.OnClickListener {
            viewModel.displayAsteroid(it)
        })
        binding.asteroidRecycler.adapter = asteroidAdapter

        viewModel.navigateToDatabaseAsteroidDetails.observe(
            viewLifecycleOwner,
            Observer { _asteroid ->
                _asteroid?.let {
                    this.findNavController()
                        .navigate(MainFragmentDirections.actionShowDetail(_asteroid))
                    viewModel.displayAsteroidCompleted()
                }
            })

        viewModel.netWorkFailure.observe(viewLifecycleOwner, Observer { _message ->
            _message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadImageOfTheDay()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_all_menu -> return true
            R.id.show_rent_menu -> return true
            R.id.show_buy_menu -> return true
        }
        return false
    }

    private fun loadImageOfTheDay() {
        viewModel.pictureOfTheDay.observe(
            viewLifecycleOwner,
            Observer<PictureOfDay> { pictureOfDay ->
                Picasso.with(requireContext())
                    .load(pictureOfDay.url)
                    .placeholder(R.drawable.placeholder_picture_of_day)
                    .error(R.drawable.error_loading_image)
                    .into(activity_main_image_of_the_day)
            })
    }
}
