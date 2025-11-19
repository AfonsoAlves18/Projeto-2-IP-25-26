import java.io.*;
import java.util.Scanner;


public class Main {

    private static final String NOT_REGISTERED = "Some user not registered.";

    public static void main(String[] args) throws FileNotFoundException{

        Scanner input = new Scanner(System.in);

        String fileName = input.nextLine();

        FileReader reader = new FileReader("src/" + fileName);
        Scanner in = new Scanner(reader);

        SharedCalendarApp app = new SharedCalendarApp();
        initialize(in, app);





    }

    private static void initialize(Scanner in, SharedCalendarApp app){
        int numberOfUsers = in.nextInt();
        in.nextLine();
        app.initUsersArray(numberOfUsers);

        for(int i=0; i<numberOfUsers; i++){
            String name = in.nextLine();
            app.addUser(name);
        }
        initializeEvents(in,app);


    }

    private static void initializeEvents(Scanner in,SharedCalendarApp app){
        int numberOfEvents = in.nextInt();
        app.initEventsArray(numberOfEvents);
        in.nextLine();
        for(int i = 0; i < numberOfEvents; i++){
            String name = in.next();
            int day = in.nextInt();
            int startHour = in.nextInt();
            int endHour = in.nextInt();
            in.nextLine();
            int numOfParticipants = in.nextInt();
            in.nextLine();
            String proposer = in.next();
            app.addEvent(name, day, startHour, endHour, numOfParticipants, proposer);
        }
    }
}