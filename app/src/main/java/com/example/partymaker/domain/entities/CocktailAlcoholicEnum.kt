package com.example.partymaker.domain.entities

enum class CocktailAlcoholicEnum {
    Alcoholic, NonAlcoholic, OptionalAlcohol, All;

    companion object {
        fun getEnumByString(cocktail: String):CocktailAlcoholicEnum {
            return when (cocktail) {
                "Alcoholic" -> Alcoholic
                "Non alcoholic" -> NonAlcoholic
                "Optional alcohol" -> OptionalAlcohol
                else -> All
            }
        }

        fun enumToString(alcoholicEnum: CocktailAlcoholicEnum): String {
            return when (alcoholicEnum) {
                Alcoholic -> "Alcoholic"
                NonAlcoholic -> "Non Alcoholic"
                OptionalAlcohol -> "Optional Alcohol"
                else -> "All"
            }
        }
    }

}
