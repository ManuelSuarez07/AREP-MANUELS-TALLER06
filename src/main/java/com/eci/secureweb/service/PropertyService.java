package com.eci.secureweb.service;

import com.eci.secureweb.model.Property;
import com.eci.secureweb.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    public Optional<Property> getPropertyById(Long id) {
        return propertyRepository.findById(id);
    }

    public Property saveProperty(Property property) {
        return propertyRepository.save(property);
    }

    public Property updateProperty(Long id, Property property) {
        return propertyRepository.findById(id).map(existingProperty -> {
            existingProperty.setAddress(property.getAddress());
            existingProperty.setDescription(property.getDescription());
            existingProperty.setPhone(property.getPhone());
            existingProperty.setPrice(property.getPrice());
            existingProperty.setSize(property.getSize());
            return propertyRepository.save(existingProperty);
        }).orElseThrow(() -> new RuntimeException("La propiedad con ID " + id + " no existe."));
    }

    public void deleteProperty(Long id) {
        if (!propertyRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. La propiedad con ID " + id + " no existe.");
        }
        propertyRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return propertyRepository.existsById(id);
    }

    public Optional<Property> findById(Long id) {
        return propertyRepository.findById(id);
    }
}