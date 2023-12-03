package com.github.kiiril.telegrambot.repository;

import com.github.kiiril.telegrambot.repository.entity.GroupSub;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * {@link GroupSubRepository} for {@link GroupSub} entity.
 */
public interface GroupSubRepository extends JpaRepository<GroupSub, Integer> {
}
