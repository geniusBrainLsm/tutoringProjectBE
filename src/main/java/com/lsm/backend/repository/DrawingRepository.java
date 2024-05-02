package com.lsm.backend.repository;

import com.lsm.backend.model.Drawing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrawingRepository extends JpaRepository<Drawing, Long> {
}
