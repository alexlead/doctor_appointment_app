package com.ait_31_2.doctor_appointment_app.services;

import com.ait_31_2.doctor_appointment_app.domain.SlotResponse;
import com.ait_31_2.doctor_appointment_app.domain.classes.Slot;
import com.ait_31_2.doctor_appointment_app.exception_handling.exceptions.NotFreeSlotsException;
import com.ait_31_2.doctor_appointment_app.repositories.SlotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class SlotServiceTest {
    @Mock
    private SlotRepository repository;
    @Mock
    private UserService userService;
    @InjectMocks
    private SlotService slotService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetSlotsList() {

        List<Slot> allSlots = new ArrayList<>();
        allSlots.add(new Slot(1, Time.valueOf("09:00:00"), Time.valueOf("09:30:00")));
        allSlots.add(new Slot(2, Time.valueOf("09:30:00"), Time.valueOf("10:00:00")));
        allSlots.add(new Slot(3, Time.valueOf("10:00:00"), Time.valueOf("10:30:00")));
        allSlots.add(new Slot(4, Time.valueOf("10:30:00"), Time.valueOf("11:00:00")));

        List<Slot> freeSlots = new ArrayList<>();
        freeSlots.add(new Slot(1, Time.valueOf("09:00:00"), Time.valueOf("09:30:00")));
        freeSlots.add(new Slot(4, Time.valueOf("10:30:00"), Time.valueOf("11:00:00")));

        when(repository.findAll()).thenReturn(allSlots);
        when(repository.findFreeSlotsByDateAndDoctorId(LocalDate.now(), 1)).thenReturn(freeSlots);

        SlotResponse slotResponse = slotService.getSlotsList(LocalDate.now(), 1);

        assertEquals(allSlots, slotResponse.getAll());
        assertEquals(freeSlots, slotResponse.getFree());
    }

    @Test
    void testGetSlotsList_NotFreeSlotsException() {

        List<Slot> allSlots = new ArrayList<>();
        allSlots.add(new Slot(1, Time.valueOf("09:00:00"), Time.valueOf("10:00:00")));
        allSlots.add(new Slot(2, Time.valueOf("10:00:00"), Time.valueOf("11:00:00")));
        allSlots.add(new Slot(3, Time.valueOf("11:00:00"), Time.valueOf("12:00:00")));

        when(repository.findAll()).thenReturn(allSlots);
        when(repository.findFreeSlotsByDateAndDoctorId(LocalDate.now(), 1)).thenReturn(null); // No free slots

        assertThrows(NotFreeSlotsException.class, () -> {
            slotService.getSlotsList(LocalDate.now(), 1);
        });
    }
}