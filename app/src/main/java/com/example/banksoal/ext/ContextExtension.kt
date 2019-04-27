package com.example.banksoal.ext

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.provider.Settings
import com.example.banksoal.R
import java.io.IOException

fun Context.showLoadingDialog(): ProgressDialog {
    val progressDialog = ProgressDialog(this)
    progressDialog.show()
    if (progressDialog.window != null) {
        progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
    progressDialog.setContentView(R.layout.progress_dialog)
    progressDialog.isIndeterminate = true
    progressDialog.setCancelable(false)
    progressDialog.setCanceledOnTouchOutside(false)
    return progressDialog
}

@SuppressLint("HardwareIds")
fun Context.getDeviceId(): String {
    return Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
}

@Throws(IOException::class)
fun Context.loadJSONFromAsset(jsonFileName: String): String {
    val manager = this.assets
    val `is` = manager.open(jsonFileName)

    val size = `is`.available()
    val buffer = ByteArray(size)
    `is`.read(buffer)
    `is`.close()

    return String(buffer, charset("UTF-8"))
}