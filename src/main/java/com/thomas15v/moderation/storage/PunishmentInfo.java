package com.thomas15v.moderation.storage;

import com.google.common.base.Optional;

import java.util.UUID;

public interface PunishmentInfo {
    
    PunishType getType();

    Optional<UserInfo> getModerator();

    UUID getModeratorUniqueId();

    boolean isExpired();

    String getMessage();

}
