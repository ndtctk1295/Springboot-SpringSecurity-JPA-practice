package com.example.demo.scheduled;

import com.example.demo.repositories.BlacklistTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    private BlacklistTokenRepository blacklistTokenRepository;

    @Scheduled(fixedRate = 3600000) // runs every hour
    public void deleteOldRecords() {
        // logic to delete old records
        blacklistTokenRepository.deleteAll();
    }
}
