package com.example.dynamicschedule;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@RestController
@RequestMapping(path = "/schedule")
public class TestController {

    private TaskScheduler scheduler;


//    Runnable exampleRunnable = new Runnable(){
//        @Override
//        public void run() {
//            System.out.println("Works");
//        }
//    };

    public Runnable sendBroadcast(BroadcastSender broadcastSender){
        Runnable exampleRunnable = new Runnable(){
            @Override
            public void run() {
                System.out.println("Works");
                System.out.println(broadcastSender.getClientId());
            }
        };
        return  exampleRunnable;
    }

    @Async
    public void executeTaskT(BroadcastSender broadcastSender) {
        System.out.println("executeTaskT");
        ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduler = new ConcurrentTaskScheduler(localExecutor);

        scheduler.schedule(sendBroadcast(broadcastSender), Instant.now().plusSeconds(broadcastSender.getSentAfter()));
    }

        @PostMapping("/taskdef")
        public void scheduleATask(@RequestBody BroadcastSender broadcastSender){
            executeTaskT(broadcastSender);


        }

    }
