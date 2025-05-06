package com.bignerdranch.android.movieapp

fun Double?.formatRating(): String {
    return if (this == null) "Н/Д"
    else if (this % 1 == 0.0) this.toInt().toString()
    else "%.1f".format(this).replace(".0", "")
}