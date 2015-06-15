package com.thomas15v.moderation.storage.sql;

import com.google.common.base.Optional;
import com.thomas15v.moderation.storage.Storage;
import com.thomas15v.moderation.storage.UserInfo;
import org.spongepowered.api.entity.player.User;
import org.spongepowered.api.service.sql.SqlService;

import javax.sql.DataSource;
import java.sql.*;
import java.util.UUID;

public class SQLStorage implements Storage {

    public DataSource dataSource;

    public SQLStorage(SqlService sqlService){
        try {
            this.dataSource = sqlService.getDataSource("jdbc:mysql://root:root@localhost:3306/moderation");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private final static String SELECT_USER_UUID = "select * from Users where uniqueId=?";

    @Override
    public Optional<UserInfo> getUser(User user){
        Optional<UserInfo> userInfo = getUser(user.getUniqueId());
        if (!userInfo.isPresent()) {
            insertUserInfo(user);
            return getUser(user);
        }
        return userInfo;
    }

    @Override
    public Optional<UserInfo> getUser(UUID id){
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(SELECT_USER_UUID);
            statement.setString(1, id.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                UserInfo userInfo = new SQLUser(resultSet, id);
                statement.close();
                return Optional.of(userInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.absent();
    }

    private final static String SELECT_USER_NAME = "select * from Users where lastname=?";

    @Override
    public Optional<UserInfo> getUser(String name){
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(SELECT_USER_NAME);
            statement.setString(1, name);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.absent();
    }

    private final static String UPDATE_USER = "update Users set lastname=?, lastSeen=? where uniqueId=?";

    @Override
    public void updateUserInfo(UserInfo userInfo) {
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(UPDATE_USER);
            statement.setString(1, userInfo.getName());
            statement.setString(3, userInfo.getUniqueId().toString());
            statement.setTimestamp(2, new Timestamp(userInfo.getLastJoined().getTime()));
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private final static String INSERT_USER = "insert into Users (uniqueId, lastname) values(?,?) on DUPLICATE key update lastname=?";

    private void insertUserInfo(User userInfo){
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(INSERT_USER);
            statement.setString(1, userInfo.getUniqueId().toString());
            statement.setString(2, userInfo.getName());
            statement.setString(3, userInfo.getName());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}