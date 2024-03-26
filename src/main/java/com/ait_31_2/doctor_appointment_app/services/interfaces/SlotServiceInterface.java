package com.ait_31_2.doctor_appointment_app.services.interfaces;

import com.ait_31_2.doctor_appointment_app.domain.classes.Slot;


import java.sql.Date;
import java.util.List;

public interface SlotServiceInterface {

        List<Slot> getAllSlots();
        Slot getSlotById(int id);
        List<Slot> getAllFreeSlotByDateAndDoctor(Date date, String doctorName, String doctorSurname);


        /**
         * поиск свободных слотов по дате и доктору (доктор по ID вероятно будет)
         * выдача всех слотов пациента - с пагинацией за период времени (тут нужно выдавать блоками, лимит со сдвигом по записям : Limit и Offset)
         * выборка ближайших будущих визитов - лимит 2, от текущего дня
         * выборка ближайших прошлых визитов - лимит 2 от текущего дня - только с меткой успешного визита (у нас заложен такой функционал в базе - последнее поле в таблице)
         */
}
