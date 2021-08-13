package com.github.greennlab.ddul.article.repository;

import com.github.greennlab.ddul.article.Article;
import com.github.greennlab.ddul.entity.AFewRepository;

public interface ArticleRepository extends AFewRepository<Article>, ArticleQuerydslRepository {

}
