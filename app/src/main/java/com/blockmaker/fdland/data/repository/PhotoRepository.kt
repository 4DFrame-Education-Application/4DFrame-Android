package com.blockmaker.fdland.data.repository

import android.net.Uri

object PhotoRepository {
    val photoUris = mutableListOf<Uri>()

    fun clear() {
        photoUris.clear()
    }
}
