package com.web.seo.repository;

import com.web.seo.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {
    Content findTopByOrderByIdDesc(); // mengambil satu data untuk ditampilan awal
}
