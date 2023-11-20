package com.example.appstory

import com.example.appstory.data.response.ListStoryItem
import com.example.appstory.data.response.StoryResponse

object Dump {
    fun generateDumpStoryResponse(): StoryResponse {
        val listStory: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val listStoryItem = ListStoryItem(
                createdAt = "2023-10-20T12:26:05Z",
                description = "Description $i",
                id = "id_$i",
                lat = i.toDouble() * 10,
                lon = i.toDouble() * 10,
                name = "Name $i",
                photoUrl = "https://images.unsplash.com/photo-1682687221175-fd40bbafe6ca?auto=format&fit=crop&q=80&w=1470&ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            )
            listStory.add(listStoryItem)
        }
        return StoryResponse(
            error = false, message = "Fetched successfully", listStory = listStory
        )
    }
}