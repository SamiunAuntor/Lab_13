import java.util.*;

class CustomerFlightInfo implements DisplayClass {
    private final List<Flight> registeredFlights;
    private final List<Integer> bookedTickets;

    CustomerFlightInfo() {
        registeredFlights = new ArrayList<>();
        bookedTickets = new ArrayList<>();
    }

    void addFlight(Flight flight) {
        registeredFlights.add(flight);
        bookedTickets.add(1);
    }

    void updateTickets(int index, int additionalTickets) {
        int updatedTickets = bookedTickets.get(index) + additionalTickets;
        bookedTickets.set(index, updatedTickets);
    }

    public List<Flight> getRegisteredFlights() {
        return Collections.unmodifiableList(registeredFlights);
    }

    public List<Integer> getBookedTickets() {
        return Collections.unmodifiableList(bookedTickets);
    }

    @Override
    public void showUsersForAllFlights() {
    }

    @Override
    public void showUsersForSpecificFlight(String flightNum) {
    }

    @Override
    public void showHeaderForUsers(Flight flight, List<Customer> customers) {
    }

    @Override
    public void showFlightsByUser(String userID) {
    }
}
