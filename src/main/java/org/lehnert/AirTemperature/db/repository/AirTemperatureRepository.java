package org.lehnert.AirTemperature.db.repository;

import org.lehnert.AirTemperature.db.ITemperature;
import org.lehnert.AirTemperature.db.tables.AirTemperature;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;



public interface AirTemperatureRepository extends CrudRepository<AirTemperature, Long> {
    @Query(value = """
    select date,time,AVG(TEMPERATURE) as temp
        from AIR_TEMPERATURE where DATE between :first and :last 
        group by date,time Order by DATE 
    """,nativeQuery = true)
    Iterable<ITemperature> getAirTemperatureByBetweenFirstAndLast(@Param("first") java.sql.Date first,
                                                                  @Param("last") java.sql.Date last);
}
