package com.uber.captain.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uber.captain.Model.Captain;

@Repository
public interface CaptainRepository extends JpaRepository<Captain, String> {

}
