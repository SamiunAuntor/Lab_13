import java.util.List;
import java.util.Scanner;
import java.util.Iterator;

public class FlightReservation implements DisplayClass {

    // Flight instance to access flight list
    private Flight flight = new Flight();

    /**
     * Book tickets for a flight.
     */
    public void bookFlight(String flightNo, int numOfTickets, String userID) {
        Flight foundFlight = findFlightByNumber(flightNo);
        if (foundFlight == null) {
            System.out.println("Invalid Flight Number...! No flight with the ID \"" + flightNo + "\" was found...");
            return;
        }

        Customer customer = findCustomerByUserId(userID);
        if (customer == null) {
            System.out.println("Invalid User ID...! No user with the ID \"" + userID + "\" was found...");
            return;
        }

        // Update seats and register customer
        foundFlight.setNoOfSeatsInTheFlight(foundFlight.getNoOfSeats() - numOfTickets);

        if (!foundFlight.isCustomerAlreadyAdded(foundFlight.getListOfRegisteredCustomersInAFlight(), customer)) {
            foundFlight.addNewCustomerToFlight(customer);
        }

        int customerFlightIndex = getFlightIndex(customer.getFlightsRegisteredByUser(), foundFlight);
        if (customerFlightIndex != -1) {
            addNumberOfTicketsToAlreadyBookedFlight(customer, numOfTickets, customerFlightIndex);
            customer.addExistingFlightToCustomerList(customerFlightIndex, numOfTickets);
        } else {
            customer.addNewFlightToCustomerList(foundFlight);
            addNumberOfTicketsForNewFlight(customer, numOfTickets);
        }

        System.out.printf("\n %50s You've booked %d tickets for Flight \"%5s\"...", "", numOfTickets, flightNo.toUpperCase());
    }

    /**
     * Cancel a flight booking.
     */
    public void cancelFlight(String userID) {
        Customer customer = findCustomerByUserId(userID);
        if (customer == null) {
            System.out.println("Invalid User ID. No customer found with ID \"" + userID + "\".");
            return;
        }

        if (customer.getFlightsRegisteredByUser().isEmpty()) {
            System.out.println("No Flight has been registered by you.");
            return;
        }

        // Display registered flights and handle cancellation
        handleFlightCancellation(customer);
    }

    /**
     * Find flight by flight number.
     */
    private Flight findFlightByNumber(String flightNo) {
        for (Flight f : flight.getFlightList()) {
            if (flightNo.equalsIgnoreCase(f.getFlightNumber())) {
                return f;
            }
        }
        return null;
    }

    /**
     * Find customer by user ID.
     */
    private Customer findCustomerByUserId(String userID) {
        for (Customer customer : Customer.customerCollection) {
            if (userID.equals(customer.getUserID())) {
                return customer;
            }
        }
        return null;
    }

    /**
     * Get index of a flight in a customer's list.
     */
    private int getFlightIndex(List<Flight> flightList, Flight targetFlight) {
        return flightList.indexOf(targetFlight);
    }

    /**
     * Add more tickets to an already booked flight.
     */
    private void addNumberOfTicketsToAlreadyBookedFlight(Customer customer, int numOfTickets, int flightIndex) {
        int newNumOfTickets = customer.getNumOfTicketsBookedByUser().get(flightIndex) + numOfTickets;
        customer.getNumOfTicketsBookedByUser().set(flightIndex, newNumOfTickets);
    }

    /**
     * Add tickets for a new flight booking.
     */
    private void addNumberOfTicketsForNewFlight(Customer customer, int numOfTickets) {
        customer.getNumOfTicketsBookedByUser().add(numOfTickets);
    }

    /**
     * Handle flight cancellation process.
     */
    private void handleFlightCancellation(Customer customer) {
        // Display flights registered by customer
        displayFlightsRegisteredByOneUser(customer.getUserID());

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the Flight Number of the Flight you want to cancel: ");
        String flightNum = scanner.nextLine();
        System.out.print("Enter the number of tickets to cancel: ");
        int numOfTickets = scanner.nextInt();

        boolean flightFound = false;
        int index = 0;
        Iterator<Flight> flightIterator = customer.getFlightsRegisteredByUser().iterator();
        while (flightIterator.hasNext()) {
            Flight registeredFlight = flightIterator.next();
            if (flightNum.equalsIgnoreCase(registeredFlight.getFlightNumber())) {
                flightFound = true;
                int numOfTicketsForFlight = customer.getNumOfTicketsBookedByUser().get(index);

                while (numOfTickets > numOfTicketsForFlight) {
                    System.out.print("ERROR!!! Number of tickets cannot be greater than " + numOfTicketsForFlight + " for this flight. Please enter the number of tickets again: ");
                    numOfTickets = scanner.nextInt();
                }

                int ticketsToBeReturned;
                if (numOfTicketsForFlight == numOfTickets) {
                    ticketsToBeReturned = registeredFlight.getNoOfSeats() + numOfTicketsForFlight;
                    customer.getNumOfTicketsBookedByUser().remove(index);
                    flightIterator.remove();
                } else {
                    ticketsToBeReturned = registeredFlight.getNoOfSeats() + numOfTickets;
                    customer.getNumOfTicketsBookedByUser().set(index, (numOfTicketsForFlight - numOfTickets));
                }
                registeredFlight.setNoOfSeatsInTheFlight(ticketsToBeReturned);
                break;
            }
            index++;
        }

        if (!flightFound) {
            System.out.println("ERROR!!! Couldn't find Flight with ID \"" + flightNum.toUpperCase() + "\".....");
        }
    }

    /**
     * Show flight status.
     */
    private String flightStatus(Flight flightToCheck) {
        for (Flight f : flight.getFlightList()) {
            if (f.getFlightNumber().equalsIgnoreCase(flightToCheck.getFlightNumber())) {
                return "As Per Schedule";
            }
        }
        return "Cancelled";
    }

    // Display Methods

    @Override
    public void displayFlightsRegisteredByOneUser(String userID) {
        for (Customer customer : Customer.customerCollection) {
            if (userID.equals(customer.getUserID())) {
                List<Flight> flights = customer.getFlightsRegisteredByUser();
                for (int i = 0; i < flights.size(); i++) {
                    System.out.println(formatFlightInfo(i + 1, flights.get(i), customer));
                }
            }
        }
    }

    @Override
    public void displayHeaderForUsers(Flight flightObj, List<Customer> customers) {
        for (int i = 0; i < customers.size(); i++) {
            int custFlightIndex = getFlightIndex(customers.get(i).getFlightsRegisteredByUser(), flightObj);
            System.out.println(formatCustomerInfo(i, customers.get(i), custFlightIndex));
        }
    }

    @Override
    public void displayRegisteredUsersForAllFlight() {
        for (Flight flightObj : flight.getFlightList()) {
            List<Customer> customers = flightObj.getListOfRegisteredCustomersInAFlight();
            if (!customers.isEmpty()) {
                displayHeaderForUsers(flightObj, customers);
            }
        }
    }

    @Override
    public void displayRegisteredUsersForASpecificFlight(String flightNum) {
        for (Flight flightObj : flight.getFlightList()) {
            if (flightObj.getFlightNumber().equalsIgnoreCase(flightNum)) {
                displayHeaderForUsers(flightObj, flightObj.getListOfRegisteredCustomersInAFlight());
            }
        }
    }

    /**
     * Format flight details.
     */
    public String formatFlightInfo(int serialNum, Flight flightInfo, Customer customer) {
        return String.format("| %-5d| %-41s | %-9s | \t%-9d | %-21s | %-22s | %-10s  |   %-6sHrs |  %-4s  | %-10s |",
                serialNum,
                flightInfo.getFlightSchedule(),
                flightInfo.getFlightNumber(),
                customer.getNumOfTicketsBookedByUser().get(serialNum - 1),
                flightInfo.getFromWhichCity(),
                flightInfo.getToWhichCity(),
                flightInfo.fetchArrivalTime(),
                flightInfo.getFlightTime(),
                flightInfo.getGate(),
                flightStatus(flightInfo));
    }

    /**
     * Format customer details.
     */
    public String formatCustomerInfo(int serialNum, Customer customer, int index) {
        return String.format("%10s| %-10d | %-10s | %-32s | %-7s | %-27s | %-35s | %-23s |       %-7s  |",
                "", serialNum + 1,
                customer.randomIDDisplay(customer.getUserID()),
                customer.getName(),
                customer.getAge(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getPhone(),
                customer.getNumOfTicketsBookedByUser().get(index));
    }
}
