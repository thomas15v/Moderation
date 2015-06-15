package com.thomas15v.moderation.storage;

import com.google.common.base.Optional;
import org.spongepowered.api.entity.player.User;

import java.util.UUID;

public interface Storage {

    public Optional<UserInfo> getUser(User user);

    public Optional<UserInfo> getUser(UUID id);

    public Optional<UserInfo> getUser(String name);

    public void updateUserInfo(UserInfo userInfo);

}
