package com.ethen.feature.spi.provider;

import com.ethen.feature.spi.Search;

/**
 * MongoSearchProvider
 *
 * @author ethenyang@126.com
 * @since 2022/02/25
 */
public class MongoSearchProvider implements Search {
    @Override
    public Object searchDoc(String docId) {

        System.err.println("使用 MongoSearchProvider searchDoc docId=" + docId);

        return "MongoSearchResult";
    }
}
