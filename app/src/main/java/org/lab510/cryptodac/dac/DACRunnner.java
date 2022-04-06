package org.lab510.cryptodac.dac;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import org.lab510.cryptodac.time.DayTimer;
import org.lab510.cryptodac.utils.Printer;
import org.lab510.cryptodac.dac.event.DACEventFactory.EventType;

public class DACRunnner {
    DAC dac;
    int dayLimited = 30;
    DayTimer dayTimer = new DayTimer();
    Map<EventType, Double> rates = new HashMap<>();
    Map<EventType, Function<Void, Object>> functions = new HashMap<>();
    List<EventType> events = new ArrayList<>();

    public DACRunnner(DAC dac) {
        this.dac = dac;
        functions.put(EventType.ASSIGN_UR, (Void) -> {dac.assignUr(); return null;});
        functions.put(EventType.ASSIGN_PA, (Void) -> {dac.assignPa(); return null;});
        functions.put(EventType.REVOKE_UR, (Void) -> {dac.revokeUr(); return null;});
        functions.put(EventType.REVOKE_PA, (Void) -> {dac.revokePa(); return null;});
    }

    public void run() {
        calculateRates();
        while (dayTimer.getToday() < dayLimited) {
            runADay();
            dayTimer.increment();
        }
    }

    void runADay() {
        printDay();
        generateEvents();
        execute();
    }

    void printDay() {
        Printer.print("Day " + dayTimer.getToday());
    }

    void generateEvents() {
        events.clear();
        for(var type : EventType.values()) {
            generateEventsOfType(type);
        }
        Collections.shuffle(events);
    }

    void generateEventsOfType(EventType type) {
        final int ADJUST = 10000;
        double rate = rates.get(type);
        int intRate = (int)Math.floor(rate);
        double prob = rate - intRate;
        int adjustProb = (int) (prob * ADJUST);

        for (int i = 0; i < intRate; i++) {
            events.add(type);
        }
        if(adjustProb > new Random().nextInt(ADJUST)) {
            events.add(type);
        }
    }

    void calculateRates() {
        var workload = dac.workload;
        var configuration = dac.configuration;

        double r = 0.1 * Math.sqrt(workload.getUsers().size());
        double muA = configuration.getDoubleValue("muA");
        double muU = configuration.getDoubleValue("muU");

        rates.put(EventType.ASSIGN_UR, muA * muU * r);
        rates.put(EventType.ASSIGN_PA, muA * (1-muU) * r);
        rates.put(EventType.REVOKE_UR, (1-muA) * muU * r);
        rates.put(EventType.REVOKE_PA, (1-muA) * (1-muU) * r);
    }

    void execute() {
        for(var event : events) {
            functions.get(event).apply(null);
        }
    }

}
