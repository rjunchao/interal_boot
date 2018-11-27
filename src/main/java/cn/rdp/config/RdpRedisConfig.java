package cn.rdp.config;

import java.time.Duration;

import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import cn.rdp.base.BaseDO;
import lombok.extern.slf4j.Slf4j;


/**
* author:rjc 	
*  email: rjunchao@126.com
*   date: 2018年11月14日 下午1:08:37
*   desc: 
*/
@Slf4j
@Configuration
public class RdpRedisConfig extends CachingConfigurerSupport{
	
	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		return new JedisConnectionFactory();
	}
	
	
    @Primary//设置默认的bean
    @Bean
    public RedisCacheManager doCacheManager(RedisConnectionFactory connectionFactory) {
    	RedisCacheConfiguration rccf = RedisCacheConfiguration.defaultCacheConfig();
    	rccf.entryTtl(Duration.ofDays(1));//
    	rccf.disableCachingNullValues();
    	//使用json
    	rccf.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer<>(BaseDO.class)));
        RedisCacheManager cacheManager = RedisCacheManager.builder(connectionFactory).cacheDefaults(rccf).build();
        log.info("创建缓存管理器 cacheManager");
        return cacheManager;
    }
    
    
    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
    	CacheErrorHandler ceh = new CacheErrorHandler() {
			@Override
			public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
				 log.info("handleCachePutError。。。。");
				
			}
			
			@Override
			public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
				 log.info("handleCacheGetError。。。。。。。。。。。。");
				
			}
			
			@Override
			public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
				 log.info("handleCacheEvictError。。。。。。。。。。。。");
			}
			
			@Override
			public void handleCacheClearError(RuntimeException exception, Cache cache) {
				 log.info("handleCacheClearError。。。。。。。。。。。。");
			}
		};
		return ceh;
    }
	
	/**
	 * 使用josn来序列化对象
	 * @param redisConnectionFactory
	 * @return 
	 *//*
	@Bean
	public RedisTemplate<String, BaseDO> doRedisConfig(RedisConnectionFactory redisConnectionFactory){
		RedisTemplate<String, BaseDO> template = new RedisTemplate<>();
		Jackson2JsonRedisSerializer<BaseDO> serializer = new Jackson2JsonRedisSerializer<>(BaseDO.class);
		template.setConnectionFactory(redisConnectionFactory);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		serializer.setObjectMapper(om );
		template.setKeySerializer(new StringRedisSerializer());//key 为字符串格式
		template.setValueSerializer(serializer);//vlaue 序列化为json格式
		return template;
	}
	*/
}
