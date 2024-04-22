package com.example.fitjournal.core.domain.util.filtering

import java.util.SortedMap

/**
 * Filters the list for the specified string
 * Then groups the list according to the first character.
 * Finally, converts to a sorted map and returns that map
 **/
fun searchForText(text: String, list: List<String>): SortedMap<Char, List<String>> {
    return list.filter {
        it.lowercase().contains(text.lowercase())
    }.groupBy {
        it.first()
    }.toSortedMap()
}
