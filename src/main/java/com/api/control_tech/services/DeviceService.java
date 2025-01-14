package com.api.control_tech.services;

import com.api.control_tech.persistence.entities.Device;
import com.api.control_tech.persistence.repositories.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;
    
    public List<Device> findByUserEmail(String email) {
         return deviceRepository.findByUserEmail(email);
    }

    public List<Device> findByType(String type) {
        return deviceRepository.findByType(type);
    }
    
    public Optional<Device> findById(Long id) {
        return deviceRepository.findById(id);
    }

    public Optional<Device> findBySerial(String serial) {
        return deviceRepository.findBySerial(serial);
    }

    public Device saveDevice(Device device) {
        return deviceRepository.save(device);
    }

    public void deleteById(Long id) {
        // deviceRepository.deleteById(id);
    }

    public List<Device> searchDevices(String search) {
       return deviceRepository.searchDevices(search);
    }
}
