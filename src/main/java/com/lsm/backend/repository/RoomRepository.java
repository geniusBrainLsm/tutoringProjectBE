package com.lsm.backend.repository;

import com.lsm.backend.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Modifying
    @Query("UPDATE Room r SET r.currentCount = :count WHERE r.id = :id")
    int updateUserCount(@Param("id") Long id, @Param("count") int count);

    Optional<Room> findById(Long Id);
}
