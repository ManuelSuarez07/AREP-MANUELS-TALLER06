package com.eci.secureweb.service;

import com.eci.secureweb.model.Property;
import com.eci.secureweb.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    private final PropertyRepository propertyRepository;

    @Autowired
    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    // Método para obtener todas las propiedades con paginación
    public Page<Property> getAllProperties(Pageable pageable) {
        return propertyRepository.findAll(pageable);
    }

    // Método para buscar propiedades con filtros
    public List<Property> searchProperties(String location, Double minPrice, Double maxPrice, Double minSize, Double maxSize) {
        return propertyRepository.findByFilters(location, minPrice, maxPrice, minSize, maxSize);
    }

    // Métodos existentes (getPropertyById, saveProperty, updateProperty, deleteProperty)
    public Optional<Property> getPropertyById(Long id) {
        return propertyRepository.findById(id);
    }

    public Property saveProperty(Property property) {
        return propertyRepository.save(property);
    }

    public Property updateProperty(Long id, Property property) {
        return propertyRepository.findById(id)
                .map(existingProperty -> {
                    existingProperty.setAddress(property.getAddress());
                    existingProperty.setPrice(property.getPrice());
                    existingProperty.setSize(property.getSize());
                    existingProperty.setDescription(property.getDescription());
                    return propertyRepository.save(existingProperty);
                })
                .orElseThrow(() -> new RuntimeException("Property not found with id " + id));
    }

    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }
}