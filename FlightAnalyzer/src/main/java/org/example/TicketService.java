package org.example;



import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class TicketService {
    private final List<Ticket> tickets;

    public TicketService(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TicketsWrapper wrapper = mapper.readValue(new File(filePath), TicketsWrapper.class);
        this.tickets = wrapper.getTickets().stream()
                .filter(t -> "VVO".equals(t.getOrigin()) && "TLV".equals(t.getDestination()))
                .collect(Collectors.toList());
    }

    public Map<String, Duration> getMinFlightTimes() {
        Map<String, Duration> result = new HashMap<>();
        tickets.forEach(ticket -> {
            Duration duration = TimeUtils.getFlightDuration(
                    ticket.getDepartureDate(), ticket.getDepartureTime(),
                    ticket.getArrivalDate(), ticket.getArrivalTime()
            );
            result.merge(ticket.getCarrier(), duration, (d1, d2) -> d1.compareTo(d2) < 0 ? d1 : d2);
        });
        return result;
    }

    public double getAveragePrice() {
        return tickets.stream().mapToInt(Ticket::getPrice).average().orElse(0);
    }

    public double getMedianPrice() {
        List<Integer> prices = tickets.stream().map(Ticket::getPrice).sorted().toList();
        int n = prices.size();
        if (n == 0) return 0;
        if (n % 2 == 1) return prices.get(n / 2);
        return (prices.get(n / 2 - 1) + prices.get(n / 2)) / 2.0;
    }
}