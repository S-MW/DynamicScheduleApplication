package com.example.dynamicschedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BroadcastRepo extends JpaRepository<Broadcast,Long> {
}
