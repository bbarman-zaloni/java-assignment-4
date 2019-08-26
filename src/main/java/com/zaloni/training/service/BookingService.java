package com.zaloni.training.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zaloni.training.dao.BookingDetailRepository;
import com.zaloni.training.dao.BookingMasterRepository;
import com.zaloni.training.dao.RoomRepository;
import com.zaloni.training.entity.BookingDetail;
import com.zaloni.training.entity.BookingMaster;
import com.zaloni.training.entity.BookingStatus;
import com.zaloni.training.entity.Room;
import com.zaloni.training.vo.BookingVo;

@Service
@Transactional
public class BookingService {

    @Autowired
    RoomRepository roomRepo;
    
    @Autowired
    BookingDetailRepository bookingDetailRepo;

    @Autowired
    BookingMasterRepository bookingMasterRepo;

    public void cancelBooking(int bookingNumber) {
        Optional<BookingMaster> bmOptional = bookingMasterRepo.findById(new Long(bookingNumber));
        if (bmOptional.isPresent()) {
            BookingMaster bm = bmOptional.get();
            bm.setBookingStatus(BookingStatus.CANCELLED);
            bookingMasterRepo.save(bm);
        }
        //bookingMasterRepo.updateBooking(new Long(bookingNumber), BookingStatus.CANCELLED);
    }

    public List<Room> getRoomDetailsById(List<Long> roomNumber) {
        return roomRepo.findByIdList(roomNumber);
    }

    public List<Room> getAvailableRoomsByDateRange(String startDate, String endDate) {
        List<Long> activeBookingIdList = bookingMasterRepo.findAllActiveBookingNumber();

        LocalDate startDateL = this.getDate(startDate);
        LocalDate endDateL = this.getDate(endDate);
        List<BookingDetail> unavailableRoomsInDateRange = bookingDetailRepo.findByBookingIdsAndDateRange(activeBookingIdList, startDateL, endDateL);

        List<Long> unavailableIds = new ArrayList<Long>();
        for (BookingDetail bookinDetail: unavailableRoomsInDateRange) {
            unavailableIds.add(bookinDetail.getRoomNumber());
        }

        List<Room> availableRoomList = unavailableIds.size() == 0 ? roomRepo.getAll() : roomRepo.findByExcludingIdList(unavailableIds);
        return availableRoomList;
    }

    public Long bookRoom(String startDate, String endDate, List<Room> roomList) {
        Long bookingNumber = getBookingNumber();
        for (Room room: roomList) {
            BookingDetail bd = new BookingDetail();
            bd.setBookingNumber(bookingNumber);
            bd.setRoomNumber(room.getRoomNumber());
            bd.setStartDate(this.getDate(startDate));
            bd.setEndDate(this.getDate(endDate));
            bookingDetailRepo.save(bd);
        }

        return bookingNumber;
    }
    
    public Long getBookingNumber() {
        BookingMaster bm = new BookingMaster();
        bm.setClientId(1L);
        bm.setBookingStatus(BookingStatus.BOOKED);
        bm.setBookedOn(LocalDateTime.now());
        bookingMasterRepo.save(bm);
        return bm.getBookingNumber();
    }

    public LocalDate getDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        return LocalDate.parse(date, formatter);
    }

    public List<BookingDetail> getBookingDetails() {
        List<Long> activeBookingIdList = bookingMasterRepo.findAllActiveBookingNumber();

        if (activeBookingIdList.size() > 0) {
            return bookingDetailRepo.findByBookingIds(activeBookingIdList);
        }
        return bookingDetailRepo.getAllBookingDetail();
    }

    public Map<Long, List<BookingDetail>> getBookings() {
        Map<Long, List<BookingDetail>> mapToReturn = new HashMap<Long, List<BookingDetail>>();

        System.out.println("getBookings start...");
        List<BookingDetail> list = getBookingDetails();
        for(BookingDetail bd: list) {
            Long bookingNumber = bd.getBookingNumber();
            if (mapToReturn.containsKey(bookingNumber)) {
                List<BookingDetail> listBookingDetail = mapToReturn.get(bookingNumber);
                listBookingDetail.add(bd);
            }
            else {
                List<BookingDetail> listBookingDetail = new ArrayList<BookingDetail>();
                listBookingDetail.add(bd);
                mapToReturn.put(bookingNumber, listBookingDetail);
            }
        }

        return mapToReturn;
    }
}
