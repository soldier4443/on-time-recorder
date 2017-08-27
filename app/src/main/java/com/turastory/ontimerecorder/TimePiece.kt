package com.turastory.ontimerecorder

import java.io.Serializable

class TimePiece(val startTime: Long, val interval: Long, var tag: Tag)
    : Serializable