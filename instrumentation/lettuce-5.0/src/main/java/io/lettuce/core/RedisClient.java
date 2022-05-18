package io.lettuce.core;

import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.codec.RedisCodec;
import io.lettuce.core.resource.ClientResources;

import java.time.Duration;

@Weave(originalName = "io.lettuce.core.RedisClient")
public abstract class RedisClient extends AbstractRedisClient {

    protected RedisClient(ClientResources clientResources, RedisURI redisURI) {
        super(clientResources);
    }

    public static RedisClient create(String uri) {
        return Weaver.callOriginal();
    }

    public abstract StatefulRedisConnection<String, String> connect();

    private final RedisURI redisURI = Weaver.callOriginal();

    protected <K, V> StatefulRedisConnectionImpl<K, V> newStatefulRedisConnection(RedisChannelWriter channelWriter,
            RedisCodec<K, V> codec, Duration timeout) {
        StatefulRedisConnectionImpl<K, V> connection = Weaver.callOriginal();
        connection.redisURI = redisURI;
        return connection;
    }
}
