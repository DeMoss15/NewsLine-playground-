package com.demoss.newsline.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.demoss.newsline.base.mvvm.BaseView
import com.demoss.newsline.base.mvvm.BaseViewModel
import io.reactivex.subjects.PublishSubject

abstract class BaseActivity<UserCommand, State, VM : BaseViewModel<UserCommand, State>>
    : AppCompatActivity(), BaseView<UserCommand, State, VM> {

    override val userCommands: PublishSubject<UserCommand> = PublishSubject.create()
    abstract val layoutResourceId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResourceId)
        subscribeToViewModel(this)
    }

    protected fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    protected fun hideKeyboard() {
        (getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow((currentFocus ?: View(this)).windowToken, 0)
    }
}