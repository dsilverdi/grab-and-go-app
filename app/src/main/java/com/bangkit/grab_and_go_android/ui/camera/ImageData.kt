package com.bangkit.grab_and_go_android.ui.camera

import android.os.Parcel
import android.os.Parcelable

data class ImageData(
    val width: Int,
    val height: Int,
    val imageFormat1: Int,
    val imageFormat2: Int,
    val imagePlanes1Size: Int,
    val imagePlanes2Size: Int,
    val rotationDegree: Int,
    val timestamp: Long,
    val cropRectWidth: Int,
    val cropRectHeight: Int,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readLong(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(width)
        parcel.writeInt(height)
        parcel.writeInt(imageFormat1)
        parcel.writeInt(imageFormat2)
        parcel.writeInt(imagePlanes1Size)
        parcel.writeInt(imagePlanes2Size)
        parcel.writeInt(rotationDegree)
        parcel.writeLong(timestamp)
        parcel.writeInt(cropRectWidth)
        parcel.writeInt(cropRectHeight)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ImageData> {
        override fun createFromParcel(parcel: Parcel): ImageData {
            return ImageData(parcel)
        }

        override fun newArray(size: Int): Array<ImageData?> {
            return arrayOfNulls(size)
        }
    }
}
