package com.vodimobile.presentation.utils.name_validator

fun validateName(name: String): Boolean {
    return name.contains("+") ||
            name.contains("=") ||
            name.contains("-") ||
            name.contains("_") ||
            name.contains(")") ||
            name.contains("(") ||
            name.contains("*") ||
            name.contains("&") ||
            name.contains("?") ||
            name.contains("^") ||
            name.contains(":") ||
            name.contains("%") ||
            name.contains("$") ||
            name.contains(";") ||
            name.contains("#") ||
            name.contains("№") ||
            name.contains("@") ||
            name.contains("\"") ||
            name.contains("!") ||
            name.contains("~") ||
            name.contains("`") ||
            name.contains("{") ||
            name.contains("[") ||
            name.contains("]") ||
            name.contains("}") ||
            name.contains("'") ||
            name.contains("<") ||
            name.contains(">") ||
            name.contains(",") ||
            name.contains(".") ||
            name.contains("/") ||
            name.contains("|") ||
            name.contains("0") ||
            name.contains("1") ||
            name.contains("2") ||
            name.contains("3") ||
            name.contains("4") ||
            name.contains("5") ||
            name.contains("6") ||
            name.contains("7") ||
            name.contains("8") ||
            name.contains("9")
}
