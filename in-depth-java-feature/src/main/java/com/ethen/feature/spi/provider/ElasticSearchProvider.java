package com.ethen.feature.spi.provider;

import com.ethen.feature.spi.Search;

/**
 * ElasticSearchProvider
 *
 * @author ethenyang@126.com
 * @since 2022/02/25
 */
public class ElasticSearchProvider implements Search {
    @Override
    public Object searchDoc(String docId) {

        System.err.println("使用 ElasticSearchProvider searchDoc docId=" + docId);

        return "ElasticSearchResult";
    }
}
