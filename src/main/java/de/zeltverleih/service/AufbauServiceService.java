package de.zeltverleih.service;



import de.zeltverleih.model.datenbank.AufbauService;
import de.zeltverleih.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AufbauServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    public AufbauService saveService(AufbauService aufbauService) {
        return serviceRepository.save(aufbauService);
    }

    public List<AufbauService> getAllService() {
        return serviceRepository.findAll();
    }

    public void deleteService(AufbauService aufbauService) {
        serviceRepository.delete(aufbauService);
    }

    public Optional<AufbauService> getById(int id) {
        return serviceRepository.findById(id);
    }
}
