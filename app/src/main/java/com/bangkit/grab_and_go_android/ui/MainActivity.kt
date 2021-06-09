package com.bangkit.grab_and_go_android.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.bangkit.grab_and_go_android.R
import com.bangkit.grab_and_go_android.data.Cart
import com.bangkit.grab_and_go_android.data.source.PaymentRepository
import com.bangkit.grab_and_go_android.data.vo.Resource
import com.bangkit.grab_and_go_android.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.net.URL
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainActivityViewModel>()
    private lateinit var navController: NavController

    private var statusIntent = 0

//    @Inject
//    lateinit var cartRepository: CartRepository
    @Inject
    lateinit var paymentRepository: PaymentRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent

//        lifecycleScope.launch {
//            val result = cartRepository.getCartItems(img)
//            Log.d("apicall", (result as Resource.Success).data.toString())
//        }
//        lifecycleScope.launch {
//            val cart = Cart(
//                id = 22,
//                total = 155999,
//                listCartItem = arrayListOf()
//            )
//            val result = paymentRepository.checkout(cart)
//            if(result is Resource.Success) {
//                val trans = result.data
//                val url = URL(trans.midtransUrl!!)
//                val uri = Uri.parse(trans.midtransUrl!!) // missing 'http://' will cause crashed
//                val intent = Intent(Intent.ACTION_VIEW, uri)
//                statusIntent += 1
//                startActivity(intent)
//            }
//        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController

        val signedInUser = viewModel.getSignedInUser()
        if(signedInUser == null) {
            val inflater = navHostFragment.navController.navInflater
            val graph = inflater.inflate(R.navigation.nav_graph)
            graph.startDestination = R.id.welcomeFragment
            navController.graph = graph
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navController.handleDeepLink(intent)
    }
}