package com.thomas15v.moderation.storage.sql;

import com.thomas15v.moderation.storage.UserInfo;
import org.spongepowered.api.entity.player.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
public class SQLUser implements UserInfo {

    private UUID uniqueId;
    private String name;
    private Date firstJoined;
    private Date lastJoined;
    private List<SQLPunishment> banEntries = new ArrayList<SQLPunishment>();

    public SQLUser(User user) throws SQLException {
        this.uniqueId = user.getUniqueId();
        this.name = user.getName();
        this.firstJoined = new Date();
        this.lastJoined = new Date();
    }

    public SQLUser(ResultSet resultSet, User user) throws SQLException {
        this(resultSet, user.getUniqueId());

    }

    public SQLUser(ResultSet resultSet, UUID uniqueId) throws SQLException {
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
    public void setLastJoined() {
        lastJoined = new Date();
    }
}
