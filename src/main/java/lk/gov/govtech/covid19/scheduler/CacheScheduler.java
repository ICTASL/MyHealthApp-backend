package lk.gov.govtech.covid19.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CacheScheduler {

    @Autowired
    CacheManager cacheManager;

    @Scheduled(fixedRate = 300000)
    public void evictAllCaches() {
        cacheManager.getCacheNames().stream()
                .forEach(cacheName -> {
                    cacheManager.getCache(cacheName).clear();
                    log.info("Cleared cache: {}", cacheName);
                });
    }
}
