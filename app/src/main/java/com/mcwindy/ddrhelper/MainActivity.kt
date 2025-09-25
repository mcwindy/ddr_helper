package com.mcwindy.ddrhelper

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.mcwindy.ddrhelper.databinding.ActivityMainBinding
import com.mcwindy.ddrhelper.overview.OverviewViewModel
import com.mcwindy.ddrhelper.store.DdnetPlayerDataCacheObject
import com.mcwindy.ddrhelper.store.SharedPreferencesUtils
import com.mcwindy.ddrhelper.ui.rank.RankViewModel
import com.mcwindy.ddrhelper.ui.server.ServerViewModel
import java.time.LocalDate


// TODO support change icon image with multi tee
class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"

        fun hideKeyboard(activity: AppCompatActivity) {
            val imm = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
        }
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val viewModel by lazy { ViewModelProvider(this)[OverviewViewModel::class.java] }
    private val rankViewModel by lazy { ViewModelProvider(this)[RankViewModel::class.java] }
    private val serverViewModel by lazy { ViewModelProvider(this)[ServerViewModel::class.java] }

    private var currentFrame = R.id.nav_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)


        val currentDate = LocalDate.now() // 获取当前日期
        val specifiedDate = LocalDate.of(2026, 1, 1) // 指定日期
        val comparisonResult = currentDate.compareTo(specifiedDate)
        if (comparisonResult >= 0) {
            Toast.makeText(
                this, "当前为测试版本，请至QQ群 638284446 获取更新版本", Toast.LENGTH_LONG
            ).show()
            Toast.makeText(
                this, "五秒后退出", Toast.LENGTH_LONG
            ).show()
            Handler(Looper.getMainLooper()).postDelayed({ // 延迟五秒后退出应用
                finish()
            }, 5000) // 5000 毫秒即 5 秒
        }

        val fab: FloatingActionButton = findViewById(R.id.fab)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController
        // val navController = findNavController(R.id.nav_host_fragment_content_main)


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_map,
                R.id.nav_friend,
                R.id.nav_clan,
                R.id.nav_rank,
                R.id.nav_server,
                R.id.nav_skin,
                R.id.nav_binding,
                R.id.nav_setting
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _: NavController?, destination: NavDestination, _: Bundle? ->
            when (destination.id) {
                R.id.nav_home -> {
                    fab.show()
                    fab.setOnClickListener { view ->
                        Snackbar.make(view, getString(R.string.refreshing), Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show()
                        // displayAd()
                        // loadAds() // TODO
                        viewModel.getPlayerData()
                    }
                }

                R.id.nav_rank -> {
                    fab.show()
                    fab.setOnClickListener { view ->
                        Snackbar.make(view, getString(R.string.refreshing), Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show()

                        rankViewModel.getRankData(this)
                    }
                }

                R.id.nav_server -> {
                    fab.show()
                    fab.setOnClickListener { view ->
                        Snackbar.make(view, getString(R.string.refreshing), Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show()

                        serverViewModel.getServerData()
                    }
                }

                else -> {
                    fab.hide()
                }

            }
            currentFrame = destination.id
            invalidateOptionsMenu()
        }

        // set shared preferences store
        // PlayerDataStore.sharedPref = getPreferences(Context.MODE_PRIVATE)
        SharedPreferencesUtils.init(this) // Pass context
        DdnetPlayerDataCacheObject.init(this)


        MobileAds.initialize(this)
        loadAds()
        viewModel.rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d(TAG, "Ad was clicked.")
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                Log.d(TAG, "Ad dismissed fullscreen content.")
                viewModel.rewardedAd = null
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                // Called when ad fails to show.
                Log.e(TAG, "Ad failed to show fullscreen content.")
                viewModel.rewardedAd = null
            }

            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d(TAG, "Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Ad showed fullscreen content.")
            }
        }
    }


    private fun loadAds() {
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(
            this,
            "ca-app-pub-3940256099942544/5224354917",
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, adError.toString())
                    viewModel.rewardedAd = null
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    Log.d(TAG, "Ad was loaded.")
                    viewModel.rewardedAd = ad
                }
            })
    }


    private fun displayAd() {
        viewModel.rewardedAd?.let { ad ->
            ad.show(this) { rewardItem ->
                // Handle the reward.
                val rewardAmount = rewardItem.amount
                val rewardType = rewardItem.type
                Log.d(TAG, "User earned the reward.")
            }
        } ?: run {
            Log.d(TAG, "The rewarded ad wasn't ready yet.")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Toast.makeText(this, "G!$item", Toast.LENGTH_SHORT).show()
        // Intent intent = new Intent(MyActivity.this, NewActivity.class);
        // startActivity(intent);

        when (item.itemId) {

            // R.id.action_settings -> {
            //     // supportFragmentManager.beginTransaction()
            //     //     .replace(
            //     //         R.id.nav_host_fragment_content_main,
            //     //         SettingFragment()
            //     //     ) // R.id.nav_setting
            //     //     .commit()
            //     // navController.navigate(R.id.nav_setting)
            //     return true
            // }
            //
            // R.id.action_preferences -> {
            //     // supportFragmentManager.beginTransaction()
            //     //     .replace(R.id.nav_host_fragment_content_main, Preference()) // R.id.nav_setting
            //     //     .commit()
            //     return true
            // }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        findViewById<TextView>(R.id.text_bar_player_name).text = SharedPreferencesUtils.playerName
        findViewById<TextView>(R.id.text_bar_player_clan).text = SharedPreferencesUtils.playerClan
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            hideKeyboard(this)
        }
        return super.dispatchTouchEvent(ev)
    }
}
