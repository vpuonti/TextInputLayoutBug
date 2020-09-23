package fi.vpuonti.textinputlayoutbug

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import fi.vpuonti.textinputlayoutbug.databinding.ActivityMainBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!

    private val mViewModel: MainViewModel by viewModels()


    init {
        lifecycleScope.launchWhenResumed {
            mViewModel.searchState.collect { searchState ->
                with(binding) {
                    Log.d(TAG, "Got result: $searchState")
                    textState.text = "Search state: $searchState"
                    val isLoading = searchState is MainViewModel.SearchState.Loading
                    inputLoading.setLoading(isLoading)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            inputLoading.setOnTextChangeListener {
                mViewModel.search(it)
            }
        }
    }

}