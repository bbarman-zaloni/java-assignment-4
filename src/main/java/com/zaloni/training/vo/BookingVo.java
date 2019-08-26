package com.zaloni.training.vo;

public class BookingVo {
    private Long bookingNumber;
    private Long roomNumber;
    private Long cliendId;

    BookingVo() {
    }

    BookingVo(long bookingNumber, long roomNumber, long cliendId) {
        this.bookingNumber = bookingNumber;
        this.roomNumber = roomNumber;
        this.cliendId = cliendId;
    }

    public Long getBookingNumber() {
        return bookingNumber;
    }
    public void setBookingNumber(Long bookingNumber) {
        this.bookingNumber = bookingNumber;
    }
    public Long getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(Long roomNumber) {
        this.roomNumber = roomNumber;
    }
    public Long getCliendId() {
        return cliendId;
    }
    public void setCliendId(Long cliendId) {
        this.cliendId = cliendId;
    }
}
