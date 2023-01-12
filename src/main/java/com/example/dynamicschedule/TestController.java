package com.example.dynamicschedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@RestController
@RequestMapping(path = "/schedule")
public class TestController {


    /**
     *
     * 1,1000,2023-01-12 11:45:15.000000,false,schedule
     * 2,1001,2023-01-12 12:02:30.000000,true,SENT
     *
     */

    @Autowired
    private BroadcastRepo broadcastRepo;

    private TaskScheduler scheduler;


//    Runnable exampleRunnable = new Runnable(){
//        @Override
//        public void run() {
//            System.out.println("Works");
//        }
//    };

    public Runnable sendBroadcast(Broadcast broadcast){
        Runnable exampleRunnable = new Runnable(){
            @Override
            public void run() {
                System.out.println("--- SENT ---");
                Broadcast sentBroadcast = broadcastRepo.findById(broadcast.getId()).orElseThrow(()-> new RuntimeException());
                sentBroadcast.setStatus("SENT");
                sentBroadcast.setIsSent(Boolean.TRUE);
                broadcastRepo.save(sentBroadcast);
                System.out.println(sentBroadcast.getBroadcastId() + " : is Sent");
            }
        };
        return  exampleRunnable;
    }

    @Async
    public void executeTaskT(Broadcast broadcast) {
        System.out.println("--- executeTask ---");
        ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduler = new ConcurrentTaskScheduler(localExecutor);

        scheduler.schedule(sendBroadcast(broadcast), Instant.now().plusSeconds(broadcast.getSentAt().getSecond()));
    }

        @PostMapping("/{id}")
        public void scheduleATask(@PathVariable Long id){

            Broadcast broadcast = broadcastRepo.findById(id).orElseThrow(()-> new RuntimeException());

            executeTaskT(broadcast);
        }

    }
