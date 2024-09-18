package com.example.songhub.model

import com.google.gson.annotations.SerializedName
import retrofit2.http.Query

data class LastFmResponse(
    val results: Results
)

data class Queryy(
    @SerializedName("#text")
    val text: String,
    val role: String,
    val startPage: String
)


data class Results(
    @SerializedName("opensearch:Query")
    val query: Queryy,
    @SerializedName("opensearch:totalResults")
    val totalResults: String,
    @SerializedName("opensearch:startIndex")
    val startIndex: String,
    @SerializedName("opensearch:itemsPerPage")
    val itemsPerPage: String,
    val trackmatches: Trackmatches
)


data class Trackmatches(
    val track: List<Track>
)

data class Track(
    val name: String,
    val artist: String, // Changed to String
    val url: String,
    val streamable: String,
    val listeners: String,
    val image: List<Image>,
    val mbid: String
)


data class Artist(
    val name: String
)

data class Image(
    @SerializedName("#text")
    val text: String,
    val size: String
)

