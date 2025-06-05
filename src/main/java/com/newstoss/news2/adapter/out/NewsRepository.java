package com.newstoss.news2.adapter.out;

import com.newstoss.news2.domain.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository   <NewsEntity, String> {
}