package com.jack.applications.webservice.threads;

import com.jack.applications.webservice.services.RoomService;

public class RoomGarbageCollector extends Thread {

    private static final long TIME_BETWEEN_CLEANUPS = 20000;

    private final RoomService roomService;

    public RoomGarbageCollector(RoomService roomService) {
        this.roomService = roomService;
    }

    @Override
    public void run() {
        System.out.println(this.getName() + ": Cleanup thread running...");
        while (true) {
            try {
                roomService.removeUnusedRooms();
                Thread.sleep(TIME_BETWEEN_CLEANUPS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
