package devarea.fr.discord.cache.cached_entity;

import devarea.fr.discord.DevArea;
import devarea.fr.discord.cache.ChannelCache;
import devarea.fr.discord.entity.Chan;
import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.channel.GuildChannel;

public class CachedChannel extends CachedObject<Chan> {

    public CachedChannel(GuildChannel channel, long last_fetch) {
        super(Chan.of(channel), last_fetch);
    }

    public CachedChannel(Chan channel, long last_fetch) {
        super(channel, last_fetch);
    }

    public CachedChannel(String channelID) {
        super(channelID);
    }

    @Override
    public Chan fetch() {
        try {
            this.object_cached = Chan.of(DevArea.devarea.getChannelById(Snowflake.of(this.object_id)).block());
        } catch (Exception e) {
            System.err.println("ERROR: Channel couldn't be fetched !");
            this.object_cached = null;
        }

        if (this.object_cached == null) {
            ChannelCache.slash(this.object_id);
            return null;
        }

        this.last_fetch = System.currentTimeMillis();
        return this.object_cached;
    }
}
