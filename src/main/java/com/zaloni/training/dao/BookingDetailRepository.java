package com.zaloni.training.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zaloni.training.entity.BookingDetail;

@Repository
public interface BookingDetailRepository extends CrudRepository<BookingDetail, Long> {
    @Query(value = "SELECT bd FROM BookingDetail bd")
    public List<BookingDetail> getAllBookingDetail();
    
    @Query(value = "SELECT bd FROM BookingDetail bd WHERE bd.bookingNumber IN :bookingIds")
    public List<BookingDetail> findByBookingIds(@Param("bookingIds") List<Long> bookingIds);

    @Query(value = "SELECT bd FROM BookingDetail bd WHERE bd.bookingNumber IN :bookingIds AND ("
            + "(bd.startDate <= :startDate AND bd.endDate > :startDate)"
            + " OR "
            + "(bd.startDate < :endDate AND bd.endDate >= :endDate)"
            + " OR "
            + "(bd.startDate >= :startDate AND bd.endDate <= :endDate)"
            + ")")
    public List<BookingDetail> findByBookingIdsAndDateRange(
            @Param("bookingIds") List<Long> bookingIds,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
