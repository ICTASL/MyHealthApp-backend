package lk.gov.govtech.covid19.scheduler;

import lk.gov.govtech.covid19.controller.NotificationController;
import lk.gov.govtech.covid19.service.NotificationService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collection;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) //avoiding authentication for tests
class CacheSchedulerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    @InjectMocks
    private CacheScheduler cacheScheduler;

    @Mock
    private CacheManager cacheManager;

    @Test
    void evictAllCaches() {
        //FIXME this can be improved
        cacheScheduler.evictAllCaches();
        Collection<String> cacheNamesUpdated = cacheManager.getCacheNames();
        Assert.assertTrue(cacheNamesUpdated.isEmpty());
    }
}