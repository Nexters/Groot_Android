package com.nexters.android.pliary.view.detail.edit

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.nexters.android.pliary.R
import com.nexters.android.pliary.base.BaseFragment
import com.nexters.android.pliary.databinding.FragmentDiaryEditLayoutBinding
import com.nexters.android.pliary.view.util.eventObserver
import com.nexters.android.pliary.view.util.photoPath

class DiaryEditFragment : BaseFragment<DiaryEditViewModel>() {

    companion object{
        const val REQUEST_PICK_FROM_ALBUM = 10
    }

    override fun getModelClass(): Class<DiaryEditViewModel> = DiaryEditViewModel::class.java

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentDiaryEditLayoutBinding>(inflater, R.layout.fragment_diary_edit_layout, container, false)
        binding.lifecycleOwner = this
        binding.vm = viewModel
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

        initView()
        initObserver()
    }

    private fun initView() {

    }

    private fun initObserver() {
        viewModel.addPhotoEvent.observe(this, Observer {
            checkPermission()
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
}