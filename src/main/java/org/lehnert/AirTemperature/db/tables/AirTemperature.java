package org.lehnert.AirTemperature.db.tables;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

/**
 * Represents an entity for storing air temperature data.
 */
@Entity
@NoArgsConstructor
public class AirTemperature {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @Getter
    private double temperature;

    @Getter
    @Setter
    private Time time;

    @Setter
    @Getter
    private Date date;
}
