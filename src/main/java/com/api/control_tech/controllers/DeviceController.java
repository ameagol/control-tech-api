package com.api.control_tech.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.api.control_tech.exceptions.DeviceIdMismatchException;
import com.api.control_tech.exceptions.DeviceNotFoundException;
import com.api.control_tech.models.DeviceDto;
import com.api.control_tech.persistence.entities.Company;
import com.api.control_tech.persistence.entities.Device;
import com.api.control_tech.persistence.repositories.CompanyRepository;
import com.api.control_tech.services.AuthService;
import com.api.control_tech.services.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://control-tech-ui.vercel.app"})
@RequestMapping("/api/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AuthService authService;

    @GetMapping
    public ResponseEntity<List<DeviceDto>> findAllDevices(@RequestHeader("Authorization") String authorization) {
        String token = authorization.replace("Bearer ", "");

        String userEmail = Optional.ofNullable(authService.getUserEmailFromToken(token))
                .orElseThrow(DeviceNotFoundException::new);

        return Optional.of(deviceService.findByUserEmail(userEmail))
                .filter(devices -> !devices.isEmpty())
                .map(devices -> devices.stream()
                        .map(device -> new DeviceDto(device))
                        .collect(Collectors.toList()))
                .map(ResponseEntity::ok)
                .orElseThrow(DeviceNotFoundException::new);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Device>> findByTitle(@PathVariable String type) {
        return Optional.ofNullable(deviceService.findByType(type))
                .filter(devices -> !devices.isEmpty())
                .map(devices -> ResponseEntity.ok(devices))
                .orElseThrow(DeviceNotFoundException::new);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceDto> findById(@PathVariable Long id) {
        return deviceService.findById(id)
                .map(device -> ResponseEntity.ok(new DeviceDto(device)))
                .orElseThrow(DeviceNotFoundException::new);
    }

    @GetMapping("/serial/{serial}")
    public ResponseEntity<DeviceDto> findById(@PathVariable String serial) {
        return deviceService.findBySerial(serial)
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
                    return deviceService.saveDevice(d);
                })
                .map(_device -> ResponseEntity.ok(new DeviceDto(_device)))
                .orElseThrow(DeviceIdMismatchException::new);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        deviceService.findById(id)
                .ifPresentOrElse(device -> deviceService.deleteById(id),
                        () -> { throw new DeviceNotFoundException(); });
    }

    @PostMapping("/search")
    public ResponseEntity<List<DeviceDto>> searchDevices(@RequestBody Map<String, String> query) {
        return Optional.ofNullable(query.get("search"))
                .filter(search -> !search.trim().isEmpty())
                .map(search -> deviceService.searchDevices(search))
                .map(devices -> devices.stream()
                        .map(DeviceDto::new)
                        .collect(Collectors.toList()))
                .filter(deviceDtos -> !deviceDtos.isEmpty())
                .map(ResponseEntity::ok)
                .orElseThrow(DeviceNotFoundException::new);
    }
}
