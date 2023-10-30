package org.olympos.athena.cache.config;

import io.lettuce.core.ReadFrom;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@EnableConfigurationProperties({ RedisBaseProperties.class, RedisServerProperties.class})
public class RedisConfig {

	// 基础配置
	@Autowired
	private RedisBaseProperties redisBaseProperties;
	// 从服务器(哨兵服务器)列表
	@Autowired
	private List<RedisServerProperties> redisServerPropertiesList;

	/**
	 * 哨兵模式连接工厂
	 * @return LettuceConnectionFactory
	 */
	public LettuceConnectionFactory sentinelConnectionFactory() {

		// master服务器配置(地址 + 端口)
		String myMaster = redisBaseProperties.getMasterHost() + ":" + redisBaseProperties.getMasterPort();

		// 哨兵配置集合
		Set<String> sentinelHostAndPorts = redisServerPropertiesList.stream()
				.map( server -> server.getHost() + ":" + server.getPort())
				.collect( Collectors.toSet());

		RedisSentinelConfiguration sentinelConfiguration = new RedisSentinelConfiguration( myMaster, sentinelHostAndPorts);
		// 密码
		if ( StringUtils.isNotBlank( redisBaseProperties.getPassword()))
			sentinelConfiguration.setPassword( redisBaseProperties.getPassword());
		// 哨兵密码
		if ( StringUtils.isNotBlank( redisBaseProperties.getSentinelPassword()))
			sentinelConfiguration.setSentinelPassword( redisBaseProperties.getSentinelPassword());

		// 群集拓扑刷新选项
		ClusterTopologyRefreshOptions clusterTopologyRefreshOptions =  ClusterTopologyRefreshOptions.builder()
				.enablePeriodicRefresh()
				.enableAllAdaptiveRefreshTriggers()
				.refreshPeriod( Duration.ofSeconds( redisBaseProperties.getRefreshPeriod()))
				.build();

		// 集群客户端选项
		// 支持自适应集群拓扑刷新和静态刷新源
		ClusterClientOptions clusterClientOptions = ClusterClientOptions
				.builder()
				.topologyRefreshOptions( clusterTopologyRefreshOptions)
				.build();

		// 客户端配置
		LettuceClientConfiguration lettuceClientConfiguration = LettucePoolingClientConfiguration
				.builder()
				.poolConfig( this.genericObjectPoolConfig())
				.readFrom( ReadFrom.REPLICA_PREFERRED)
				.commandTimeout( Duration.ofMillis( redisBaseProperties.getTimeout()))
				.clientOptions( clusterClientOptions)
				.build();

		return new LettuceConnectionFactory( sentinelConfiguration, lettuceClientConfiguration);
	}

	/**
	 * 集群模式连接工厂
	 * @return LettuceConnectionFactory
	 */
	public LettuceConnectionFactory clusterConnectionFactory() {

		RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
		// 密码
		if ( StringUtils.isNotBlank( redisBaseProperties.getPassword()))
			clusterConfiguration.setPassword( redisBaseProperties.getPassword());

		// 群集拓扑刷新选项
		ClusterTopologyRefreshOptions clusterTopologyRefreshOptions =  ClusterTopologyRefreshOptions.builder()
				.enablePeriodicRefresh()
				.enableAllAdaptiveRefreshTriggers()
				.refreshPeriod( Duration.ofSeconds( redisBaseProperties.getRefreshPeriod()))
				.build();

		// 集群客户端选项
		// 支持自适应集群拓扑刷新和静态刷新源
		ClusterClientOptions clusterClientOptions = ClusterClientOptions
				.builder()
				.topologyRefreshOptions( clusterTopologyRefreshOptions)
				.build();

		// 客户端配置
		// 从优先，读写分离，读从可能存在不一致，最终一致性CP
		LettuceClientConfiguration lettuceClientConfiguration = LettucePoolingClientConfiguration
				.builder()
				.poolConfig( this.genericObjectPoolConfig())
				.readFrom( ReadFrom.REPLICA_PREFERRED)
				.commandTimeout( Duration.ofMillis( redisBaseProperties.getTimeout()))
				.clientOptions( clusterClientOptions)
				.build();

		return new LettuceConnectionFactory( clusterConfiguration, lettuceClientConfiguration);
	}

	/**
	 * 单服务器连接工厂
	 * @return LettuceConnectionFactory
	 */
	public LettuceConnectionFactory standaloneConnectionFactory() {

		// 服务器配置(地址 + 端口)
		RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration(
				redisBaseProperties.getMasterHost(),
				redisBaseProperties.getMasterPort());

		// 密码
		if ( StringUtils.isNotBlank( redisBaseProperties.getPassword()))
			standaloneConfiguration.setPassword( redisBaseProperties.getPassword());

		// 客户端配置
		LettuceClientConfiguration lettuceClientConfiguration = LettucePoolingClientConfiguration
				.builder()
				.poolConfig( this.genericObjectPoolConfig())
				.commandTimeout( Duration.ofMillis( redisBaseProperties.getTimeout()))
				.build();

		return new LettuceConnectionFactory( standaloneConfiguration, lettuceClientConfiguration);
	}

	/**
	 * 连接池配置
	 * @return GenericObjectPoolConfig
	 */
	private GenericObjectPoolConfig genericObjectPoolConfig() {

		GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
		genericObjectPoolConfig.setMaxIdle( redisBaseProperties.getPoolMaxIdle());
		genericObjectPoolConfig.setMinIdle( redisBaseProperties.getPoolMinIdle());
		genericObjectPoolConfig.setMaxTotal( redisBaseProperties.getPoolMaxTotal());
		genericObjectPoolConfig.setMaxWaitMillis( redisBaseProperties.getPoolMaxWait());

		return genericObjectPoolConfig;
	}
}