package com.zaloni.training.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.zaloni.training.entity.BookingMaster;

@Repository
public interface BookingMasterRepository extends CrudRepository<BookingMaster, Long> {
    @Query("SELECT bm.bookingNumber FROM BookingMaster bm WHERE bm.bookingStatus = 'BOOKED'")
    List<Long> findAllActiveBookingNumber();
}
