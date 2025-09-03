package org.example;

import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Укажите путь к файлу tickets.json");
            return;
        }
        try {
            TicketService service = new TicketService(args[0]);

            System.out.println("Минимальное время полета по авиаперевозчикам:");
            for (var entry : service.getMinFlightTimes().entrySet()) {
                Duration d = entry.getValue();
                System.out.println(entry.getKey() + ": " + TimeUtils.formatDuration(d));
            }

            double avg = service.getAveragePrice();
            double median = service.getMedianPrice();
            System.out.printf("Разница между средней ценой и медианой: %.2f%n", avg - median);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
