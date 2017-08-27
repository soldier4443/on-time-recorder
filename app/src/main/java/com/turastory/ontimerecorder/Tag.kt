package com.turastory.ontimerecorder

import java.io.Serializable

data class Tag(val name: String) : Serializable {
    companion object {
        @JvmField val DEFAULT = Tag("default")
    }
}