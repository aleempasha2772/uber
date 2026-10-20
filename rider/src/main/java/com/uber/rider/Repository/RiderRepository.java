package com.uber.rider.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uber.rider.Model.Rider;

@Repository
public interface RiderRepository extends JpaRepository<Rider, String> {
    Optional<Rider> findByPhone(String phone);
}
