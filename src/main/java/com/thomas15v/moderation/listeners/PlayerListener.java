package com.thomas15v.moderation.listeners;

import com.thomas15v.moderation.storage.Storage;
import com.thomas15v.moderation.storage.UserInfo;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerJoinEvent;
import org.spongepowered.api.event.entity.player.PlayerQuitEvent;

public class PlayerListener {

    private Storage storage;

    public PlayerListener(Storage storage){
        this.storage = storage;
    }

    @Subscribe
    public void onPlayerJoin(PlayerJoinEvent event) {
        storage.getUser(event.getEntity()).get().getPunishmentHistory();
    }

    @Subscribe
    public void onPlayerLeave(PlayerQuitEvent event){
        UserInfo userInfo = storage.getUser(event.getEntity()).get();
        userInfo.resetLastJoined();
        storage.updateUserInfo(userInfo);
    }

}
