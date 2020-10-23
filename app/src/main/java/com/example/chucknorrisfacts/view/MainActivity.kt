package com.example.chucknorrisfacts.view


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.chucknorrisfacts.InternetConnectionHandler
import com.example.chucknorrisfacts.R
import com.example.chucknorrisfacts.adapter.ChuckFactAdapter
import com.example.chucknorrisfacts.dialogs.SearchDialog
import com.example.chucknorrisfacts.domain.model.ChuckFact
import com.example.chucknorrisfacts.services.ErrorBody
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import kotlinx.android.synthetic.main.activity_main.*
import java.net.ConnectException
import java.net.SocketTimeoutException

class MainActivity : AppCompatActivity() {


    private val viewModel by lazy {

        ViewModelProvider(this, MainViewModelFactory()).get(MainViewModel::class.java)

    }


    private val adapter by lazy {

        ChuckFactAdapter(ArrayList(), this)

    }
    private val internetConnectionHandler = InternetConnectionHandler(this)
    private val compoDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupListners()

        setupViews()


    }

    private fun setupListners() {
        compoDisposable += internetConnectionHandler.isConnected().subscribe { isConnected ->
            when (isConnected) {
                true -> onConnect()
                false -> onDisconnect()
            }
        }

    }


    private fun setupViews() {

        chuck_facts_list.adapter = adapter
    }


    private fun onConnect() {
        chuck_facts_list.visibility = View.VISIBLE
        sem_conexao_layout.visibility = View.GONE
    }


    private fun onDisconnect() {
        chuck_facts_list.visibility = View.GONE
        sem_conexao_layout.visibility = View.VISIBLE
    }

    private fun showDialog() {

        val dialog = SearchDialog(this)

        dialog.show { query ->
            compoDisposable += viewModel.search(query).observeOn(AndroidSchedulers.mainThread())
                .subscribe { state ->

                    handleStates(state)

                }
        }


    }

    private fun handleStates(searchState: SearchStates) {

        when (searchState) {

            is SearchStates.Loading -> {
                showLoading()
            }
            is SearchStates.Success -> {

                if (searchState.facts.isNotEmpty()) showSearchResult(searchState.facts)
                else noDataView()

            }
            is SearchStates.Error -> {
                showError(searchState.reason)
            }


        }
    }

    private fun showLoading() {

        prgs_circle.isVisible = true
        no_result_layout.isVisible = false
        chuck_facts_list.isVisible = false

    }

    private fun showSearchResult(chuckFatcs: List<ChuckFact>) {
        prgs_circle.isVisible = false
        showList()
        chuck_facts_list.adapter = ChuckFactAdapter(chuckFatcs, this)
    }

    private fun showError(reason: Throwable) {
        prgs_circle.isVisible = false

        when(reason){

            is SocketTimeoutException, is ConnectException -> Toast.makeText(this, "Falha na conexão. Tente novamente", Toast.LENGTH_SHORT).show()

            is ErrorBody -> Toast.makeText(this, reason.errorMessage, Toast.LENGTH_SHORT).show()

            else -> Toast.makeText(this, "Erro ao realizar pesquisa: $reason", Toast.LENGTH_SHORT).show()

        }

    }



    private fun showList() {
        no_result_layout.isVisible = false
        chuck_facts_list.isVisible = true
    }

    private fun noDataView() {
        prgs_circle.isVisible = false
        chuck_facts_list.isVisible = false
        no_result_layout.isVisible = true

    }

    private fun showNoConnectionError() {
        Toast.makeText(this, getString(R.string.msg_wifi_off), Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onStart() {
        super.onStart()
        internetConnectionHandler.register()

    }

    override fun onStop() {
        super.onStop()
        internetConnectionHandler.unregister()
        compoDisposable.clear()
        compoDisposable.dispose()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.menu_pesquisar -> showDialog()

        }

        return super.onOptionsItemSelected(item)
    }


}
