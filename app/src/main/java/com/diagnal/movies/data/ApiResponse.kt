package com.diagnal.movies.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ApiResponse(

	@field:SerializedName("page")
	val page: Page? = null
) : Parcelable

@Parcelize
data class ContentItems(

	@field:SerializedName("content")
	val content: List<ContentItem>
) : Parcelable

@Parcelize
data class ContentItem(
	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("poster-image")
	val posterImage: String? = null
) : Parcelable

@Parcelize
data class Page(

	@field:SerializedName("page-num")
	val pageNum: String? = null,

	@field:SerializedName("page-size")
	val pageSize: String? = null,

	@field:SerializedName("content-items")
	val contentItems: ContentItems? = null,

	@field:SerializedName("total-content-items")
	val totalContentItems: String? = null,

	@field:SerializedName("title")
	val title: String? = null
) : Parcelable
