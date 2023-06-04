package org.lehnert.AirTemperature.db;

import java.sql.Date;
import java.sql.Time;

public interface ITemperature {
    Date getDate();
    Time getTime();
    Double getTemp();
}
