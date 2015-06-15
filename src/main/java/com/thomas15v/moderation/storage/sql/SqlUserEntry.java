package com.thomas15v.moderation.storage.sql;

import com.thomas15v.moderation.storage.UserInfo;
import org.spongepowered.api.entity.player.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
public class SqlUserEntry implements UserInfo {

    private int id;
    private UUID uniqueId;
    private String name;
    private Date firstJoined;
    private Date lastJoined;
    private List<SqlPunishmentEntry> banEntries = new ArrayList<SqlPunishmentEntry>();

    public SqlUserEntry(ResultSet resultSet, User user) throws SQLException {
        this(resultSet);
        this.uniqueId = user.getUniqueId();
        this.name = user.getName();
    }

    public SqlUserEntry(ResultSet resultSet) throws SQLException {
        this.firstJoined = resultSet.getDate("firstJoined");
        this.lastJoined = resultSet.getDate("lastJoined");
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

    public void setLastJoined(Date lastJoined) {
        this.lastJoined = lastJoined;
    }
}
