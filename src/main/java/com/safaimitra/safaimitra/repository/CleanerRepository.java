package com.safaimitra.safaimitra.repository;  // ✅ Yeh sahi hai

import com.safaimitra.safaimitra.model.Cleaner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CleanerRepository extends JpaRepository<Cleaner, Long> {

    // Available cleaners dhundho
    List<Cleaner> findByAvailableTrue();

    // Gender ke hisaab se cleaners
    List<Cleaner> findByGenderAndAvailableTrue(String gender);
}