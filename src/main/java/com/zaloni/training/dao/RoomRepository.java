package com.zaloni.training.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zaloni.training.entity.Room;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {
    @Query(value = "SELECT r FROM Room r WHERE r.roomNumber IN :ids")
    public List<Room> findByIdList(@Param("ids") List<Long> ids);
    
    @Query(value = "SELECT r FROM Room r")
    public List<Room> getAll();

    @Query(value = "SELECT r FROM Room r WHERE r.roomNumber NOT IN :ids")
    public List<Room> findByExcludingIdList(@Param("ids") List<Long> ids);
}
