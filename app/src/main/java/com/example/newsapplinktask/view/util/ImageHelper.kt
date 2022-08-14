package com.qpn.kamashka.view.util

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.provider.OpenableColumns
import com.qpn.kamashka.util.network.UploadRequestBody
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.create
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException


object ImageHelper {
    private fun decodeFile(filePath: String?): Bitmap? {
        val bitmap = BitmapFactory.Options()
        bitmap.inJustDecodeBounds = true
        BitmapFactory.decodeFile(filePath, bitmap)

// The new size we want to scale to
        val REQUIRED_SIZE = 1024

// Find the correct scale value. It should be the power of 2.
        var width_tmp = bitmap.outWidth
        var height_tmp = bitmap.outHeight
        var scale = 1
        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE) break
            width_tmp /= 2
            height_tmp /= 2
            scale *= 2
        }

// Decode with inSampleSize
        val decodeSampleSize = BitmapFactory.Options()
        decodeSampleSize.inSampleSize = scale
        var image = BitmapFactory.decodeFile(filePath, decodeSampleSize)
        val exif: ExifInterface
        try {
            exif = ExifInterface(filePath!!)
            val exifOrientation: Int = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
            var rotate = 0f
            when (exifOrientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> rotate = 90f
                ExifInterface.ORIENTATION_ROTATE_180 -> rotate = 180f
                ExifInterface.ORIENTATION_ROTATE_270 -> rotate = 270f
            }
            if (rotate != 0f) {
                val w = image.width
                val h = image.height

                // Setting pre rotate
                val mtx = Matrix()
                mtx.preRotate(rotate)

                // Rotating Bitmap & convert to ARGB_8888, required by tess
                image = Bitmap.createBitmap(image, 0, 0, w, h, mtx, false)
            }
        } catch (e: IOException) {
            return null
        }
        return image.copy(Bitmap.Config.ARGB_8888, true)
    }


    fun convertFileToMultiPart(
        imagePath: String, name: String,
        uploadCallback: UploadRequestBody.UploadCallback
    ): MultipartBody.Part {
        val file = decodeFile(imagePath)
        val byteArrayOutputStream = ByteArrayOutputStream()
        file?.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
        // val requestFile = imageBytes.toRequestBody("image/*".toMediaTypeOrNull())
        val uploadRequestBody =
            UploadRequestBody("image/*".toMediaTypeOrNull(), File(imagePath), uploadCallback)
        return createFormData(name, "image", uploadRequestBody)
    }


    fun convertFileToMultiPart(imagePath: String, name: String): MultipartBody.Part {
        val file = decodeFile(imagePath)
        val byteArrayOutputStream = ByteArrayOutputStream()
        file?.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
        val requestFile = imageBytes.toRequestBody("image/*".toMediaTypeOrNull())
        return createFormData(name, name, requestFile)
    }

    fun convertFileToMultiPart(
        imageFile: File, name: String,
        uploadCallback: UploadRequestBody.UploadCallback
    ): MultipartBody.Part {
        val uploadRequestBody =
            UploadRequestBody("image/*".toMediaTypeOrNull(), imageFile, uploadCallback)
        return createFormData(name, "image", uploadRequestBody)
    }

    fun convertFileToMultiPart(image: File?): MultipartBody.Part {
        val file = decodeFile(image?.toString())
        val byteArrayOutputStream = ByteArrayOutputStream()
        file?.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
        val requestFile = imageBytes.toRequestBody("image/*".toMediaTypeOrNull())
        return createFormData("image", image?.toString(), requestFile)
    }


    fun convertVideoToMultiPart(
        path: String,
        name: String,
        uploadCallback: UploadRequestBody.UploadCallback
    )
            : MultipartBody.Part {
        val file = File(path)
        //  val requestBody = file.asRequestBody()
        val requestBody =
            UploadRequestBody("*/*".toMediaTypeOrNull(), file, callback = uploadCallback)
        return createFormData(name, file.name, requestBody)
    }


    fun ContentResolver.getFileName(fileUri: Uri): String {
        var name = ""
        val returnCursor = this.query(fileUri, null, null, null, null)
        if (returnCursor != null) {
            val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            name = returnCursor.getString(nameIndex)
            returnCursor.close()
        }
        return name
    }

//    // UPDATED!
//    fun getPath(uri: Uri?,): String? {
//        val projection = arrayOf(MediaStore.Video.Media.DATA)
//        val cursor: Cursor = getContentResolver().query(uri, projection, null, null, null)
//        return if (cursor != null) {
//            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
//            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
//            val column_index: Int = cursor
//                .getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
//            cursor.moveToFirst()
//            cursor.getString(column_index)
//        } else null
//    }

}