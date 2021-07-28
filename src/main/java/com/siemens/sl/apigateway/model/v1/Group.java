package com.siemens.sl.apigateway.model.v1;

public class Group {

    private long id;
    private String name;
    private String description;
    private boolean locked;
    private long session_timeout;

    public Group(long id, String name, String description, boolean locked, long session_timeout) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.locked = locked;
        this.session_timeout = session_timeout;
    }

    public long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isLocked() {
        return locked;
    }

    public long getSessionTimeout() {
        return session_timeout;
    }
}
