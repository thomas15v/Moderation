package com.thomas15v.moderation;

import com.thomas15v.moderation.storage.Storage;
import com.thomas15v.moderation.storage.UserInfo;
import com.thomas15v.moderation.storage.sql.SQLStorage;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerJoinEvent;
import org.spongepowered.api.event.entity.player.PlayerQuitEvent;
import org.spongepowered.api.event.state.ServerStartedEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.ban.BanService;
import org.spongepowered.api.service.sql.SqlService;

@Plugin(id="Moderation", name ="Moderation", version = "0.1")
public class ModerationPlugin {

    private Storage storage;

    @Subscribe
    public void onEnable(ServerStartedEvent event){
        System.out.println(event.getGame().getServiceManager().provide(BanService.class).isPresent());
        this.storage = new SQLStorage(event.getGame().getServiceManager().provide(SqlService.class).get());
    }

    @Subscribe
    public void onPlayerJoin(PlayerJoinEvent event){
        storage.getUser(event.getEntity());
    }

    @Subscribe
    public void onPlayerLeave(PlayerQuitEvent event){
        UserInfo userInfo = storage.getUser(event.getEntity()).get();
        userInfo.setLastJoined();
        storage.updateUserInfo(userInfo);
    }

}
