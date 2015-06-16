package com.thomas15v.moderation.storage.sql;

import com.thomas15v.moderation.storage.PunishType;
import com.thomas15v.moderation.storage.PunishmentInfo;
import com.thomas15v.moderation.storage.UserInfo;
import org.spongepowered.api.entity.player.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
public class SQLUser implements UserInfo {

    private UUID uniqueId;
    private String name;
    private Date firstJoined;
    private Date lastJoined;
    private SQLStorage sqlStorage;

    public SQLUser(ResultSet resultSet, User user, SQLStorage sqlStorage) throws SQLException {
        this(resultSet, user.getUniqueId(), sqlStorage);
    }

    public SQLUser(ResultSet resultSet, UUID uniqueId, SQLStorage sqlStorage) throws SQLException {
        this.sqlStorage = sqlStorage;
        this.name = resultSet.getString("lastname");
        this.firstJoined = resultSet.getDate("firstJoined");
        this.lastJoined = resultSet.getDate("lastSeen");
        this.uniqueId = uniqueId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public Date getFirstJoined() {
        return firstJoined;
    }

    @Override
    public Date getLastJoined() {
        return lastJoined;
    }

    @Override
    public void resetLastJoined() {
        lastJoined = new Date();
    }

    @Override
    public boolean isBanned() {
        for (PunishmentInfo info : getPunishmentHistory())
            if (info.getType() == PunishType.BAN && info.isExpired())
                return true;
        return false;
    }



    @Override
    public List<PunishmentInfo> getPunishmentHistory() {
        return sqlStorage.getPunishments(uniqueId);
    }
}
