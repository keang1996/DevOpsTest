package com.whatcity.topup.repository;

import com.whatcity.topup.model.table.AdminNews;
import com.whatcity.topup.model.table.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
