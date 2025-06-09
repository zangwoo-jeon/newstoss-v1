package com.newstoss.news2.adapter.out;

import com.newstoss.news.domain.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository   <NewsEntity, String> {
}