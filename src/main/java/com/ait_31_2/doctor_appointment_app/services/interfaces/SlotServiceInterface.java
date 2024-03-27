package com.ait_31_2.doctor_appointment_app.services.interfaces;

import com.ait_31_2.doctor_appointment_app.domain.classes.Slot;
import com.ait_31_2.doctor_appointment_app.domain.dto.AppointmentDto;


import java.sql.Date;
import java.util.List;

public interface SlotServiceInterface {

        List<Slot> getAllSlots();
        Slot getSlotById(int id);
        List<Slot> getAllFreeSlotByDateAndDoctor(Date date, String doctorName, String doctorSurname);



        /**

         * выборка ближайших будущих визитов - лимит 2, от текущего дня
         * выборка ближайших прошлых визитов - лимит 2 от текущего дня - только с меткой успешного визита (у нас заложен такой функционал в базе - последнее поле в таблице)
         */
}
