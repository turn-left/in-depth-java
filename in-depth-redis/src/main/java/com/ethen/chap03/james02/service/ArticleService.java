

package com.ethen.chap03.james02.service;

import java.util.List;
import java.util.Map;

public interface ArticleService {
	
	List<Map> queryArticleVoteByPostTime(String articleId);
}