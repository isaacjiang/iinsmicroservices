package com.iins.applications;

public class ApplicationModel {

    private final long id;
    private final String content;

    public ApplicationModel(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
