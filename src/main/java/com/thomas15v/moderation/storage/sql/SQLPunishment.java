package com.thomas15v.moderation.storage.sql;

import com.google.common.base.Optional;
import com.thomas15v.moderation.storage.PunishType;
import com.thomas15v.moderation.storage.PunishmentInfo;
import com.thomas15v.moderation.storage.UserInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

public class SQLPunishment implements PunishmentInfo {

    private final PunishType type;
    private final UUID moderator;
    private Optional<UserInfo> moderatorInfo = Optional.absent();
    private final Date expires;
    private final String message;
    private SQLStorage sqlStorage;

    public SQLPunishment(PunishType type, UUID moderator, Date Expire, String message, SQLStorage sqlStorage){
        this.type = type;
        this.moderator = moderator;
        this.expires = Expire;
        this.message = message;
        this.sqlStorage = sqlStorage;
    }

    public SQLPunishment(ResultSet resultSet, SQLStorage sqlStorage) throws SQLException {
        this.sqlStorage = sqlStorage;
        type = PunishType.valueOf(resultSet.getString("type"));
        moderator = UUID.fromString(resultSet.getString("moderatorId"));
        expires = resultSet.getDate("expires");
        message = resultSet.getString("message");
        System.out.println(isExpired());
    }

    @Override
    public PunishType getType() {
        return type;
    }

    @Override
    public Optional<UserInfo> getModerator() {
        if (!moderatorInfo.isPresent())
            this.moderatorInfo = sqlStorage.getUser(moderator);
        return moderatorInfo;
    }

    @Override
    public UUID getModeratorUniqueId() {
        return moderator;
    }

    @Override
    public boolean isExpired() {
        return expires.before(new Date());
    }

    @Override
    public String getMessage() {
        return message;
    }
}
