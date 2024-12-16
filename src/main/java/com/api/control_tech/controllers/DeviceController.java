package com.api.control_tech.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.api.control_tech.exceptions.DeviceIdMismatchException;
import com.api.control_tech.exceptions.DeviceNotFoundException;
import com.api.control_tech.models.DeviceDto;
import com.api.control_tech.persistence.entities.Company;
import com.api.control_tech.persistence.entities.Device;
import com.api.control_tech.persistence.repositories.CompanyRepository;
import com.api.control_tech.persistence.repositories.DeviceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://control-tech-ui.vercel.app"})
@RequestMapping("/api/devices")
public class DeviceController {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping
    public ResponseEntity<List<DeviceDto>> findAllDevices() {
        return Optional.of(deviceRepository.findAll())
                .map(devices -> StreamSupport.stream(devices.spliterator(), false)
                        .map(device -> new DeviceDto(device))
                        .collect(Collectors.toList()))
                .filter(deviceDtos -> !deviceDtos.isEmpty())
                .map(ResponseEntity::ok)
                .orElseThrow(DeviceNotFoundException::new);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Device>> findByTitle(@PathVariable String type) {
        return Optional.ofNullable(deviceRepository.findByType(type))
                .filter(devices -> !devices.isEmpty())
                .map(devices -> ResponseEntity.ok(devices))
                .orElseThrow(() -> new DeviceNotFoundException("No devices found for type: " + type));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceDto> findById(@PathVariable Long id) {
        return deviceRepository.findById(id)
                .map(device -> ResponseEntity.ok(new DeviceDto(device)))
                .orElseThrow(DeviceNotFoundException::new);
    }

    @GetMapping("/serial/{serial}")
    public ResponseEntity<DeviceDto> findById(@PathVariable String serial) {
        return deviceRepository.findBySerial(serial)
                .map(device -> ResponseEntity.ok(new DeviceDto(device)))
                .orElseThrow(DeviceNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DeviceDto> create(@RequestBody Device device) {
        return Optional.of(device)
                .map(d -> {
                    Company company = companyRepository.findById(d.getCompany().getId())
                            .orElseThrow(DeviceNotFoundException::new);
                    d.setCompany(company);
                    return deviceRepository.save(d);
                })
                .map(_device -> ResponseEntity.ok(new DeviceDto(_device)))
                .orElseThrow(() -> new RuntimeException("Failed to Save Device"));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        deviceRepository.findById(id)
                .ifPresentOrElse(device -> deviceRepository.deleteById(id),
                        () -> { throw new DeviceNotFoundException("Devices Not Found"); });
    }

    @PostMapping("/search")
    public ResponseEntity<List<DeviceDto>> searchDevices(@RequestBody Map<String, String> query) {
        return Optional.ofNullable(query.get("search"))
                .filter(search -> !search.trim().isEmpty())
                .map(search -> deviceRepository.searchDevices(search))
                .map(devices -> devices.stream()
                        .map(DeviceDto::new) // Convert to DTO
                        .collect(Collectors.toList()))
                .filter(deviceDtos -> !deviceDtos.isEmpty())
                .map(ResponseEntity::ok)
                .orElseThrow(DeviceNotFoundException::new);
    }
}
