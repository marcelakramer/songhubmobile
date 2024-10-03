package com.example.songhub.DAO

import com.example.songhub.model.LastFmResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFmApiService {
    @GET("2.0/")
    suspend fun searchTracks(
        @Query("method") method: String = "track.search",
        @Query("track") trackName: String,
        @Query("api_key") apiKey: String,
        @Query("format") format: String = "json"
    ): LastFmResponse
}
