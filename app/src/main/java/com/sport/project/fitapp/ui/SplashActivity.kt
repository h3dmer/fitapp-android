package com.sport.project.fitapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.rbddevs.splashy.Splashy
import com.sport.project.fitapp.R
import com.sport.project.fitapp.ui.mainactivity.MainActivity
import com.tombayley.activitycircularreveal.CircularReveal
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    private lateinit var mActivityCircularReveal: CircularReveal

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val rootView: View = splashScreenLayout
        mActivityCircularReveal = CircularReveal(rootView)
        mActivityCircularReveal.onActivityCreate(intent)
        setSplashy()
    }

    private fun setSplashy() {
        Splashy(this)
            .setAnimation(Splashy.Animation.SLIDE_IN_TOP_BOTTOM)
            .setLogo(R.drawable.ic_pesa)
            .setTitle("Fit App")
            .setTitleColor(R.color.colorPrimaryDark)
            .setSubTitle("Your personal app")
            .setSubTitleColor(R.color.colorAccent)
            .setProgressColor(R.color.colorLigh)
            .setBackgroundResource(R.color.logoColor)
            .setFullScreen(true)
            .setTime(4000)
            .show()

        Splashy.onComplete(object : Splashy.OnComplete {
            override fun onComplete() {
                CircularReveal.presentActivity(CircularReveal.Builder(
                    this@SplashActivity,
                    splashScreenLayout,
                    Intent(this@SplashActivity, MainActivity::class.java),
                    0
                ))
            }
        })
    }
}
