package com.example.realestate

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.realestate.base.BaseActivity
import com.example.realestate.databinding.ActivityMainBinding
import com.example.realestate.viewmodel.HomeViewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var viewModel: HomeViewModel

    override fun getLayoutId(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater, null, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_container
        ) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottonNavigationView.setupWithNavController(navController)

        setLiveDataListener()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // Flush all items in db if
        viewModel.flushItemsIntoDb()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    private fun setLiveDataListener(){
        with(viewModel){
            items.observe(this@MainActivity) { items ->
                //Check number of items in db
                Log.d("ItemCount", "${items.size}")
            }

            isLoading.observe(this@MainActivity) { flag ->
                // observe if is there any ongoing process before proceed to next action
                if(!flag){
                    viewModel.getAllItems()
                }
            }
        }
    }
}
