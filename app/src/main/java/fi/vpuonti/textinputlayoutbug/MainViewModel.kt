package fi.vpuonti.textinputlayoutbug

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull

@ExperimentalCoroutinesApi
class MainViewModel : ViewModel() {

    companion object {
        const val TAG = "MainViewModel"
    }

    private var mSearchJob: Job? = null

    private val mSearchState: MutableStateFlow<SearchState?> = MutableStateFlow(null)
    val searchState: Flow<SearchState> = mSearchState.filterNotNull()

    fun search(query: String) {
        Log.d(TAG, "Search $query")
        mSearchState.value = SearchState.Loading
        viewModelScope.launch {
            // cancel previous search
            mSearchJob?.cancelAndJoin()
            mSearchJob = launch {
                // fake query
                delay(1000)
                mSearchState.value = SearchState.Success
            }
        }
    }

    sealed class SearchState {
        object Loading : SearchState()
        object Success : SearchState()

        override fun toString(): String {
            return when (this) {
                Loading -> "Loading"
                Success -> "Success"
            }
        }
    }
}