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
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(SELECT_USER_UUID);
            statement.setString(1, user.getUniqueId().toString());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                insertUserInfo(user);
                return getUser(user);
            }else {
                UserInfo userInfo = new SqlUserEntry(resultSet, user);
                statement.close();
                updateUserInfo(userInfo);
                return Optional.of(userInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.absent();
    }

    @Override
    public Optional<UserInfo> getUser(UUID id){
        return null;
    }

    private final static String SELECT_USER_NAME = "select * from Users where name='%s'";

    @Override
    public Optional<UserInfo> getUser(String name){
        return null;
    }

    private final static String UPDATE_USER = "update Users set lastname=?, lastJoined=now() where uniqueId=?";

    @Override
    public void updateUserInfo(UserInfo userInfo) {
        PreparedStatement statement = null;
        try {
            statement = dataSource.getConnection().prepareStatement(UPDATE_USER);
            statement.setString(1, userInfo.getName());
            statement.setString(2, userInfo.getUniqueId().toString());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private final static String INSERT_USER = "insert into Users (uniqueId, lastname) values(?,?)";

    private void insertUserInfo(User userInfo){
        try {
            PreparedStatement statement = dataSource.getConnection().prepareStatement(INSERT_USER);
            statement.setString(1, userInfo.getUniqueId().toString());
            statement.setString(2, userInfo.getName());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
