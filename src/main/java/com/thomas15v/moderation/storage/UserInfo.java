package com.thomas15v.moderation.storage;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface UserInfo {

    String getName();

    UUID getUniqueId();

    Date getFirstJoined();

    Date getLastJoined();

    /**
     * Sets the lastjoined time to the current time
     */
    void resetLastJoined();

    boolean isBanned();

    List<PunishmentInfo> getPunishmentHistory();

    public void punish(PunishmentInfo punishmentInfo);
}
