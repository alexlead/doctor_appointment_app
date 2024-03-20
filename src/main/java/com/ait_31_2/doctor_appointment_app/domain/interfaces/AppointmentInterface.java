package com.ait_31_2.doctor_appointment_app.domain.interfaces;



import com.ait_31_2.doctor_appointment_app.domain.classes.Slot;
import com.ait_31_2.doctor_appointment_app.domain.classes.User;

import java.util.Date;

public interface AppointmentInterface {

    int getId();

    User getPatientId();

    void setPatientId(User patientId);

    User getDoctorId();

    void setDoctorId(User doctorId);

    Slot getSlotId();

    void setSlotId(Slot slotId);

    Date getDate();

    void setDate(Date date);

    boolean isVisitComplete();

    void setVisitComplete(boolean visitComplete);

}
