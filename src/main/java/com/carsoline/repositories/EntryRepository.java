package com.carsoline.repositories;

import com.carsoline.rest.daos.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Dominik on 2017-04-25.
 */
public interface EntryRepository extends JpaRepository<Entry, String> {

    Entry findOneByUserIdAndEngineId(String userId, Long engineId);

    List<Entry> findByEngineId(Long engineId);

    List<Entry> findByUserId(String userId);

    Entry findOneById(String id);
}
