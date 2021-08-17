package com.jack.applications.webservice.handlers;

public class RoomGarbageCollector extends Thread {

    private static final long TIME_BETWEEN_CLEANUPS = 20000;

    private final RoomHandler roomHandler;

    public RoomGarbageCollector(RoomHandler roomHandler) {
        this.roomHandler = roomHandler;
    }

    @Override
    public void run() {
        System.out.println(this.getName() + ": Cleanup thread running...");
        while (true) {
            try {
                roomHandler.cleanRooms();
                Thread.sleep(TIME_BETWEEN_CLEANUPS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
