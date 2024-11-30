package com.api.control_tech.controllers;

import java.util.List;
import java.util.Map;

import com.api.control_tech.exceptions.DeviceIdMismatchException;
import com.api.control_tech.exceptions.DeviceNotFoundException;
import com.api.control_tech.persistence.entities.Device;
import com.api.control_tech.persistence.repositories.DeviceRepository;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
//@CrossOrigin(origins = {"http://localhost:4200", "https://control-tech-ui.vercel.app"})
@RequestMapping("/api/devices")
public class DeviceController {

    @Autowired
    private DeviceRepository deviceRepository;

    @GetMapping
    public Iterable<Device> findAll() {
        return deviceRepository.findAll();
    }

    @GetMapping("/title/{type}")
    public List<Device> findByTitle(@PathVariable String type) {
        return deviceRepository.findByType(type);
    }

    @GetMapping("/{id}")
    public Device findById(@PathVariable Long id) {
        return deviceRepository.findById(id).orElseThrow(DeviceNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Device create(@RequestBody Device device) {
        return deviceRepository.save(device);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        deviceRepository.findById(id).orElseThrow(DeviceNotFoundException::new);
        deviceRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Device update(@RequestBody Device book, @PathVariable Long id) {
        if(book.getId() != id){
            throw new DeviceIdMismatchException();
        }
        deviceRepository.findById(id).orElseThrow(DeviceNotFoundException::new);
        return deviceRepository.save(book);
    }

    @PostMapping("/search")
    public List<Device> searchDevices(@RequestBody Map<String, String> query) {
        String searchValue = query.get("search");
        return deviceRepository.searchDevices(searchValue);
    }
}
