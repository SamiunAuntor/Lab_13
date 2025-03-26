import java.util.Random;

public class RandomGenerator {
    private String randomNum;
    private static final Random RANDOM = new Random();

    private static final String[][] DESTINATIONS = {
            {"Karachi", "24.871940", "66.988060"}, {"Bangkok", "13.921430", "100.595337"},
            {"Jakarta", "-6.174760", "106.827072"}, {"Islamabad", "33.607587", "73.100316"},
            {"New York City", "40.642422", "-73.781749"}, {"Lahore", "31.521139", "74.406519"},
            {"Gilgit Baltistan", "35.919108", "74.332838"}, {"Jeddah", "21.683647", "39.152862"},
            {"Riyadh", "24.977080", "46.688942"}, {"New Delhi", "28.555764", "77.096520"},
            {"Hong Kong", "22.285005", "114.158339"}, {"Beijing", "40.052121", "116.609609"},
            {"Tokyo", "35.550899", "139.780683"}, {"Kuala Lumpur", "2.749914", "101.707160"},
            {"Sydney", "-33.942028", "151.174304"}, {"Melbourne", "-37.671812", "144.846079"},
            {"Cape Town", "-33.968879", "18.596982"}, {"Madrid", "40.476938", "-3.569428"},
            {"Dublin", "53.424077", "-6.256792"}, {"Johannesburg", "25.936834", "27.925890"},
            {"London", "51.504473", "0.052271"}, {"Los Angeles", "33.942912", "-118.406829"},
            {"Brisbane", "-27.388925", "153.116751"}, {"Amsterdam", "52.308100", "4.764170"},
            {"Stockholm", "59.651236", "17.924793"}, {"Frankfurt", "50.050085", "8.571911"},
            {"New Taipei City", "25.066471", "121.551638"}, {"Rio de Janeiro", "-22.812160", "-43.248636"},
            {"Seoul", "37.558773", "126.802822"}, {"Yokohama", "35.462819", "139.637008"},
            {"Ankara", "39.951898", "32.688792"}, {"Casablanca", "33.368202", "-7.580998"},
            {"Shenzhen", "22.633977", "113.809360"}, {"Baghdad", "33.264824", "44.232014"},
            {"Berlin", "52.554316", "13.291213"}, {"Paris", "48.999560", "2.539274"}, {"Dubai", "25.249869", "55.366483"}
    };

    public void generateRandomID() {
        int randomID;
        do {
            randomID = RANDOM.nextInt(1_000_000);
        } while (randomID < 20_000);
        this.randomNum = String.valueOf(randomID);
    }

    public String[][] generateRandomDestinations() {
        int city1, city2;
        do {
            city1 = RANDOM.nextInt(DESTINATIONS.length);
            city2 = RANDOM.nextInt(DESTINATIONS.length);
        } while (city1 == city2);

        return new String[][] {
                DESTINATIONS[city1], DESTINATIONS[city2]
        };
    }

    public int generateRandomNumOfSeats() {
        return 75 + RANDOM.nextInt(426);
    }

    public String generateRandomFlightNumber(int letterCount, int divisor) {
        StringBuilder flightNumber = new StringBuilder();
        for (int i = 0; i < letterCount; i++) {
            flightNumber.append((char) ('A' + RANDOM.nextInt(26)));
        }
        flightNumber.append("-").append(generateRandomNumOfSeats() / divisor);
        return flightNumber.toString();
    }

    public String getRandomNumber() {
        return randomNum;
    }
}
