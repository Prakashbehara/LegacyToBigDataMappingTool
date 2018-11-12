package com.mycompany.myapp.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.mycompany.myapp.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Application.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Application.class.getName() + ".feeds", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Application.class.getName() + ".dataAssets", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.DataModel.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.DataModel.class.getName() + ".dataModelMappings", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.DataModelMapping.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.DataAsset.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.DataAsset.class.getName() + ".feeds", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.SourceFeedMapping.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.SourceFeedMapping.class.getName() + ".dataAssets", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.SourceDatabaseMapping.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Application.class.getName() + ".sourceFeeds", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Application.class.getName() + ".sourceDatabaseMappings", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.SourceFeed.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.SourceFeed.class.getName() + ".sourceFeedMappings", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.DataAsset.class.getName() + ".sourceFeeds", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.SourceDatabase.class.getName(), jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.SourceDatabase.class.getName() + ".sourceDatabaseMappings", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.Application.class.getName() + ".sourceDatabases", jcacheConfiguration);
            cm.createCache(com.mycompany.myapp.domain.DataAsset.class.getName() + ".sourceDatabases", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
