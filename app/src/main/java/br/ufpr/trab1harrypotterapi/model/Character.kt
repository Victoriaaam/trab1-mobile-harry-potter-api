package br.ufpr.trab1harrypotterapi.model

import com.google.gson.annotations.SerializedName

data class Character(
    val id: String,
    val name: String,
    val species: String?,
    val house: String?,
    val image: String?,
    @SerializedName("alternate_names")
    val alternateNames: List<String>? = emptyList(),
    val hogwartsStudent: Boolean,
    val hogwartsStaff: Boolean
)
