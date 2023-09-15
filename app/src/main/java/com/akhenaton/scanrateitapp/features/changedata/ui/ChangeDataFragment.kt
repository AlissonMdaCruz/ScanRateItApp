package com.akhenaton.scanrateitapp.features.changedata.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.akhenaton.scanrateitapp.common.BaseFragment
import com.akhenaton.scanrateitapp.databinding.FragmentChangeDataBinding

class ChangeDataFragment : BaseFragment<FragmentChangeDataBinding>() {
    override fun initView() {
        //
    }

    override fun viewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChangeDataBinding = FragmentChangeDataBinding.inflate(inflater, container, false)
}
