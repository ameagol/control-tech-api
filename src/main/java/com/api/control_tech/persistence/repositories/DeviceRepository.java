package com.api.control_tech.persistence.repositories;

import java.util.List;
import java.util.Optional;

import com.api.control_tech.persistence.entities.Device;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface DeviceRepository  extends CrudRepository<Device, Long>{
    List<Device> findByType(String type);
    Optional<Device> findBySerial(String serial);
    List<Device> findByFru(String fru);
    List<Device> findByStatus(String status);
    @Query("SELECT d FROM Device d WHERE " +
            "LOWER(CAST(d.serial AS string)) LIKE LOWER(CONCAT('%', :searchValue, '%')) OR " +
            "LOWER(d.name) LIKE LOWER(CONCAT('%', :searchValue, '%')) OR " +
            "LOWER(d.type) LIKE LOWER(CONCAT('%', :searchValue, '%'))")
    List<Device> searchDevices(@Param("searchValue") String searchValue);
}
