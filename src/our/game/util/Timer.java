package our.game.util;

import java.util.ArrayList;
import java.util.Iterator;

public class Timer {

    // #########################
    public void run() {

    }

    public void runInterval() {

    }

    public void onDestroy() {

    }

    public void onCreate() {

    }
    // #########################

    private static ArrayList<Timer> timers = new ArrayList<>();

    public final String UID;
    private final long initTime;
    private final long intervall;
    private long c_inter = 1;
    private long duration = 0;

    public static void init() {
        if(timers != null) return;

        timers = new ArrayList<>();
        timersToDestroy = new ArrayList<>();
    }

    public Timer(String UID, long time, long intervall) {

        this.UID = UID;
        this.duration = time;
        this.intervall = intervall;
        this.initTime = System.currentTimeMillis();

        if(UID == null) return;
        
        timersToAdd.add(this);
        // Timer.addTimer(this);
    }

    public static void advance(long dt) {

        destroyQueuedTimers();
        addQueuedTimers();

        Iterator<Timer> it = timers.iterator();

        while(it.hasNext()) {
            Timer t = it.next();
            long i_time = t.initTime;
            long c_time = System.currentTimeMillis();

            if(! (t.duration < 1)) {
                if(c_time >= i_time + t.duration) {
                    // Timer has Ended
                    t.run();
                    it.remove();
                    continue;
                }
            } else if(!(t.intervall > 0)) it.remove(); // Remove Timer if intervall is negative and duration is < 0

            if(t.intervall > 0 && t.isInterval(c_time)) {
                // Timer intervall reached
                t.runInterval();
                t.addInter();
            }
        }
        
    }

    private static ArrayList<Timer> timersToDestroy = new ArrayList<>();

    private static void destroyQueuedTimers() {
        Iterator<Timer> it = timersToDestroy.iterator();
        while(it.hasNext()) {
            Timer t = it.next();
            t.onDestroy();
            if(timers.contains(t)) timers.remove(t);
            it.remove();
        }
    }

    private static ArrayList<Timer> timersToAdd = new ArrayList<>();

    private static void addQueuedTimers() {
        Iterator<Timer> it = timersToAdd.iterator();
        while(it.hasNext()) {
            Timer t = it.next();
            if(timers.contains(t)) return;
            t.onCreate();
            it.remove();
            addTimer(t);
        }
    }

    public long getDuration() {
        return this.duration;
    }

    public long getInter() {
        return this.c_inter;
    }

    private void addInter() {
        c_inter++;
    }

    private boolean isInterval(long c_time) {
        if(c_time > initTime + c_inter * intervall) {
            return true;
        }
        return false;
    }

    public static boolean destroy(String uid) {

        Iterator<Timer> it = timers.iterator();
        while(it.hasNext()) {
            Timer t = it.next();
            if(t.UID.equals(uid)) {
                timersToDestroy.add(t);
                return true;
            }
        }

        return true;
    }

    private static boolean addTimer(Timer _t) {
        if(_t == null) return false;
        if(timers == null) {
            System.out.println("timers == null! Error");
            init();
            // return false;
        }
        if(timers.contains(_t)) return false;

        for(Timer tmp : timers) {
            if(tmp.UID.equals(_t.UID)) return false;
        }

        timers.add(_t);
        _t.onCreate();

        return true;
    }

}