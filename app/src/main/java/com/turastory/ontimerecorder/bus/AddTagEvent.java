package com.turastory.ontimerecorder.bus;

/**
 * Created by soldi on 2017-08-29.
 */

public class AddTagEvent {
    private String tagName;

    public AddTagEvent(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }
}
