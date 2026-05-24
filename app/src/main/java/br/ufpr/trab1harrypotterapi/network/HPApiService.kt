package br.ufpr.trab1harrypotterapi.network

import br.ufpr.trab1harrypotterapi.model.Character
import br.ufpr.trab1harrypotterapi.model.Spell
import retrofit2.http.GET
import retrofit2.http.Path

interface HPApiService {
    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: String): List<Character>

    @GET("characters/staff")
    suspend fun getStaff(): List<Character>

    @GET("characters/house/{house}")
    suspend fun getCharactersByHouse(@Path("house") house: String): List<Character>

    @GET("spells")
    suspend fun getSpells(): List<Spell>
}
