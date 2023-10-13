package com.mcwindy.ddrhelper.model

class ClassifiedServerList<T>(
    private var classifierTitle: String,
    private var classifierItems: List<T>,
    var isOpened: Boolean = false
) : Map.Entry<String, List<T>> {

    override val key: String
        get() = classifierTitle

    override val value: List<T>
        get() = classifierItems
}
