package com.emlakjet.ismaildemirler.productservice.util.cache;

import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.cache.Cache;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CacheManagement {

	private final CacheManager cacheManager;

    public void refreshCache(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        }
    }
}
