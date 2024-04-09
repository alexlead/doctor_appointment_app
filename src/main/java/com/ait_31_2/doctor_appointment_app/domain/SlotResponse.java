package com.ait_31_2.doctor_appointment_app.domain;

import com.ait_31_2.doctor_appointment_app.domain.classes.Slot;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * Response object containing slot information.
 * Represents a response containing lists of all {@link Slot} and free {@link Slot}.
 */
@Schema(
        description = "Response object containing slot information"
)
@Data
public class SlotResponse {
    /**
     * List of all slots.
     */
    @Schema(description = "List of all slots")
    private List<Slot> all;
    /**
     * List of free slots.
     */
    @Schema(description = "List of free slots")
    private List<Slot> free;
}
