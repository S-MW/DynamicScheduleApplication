package com.example.dynamicschedule;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BroadcastSender {
    private int clientId;
    private Boolean isSent;
    private int sentAfter;
}
