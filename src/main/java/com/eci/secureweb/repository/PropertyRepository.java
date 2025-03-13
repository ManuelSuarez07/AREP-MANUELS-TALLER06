package com.eci.secureweb.repository;

import com.eci.secureweb.model.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    // Método para buscar propiedades con filtros
    @Query("SELECT p FROM Property p WHERE " +
            "(:location IS NULL OR p.address LIKE %:location%) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
            "(:minSize IS NULL OR p.size >= :minSize) AND " +
            "(:maxSize IS NULL OR p.size <= :maxSize)")
    List<Property> findByFilters(
            @Param("location") String location,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("minSize") Double minSize,
            @Param("maxSize") Double maxSize);
}