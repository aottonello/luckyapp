package com.lucky.app.utils

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lucky.app.R
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

private const val FIVE_HUNDRED = 500L
private const val ANIMATION_DURATION = 200L

fun View.removeFromParent() {
    this.parent?.let {
        (it as ViewGroup).removeView(this)
    }
}

/**
 * Load image  from url.
 * While load show place holder using Glide Library
 */
fun ImageView.loadImageFromUrl(
    url: String,
    placeholder: Int = R.drawable.ic_place_holder_outline,
    context: Context
) {
    Glide.with(context)
        .load(url)
        .placeholder(placeholder)
        .into(this)
}

/**
 * Set vertical type layoutManager to RecyclerView
 */
fun RecyclerView.setVerticalLayoutManager() {
    this.layoutManager =
        LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )
}

/**
 * Prevents several consecutive clicks being executed
 */
inline fun View.executeAfterOnClickListenerWithoutRepeated(crossinline f: () -> Unit): Disposable {
    return this.setOnClickListenerWithoutRepeated()
        .subscribe(
            {
                f()
            },
            Throwable::printStackTrace
        )
}

fun View.setOnClickListenerWithoutRepeated(): Observable<Any> {
    val subject: PublishSubject<Any> = PublishSubject.create()
    setOnClickListener { subject.onNext(Unit) }
    return subject
        .throttleFirst(FIVE_HUNDRED, TimeUnit.MILLISECONDS, Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

/**
 * Create image animation
 * Type: zoom in - zoom out
 * Duration: 200 milliseconds
 */
fun setAnimationImage(context: Context, imgSecond: ImageView) {
    val animZoomIn = AnimationUtils.loadAnimation(context, R.anim.zoom_in)
    val animZoomOut = AnimationUtils.loadAnimation(context, R.anim.zoom_out)
    imgSecond.visibility = View.VISIBLE
    Single.fromCallable {}
        .delay(ANIMATION_DURATION, TimeUnit.MILLISECONDS)
        .applySchedulers()
        .doOnSubscribe {
            imgSecond.startAnimation(animZoomOut)
        }
        .doOnSuccess {
            imgSecond.visibility = View.GONE
            imgSecond.startAnimation(animZoomIn)
        }
        .subscribe()
        .dispose()
}

