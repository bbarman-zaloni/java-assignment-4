package com.zaloni.training.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.zaloni.training.entity.BookingDetail;
import com.zaloni.training.entity.Room;
import com.zaloni.training.entity.User;
import com.zaloni.training.model.Booking;
import com.zaloni.training.service.BookingService;
import com.zaloni.training.service.UserService;

@Controller
public class RoomController {
    @Autowired
    UserService userService;
    
    @Autowired
    BookingService bookingService;

    @RequestMapping(value = "/booking/cancel/{bookingNumber}", method = RequestMethod.GET)
    public ModelAndView cancelBookingForm(HttpServletRequest request, @PathVariable("bookingNumber") int bookingNumber) {
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            return new ModelAndView("redirect:/relogin");
        }
        
        ModelAndView mav = new ModelAndView("room/cancel-booking");
        mav.addObject("bookingNumber", bookingNumber);
        mav.addObject("bookingTarget", "/booking/cancel/"+bookingNumber);
        return mav;
    }

    @RequestMapping(value = "/booking/cancel/{bookingNumber}", method = RequestMethod.POST)
    public ModelAndView cancelBookingFormHandler(HttpServletRequest request, @PathVariable("bookingNumber") int bookingNumber) {
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            return new ModelAndView("redirect:/relogin");
        }

        bookingService.cancelBooking(bookingNumber);
        return new ModelAndView("redirect:/my-bookings");
    }
    
    @RequestMapping(value = "/my-bookings", method = RequestMethod.GET)
    public ModelAndView myBooking(HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            return new ModelAndView("redirect:/relogin");
        }

        ModelAndView mav = new ModelAndView("room/my-bookings");
        Map<Long, List<BookingDetail>> bookingDetailMap = bookingService.getBookings();
        mav.addObject("bookingDetailMap", bookingDetailMap);
        mav.addObject("mapSize", bookingDetailMap.size());
        return mav;
    }

    @RequestMapping(value = "/room-booking", method = RequestMethod.GET)
    public ModelAndView roomBookingForm(HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            return new ModelAndView("redirect:/relogin");
        }

        String bookingTarget = "/room-booking";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
        String startDate = LocalDate.now().plusDays(1).format(formatter);
        String endDate = LocalDate.now().plusDays(2).format(formatter);

        Booking booking = new Booking();
        booking.setStartDate(startDate);
        booking.setEndDate(endDate);
        booking.setStatus("SELECT_DATE_RANGE");

        ModelAndView mav = new ModelAndView("room/booking");
        mav.addObject("booking", booking);
        mav.addObject("bookingTarget", bookingTarget);

        return mav;
    }

    @RequestMapping(value = "/room-booking", method = RequestMethod.POST)
    public ModelAndView roomBookingFormSubmit(@ModelAttribute("booking")Booking booking, ModelMap model, HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            return new ModelAndView("redirect:/relogin");
        }

        String status = booking.getStatus();
        if (status == null) {
            status = "";
        }

        if (status.equals("SELECT_DATE_RANGE")) {
            booking.setStatus("SELECT_ROOM");
            return handleDateSelection(booking);
        }
        else if (status.equals("SELECT_ROOM")) {
            booking.setStatus("CONFIRM_BOOKING");
            return handleRoomSelection(booking);
        }
        else if (status.equals("CONFIRM_BOOKING")) {
            booking.setStatus("BOOK_ROOM");
            return bookRoom(booking);
        }
        else {
            //throw new IllegalStateException();
            System.out.println("This is invalid state");
            return new ModelAndView("redirect:/room-booking");
        }
    }

    private ModelAndView handleDateSelection(Booking booking) {
        setAvailableRooms(booking);

        ModelAndView mav = new ModelAndView("room/booking");
        mav.addObject("booking", booking);
        mav.addObject("bookingTarget", "/room-booking");
        return mav;
    }

    private void setAvailableRooms(Booking booking) {
        String startDate = booking.getStartDate();
        String endDate = booking.getEndDate();
        List<Room> availableRoomList = bookingService.getAvailableRoomsByDateRange(startDate, endDate);
        booking.setRooms(availableRoomList);
    }

    private ModelAndView handleRoomSelection(Booking booking) {
        ModelAndView mav = new ModelAndView("room/booking");
        mav.addObject("booking", booking);
        mav.addObject("bookingTarget", "/room-booking");
        return mav;
    }

    private ModelAndView bookRoom(Booking booking) {
        System.out.println("Booking rooms...");
        bookingService.bookRoom(booking.getStartDate(), booking.getEndDate(), booking.getRooms());
        System.out.println("Rooms Booked!");

        return new ModelAndView("redirect:/room-booking");
    }
}
