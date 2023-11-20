package com.example.appstory.data.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class PostResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
		parcel.readString()
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeValue(error)
		parcel.writeString(message)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<PostResponse> {
		override fun createFromParcel(parcel: Parcel): PostResponse {
			return PostResponse(parcel)
		}

		override fun newArray(size: Int): Array<PostResponse?> {
			return arrayOfNulls(size)
		}
	}
}
