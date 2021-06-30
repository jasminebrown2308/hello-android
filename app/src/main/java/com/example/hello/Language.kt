package com.example.hello

enum class Language {
    Select, English, Spanish, German, Dutch, French, Italian;

    fun translate(): String {
        return when (this) {
            Select, English -> "Hello"
            Spanish -> "¡Hola"
            German, Dutch -> "Hallo"
            French -> "Bonjour"
            Italian -> "Ciao"
        }
    }

    fun image(): Int {
        return when (this) {
            Select -> R.drawable.no_image
            English -> R.drawable.england
            Spanish -> R.drawable.spain
            German -> R.drawable.germany
            Dutch -> R.drawable.netherlands
            French -> R.drawable.france
            Italian -> R.drawable.italy
        }
    }

}