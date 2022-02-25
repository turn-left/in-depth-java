package com.ethen.feature.spi;

import org.junit.Test;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author ethenyang@126.com
 * @since 2022/02/25
 */
public class MainTest {

    @Test
    public void testSPI() {
        ServiceLoader<Search> serviceLoader = ServiceLoader.load(Search.class);
        Iterator<Search> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            Search searchProvider = iterator.next();
            searchProvider.searchDoc("名士风流");
        }
    }
}
