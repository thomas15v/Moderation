package com.thomas15v.moderation.storage;

import java.util.Date;
import java.util.UUID;

public interface UserInfo {

    String getName();

    UUID getUniqueId();

    Date getFirstJoined();

    Date getLastJoined();

    /**
     * Sets the lastjoined time to the current time
     */
    void setLastJoined();
}
