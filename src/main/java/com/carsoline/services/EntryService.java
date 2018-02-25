package com.carsoline.services;

import com.carsoline.repositories.EntryRepository;
import com.carsoline.rest.daos.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * Created by Dominik on 2017-04-25.
 */
@Component
public class EntryService {

    private final EntryRepository entryRepository;

    @Autowired
    public EntryService(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    public List<Entry> getEntries() { return entryRepository.findAll(); }

    public Entry save(Entry entry) {
        return entryRepository.save(entry);
    }

    public Entry findById(String id) {
        return entryRepository.findOneById(id);
    }

    public List<Entry> findAll() {
        return entryRepository.findAll();
    }

    public Entry findByUserIdAndEngineId(String userId, Long engineId) {
        return entryRepository.findOneByUserIdAndEngineId(userId, engineId);
    }

    public List<Entry> findByUserId(String userId) {
        return entryRepository.findByUserId(userId);
    }

    public void delete(String id) {
        entryRepository.delete(id);
    }

    public List<Entry> findByEngineId(Long engineId) {
        return entryRepository.findByEngineId(engineId);
    }
}
