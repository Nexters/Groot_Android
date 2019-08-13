package com.nexters.android.pliary.view.util

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import java.util.ArrayList

fun Context.photoPath(uri : Uri): String {
    var photoPath = ""

    //val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

    val projection = arrayOf(MediaStore.MediaColumns.DATA)

    //val sortOrderDESC = MediaStore.Images.Media._ID + " COLLATE LOCALIZED DESC"

    val cursor = this.contentResolver.query(uri, projection, null, null, null)

    val columnIndexData = cursor!!.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)

    while(cursor.moveToNext()) {
        photoPath = cursor.getString(columnIndexData)
    }

    cursor.close()

    return photoPath
}