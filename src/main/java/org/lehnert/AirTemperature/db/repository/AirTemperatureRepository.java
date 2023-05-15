package org.lehnert.AirTemperature.db.repository;

import org.lehnert.AirTemperature.db.tables.AirTemperature;
import org.springframework.data.repository.CrudRepository;

public interface AirTemperatureRepository extends CrudRepository<AirTemperature, Long> {

}
