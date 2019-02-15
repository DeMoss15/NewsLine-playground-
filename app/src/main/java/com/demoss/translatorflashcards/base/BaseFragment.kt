package com.demoss.translatorflashcards.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.demoss.translatorflashcards.base.mvvm.BaseView
import com.demoss.translatorflashcards.base.mvvm.BaseViewModel
import io.reactivex.subjects.PublishSubject

abstract class BaseFragment<UserCommand, State, VM : BaseViewModel<UserCommand, State>>
    : Fragment(), BaseView<UserCommand, State, VM> {

    override val userCommands: PublishSubject<UserCommand> = PublishSubject.create()
    abstract val layoutResourceId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeToViewModel(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(layoutResourceId, container, false)
    }

    protected fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    protected fun hideKeyboard() {
        (activity?.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow((activity?.currentFocus ?: View(context)).windowToken, 0)
    }
}