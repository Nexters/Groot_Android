package com.nexters.android.pliary.view.detail.edit

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.nexters.android.pliary.base.BaseFragment
import com.nexters.android.pliary.databinding.FragmentDiaryEditLayoutBinding
import com.nexters.android.pliary.view.util.photoPath
import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import com.nexters.android.pliary.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

internal class DiaryEditFragment : BaseFragment<DiaryEditViewModel>() {

    companion object{
        val TAG = this::class.java.simpleName
        const val REQUEST_PICK_FROM_ALBUM = 10
    }

    lateinit var binding : FragmentDiaryEditLayoutBinding

    override fun getModelClass(): Class<DiaryEditViewModel> = DiaryEditViewModel::class.java
    private val cardID : Long by lazy { arguments?.getLong("cardID") ?: 0L }
    private val diaryID : Long by lazy { arguments?.getLong("diaryID") ?: 0L }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_diary_edit_layout, container, false)
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode != RESULT_OK) return

        when(requestCode) {
            REQUEST_PICK_FROM_ALBUM -> {
                data?.let{ intent ->
                    intent.data?.let { viewModel.onSetPhotoView(context?.photoPath(it))} }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.vm = viewModel


        initView()
        initObserver()

        binding.ivDone.setOnClickListener {
            viewModel.onClickDone(cardID)
        }
        binding.ivBack.setOnClickListener { popBackStack() }
    }

    private fun initView() {

        if(diaryID > 0) {
            viewModel.localDataSource.diary(diaryID).observe(this, Observer {
                viewModel.diaryData = it
                viewModel.onSetPhotoView(it.photoUrl)
                viewModel.content.value = it.content
                viewModel.writeDate.value = it.date
            })

        }
    }

    private fun initObserver() {
        viewModel.addPhotoEvent.observe(this, Observer {
            checkPermission()
        })

        viewModel.clickDoneEvent.observe(this, Observer {
            hideKeyboard()
            popBackStack()
        })
    }

    private fun checkPermission() {
        TedPermission.with(context)
            .setPermissionListener(object: PermissionListener {
                override fun onPermissionGranted() {
                    // 권한 허용됨
                    startActivityForResult(Intent(Intent.ACTION_PICK).apply {
                        type = MediaStore.Images.Media.CONTENT_TYPE
                    }, REQUEST_PICK_FROM_ALBUM)
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    // 권한 거부됨
               }
            })
            .setRationaleMessage(R.string.permission_rational)
            .setDeniedMessage(R.string.permission_denied)
            .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
            .check()
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity?.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}