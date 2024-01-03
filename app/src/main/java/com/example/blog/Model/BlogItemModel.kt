package com.example.blog.Model

import android.os.Parcel
import android.os.Parcelable

data class BlogItemModel(
    val heading:String?="null",
    val post:String?="null",
    val likeCount:Int?=0,
    val profileImage:String?="null",
    val postId:String="null"


):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
        parcel.readString()

    )


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(heading)
        parcel.writeString(post)
        if (likeCount != null) {
            parcel.writeInt(likeCount)
        }
        parcel.writeString(profileImage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BlogItemModel> {
        override fun createFromParcel(parcel: Parcel): BlogItemModel {
            return BlogItemModel(parcel)
        }

        override fun newArray(size: Int): Array<BlogItemModel?> {
            return arrayOfNulls(size)
        }
    }

}
