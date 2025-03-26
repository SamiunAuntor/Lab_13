import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

class FlightSchedule {
    private final String flightSchedule;
    private final String gate;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy, HH:mm a");

    FlightSchedule(String schedule, String gate) {
        this.flightSchedule = schedule;
        this.gate = gate;
    }

    public String getFlightSchedule() {
        return flightSchedule;
    }

    public String getGate() {
        return gate;
    }

    public static String createNewFlightTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, (int) (Math.random() * 7));
        calendar.add(Calendar.HOUR, (int) (Math.random() * 24));

        return LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault()).format(FORMATTER);
    }
}
