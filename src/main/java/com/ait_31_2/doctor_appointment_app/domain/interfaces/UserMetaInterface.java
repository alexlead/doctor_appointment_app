package com.ait_31_2.doctor_appointment_app.domain.interfaces;

import com.ait_31_2.doctor_appointment_app.domain.classes.User;

public interface UserMetaInterface {

    int getId();

    void setId(int id);

    User getUserId();

    void setUserId(User userId);

    String getMetaKey();

    void setMetaKey(String metaKey);

    String getMetaValue();


}
