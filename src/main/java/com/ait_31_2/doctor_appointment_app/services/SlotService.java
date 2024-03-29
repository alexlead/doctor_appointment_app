package com.ait_31_2.doctor_appointment_app.services;


import com.ait_31_2.doctor_appointment_app.domain.classes.Slot;
import com.ait_31_2.doctor_appointment_app.domain.dto.AppointmentDto;
import com.ait_31_2.doctor_appointment_app.exception_handling.exceptions.NotFreeSlotsException;
import com.ait_31_2.doctor_appointment_app.repositories.SlotRepository;
import com.ait_31_2.doctor_appointment_app.services.interfaces.SlotServiceInterface;
import org.springframework.stereotype.Service;


import java.sql.Date;
import java.util.List;

@Service
public class SlotService implements SlotServiceInterface {


    private SlotRepository repository;
    private UserService userService;

    public SlotService(SlotRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }


    @Override
    public List<Slot> getAllSlots() {
        return repository.findAll();
    }

    @Override
    public Slot getSlotById(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<Slot> getAllFreeSlotByDateAndDoctor(Date date, String doctorName, String doctorSurname) {
        int doctorId = userService.getDoctorByName(doctorName, doctorSurname).getId();
        List<Slot> slots =  repository.findFreeSlotsByDateAndDoctor(date, doctorId);
        if (slots==null){
            throw new NotFreeSlotsException("There are no slots on this date!");
        }

        return slots;
    }




}
