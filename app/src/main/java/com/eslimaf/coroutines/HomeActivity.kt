package com.eslimaf.coroutines

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.eslimaf.coroutines.ui.timeline.TimelineFragment
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

class HomeActivity : AppCompatActivity() {

    private val firebaseRemoteConfig by lazy {
        val configSettings =
            FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build()

        FirebaseRemoteConfig.getInstance().apply {
            setConfigSettings(configSettings)
            setDefaults(R.xml.remote_config_defaults)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TimelineFragment.newInstance())
                .commit()
        }
    }

    override fun onResume() {
        super.onResume()
        firebaseRemoteConfig.fetch().addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(this@HomeActivity, "Fetch succeeded", Toast.LENGTH_SHORT).show()
                firebaseRemoteConfig.activateFetched()
            } else {
                Toast.makeText(this@HomeActivity, "Fetch failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
