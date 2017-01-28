package com.kfnoon.huanm.aribbble

import android.app.Activity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash.*
import android.content.Intent
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.os.Handler


class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.activity_splash)

        val logoAnimation = AnimationUtils.loadAnimation(this,R.anim.splash)
        logoAnimation.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation) {
            }

            override fun onAnimationRepeat(animation: Animation) {
            }

            override fun onAnimationEnd(animation: Animation) {
                Thread(Runnable {
                    startActivity(Intent(this@SplashActivity, Init::class.java))
                    finish()
                }).start()
            }
        })
        splashLogo.startAnimation(logoAnimation)
    }
}

