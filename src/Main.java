import java.io.*;
import java.util.Scanner;


public class Main {

    private static final String NOT_REGISTERED = "Some user not registered.";
    private static final String EVENT_ALREADY_EXISTS = "Event already exists.";
    private static final String PROPOSER_NOT_AVAILABLE = "Proposer not available.";
    private static final String USER_NOT_AVAILABLE = "Some user not available.";
    private static final String USER_ALREADY_REGISTERED = "User already registered.";
    private static final String USER_NOT_REGISTERED = "User not registered.";
    private static final String USER_CREATED = "User successfully created.";
    private static final String EVENT_CREATED = "Event successfully created.";
    private static final String EVENT_CANCELED = "Event successfully canceled.";
    private static final String NO_EVENTS_REGISTERED = "No events registered.";
    private static final String INVALID_COMMAND = "Invalid command.";

    private static final String CREATE = "create";
    private static final String SCHEDULE = "schedule";
    private static final String CANCEL = "cancel";
    private static final String SHOW = "show";
    private static final String TOP = "top";
    private static final String EXIT = "exit";
    private static final String APP_EXITED = "Application exited.";

    public static void main(String[] args) throws FileNotFoundException{

        Scanner input = new Scanner(System.in);

        String fileName = input.nextLine();

        FileReader reader = new FileReader("src/" + fileName);
        Scanner fileScanner = new Scanner(reader);

        SharedCalendarApp app = new SharedCalendarApp();

        initialize(fileScanner, app);
        fileScanner.close();

        handleInput(input, app);
        input.close();
    }



    private static void handleInput(Scanner input, SharedCalendarApp app){

        String command = input.next().trim();

        while(!command.equals(EXIT)){
            switch (command) {
                case CREATE -> handleCreate(input, app);
                case SCHEDULE -> handleSchedule(input, app);
                case CANCEL -> handleCancel(input, app);
                case SHOW -> handleShow(input, app);
                case TOP -> handleTop(app);
                default ->{
                    System.out.println(INVALID_COMMAND);
                    input.nextLine();
                }
            }
            command = input.next().trim();
        }

        System.out.println(APP_EXITED);
    }

    private static void initialize(Scanner in, SharedCalendarApp app){
        int numberOfUsers = in.nextInt();
        in.nextLine();

        String name;

        for(int i=0; i<numberOfUsers; i++){
            name = in.nextLine().trim();
            app.addUser(name);
        }

        int numberOfEvents = in.nextInt();
        in.nextLine();
        String eventName;
        int day;
        int startHour;
        int endHour;
        int numOfParticipants;

        for(int i=0; i<numberOfEvents; i++){
            eventName = in.next().trim();
            day = in.nextInt();
            startHour = in.nextInt();
            endHour = in.nextInt();
            in.nextLine();

            numOfParticipants = in.nextInt();
            in.nextLine();

            String[] participants = readParticipants(in, numOfParticipants);
            app.addEvent(eventName, day, startHour, endHour, numOfParticipants, participants[0]);
            addEventToUsers(app, eventName, participants);
        }
    }

    private static void handleCreate(Scanner in, SharedCalendarApp app){
        String name = in.nextLine().trim();

        if(app.doesUserExist(name)){
            System.out.println(USER_ALREADY_REGISTERED);
        }else{
            app.addUser(name);
            System.out.println(USER_CREATED);
        }
    }

    private static void handleSchedule(Scanner in, SharedCalendarApp app){
        String eventName = in.next().trim();
        int day = in.nextInt();
        int startHour = in.nextInt();
        int endHour = in.nextInt();
        in.nextLine();

        int numOfParticipants = in.nextInt();
        in.nextLine();

        String[] participants = readParticipants(in, numOfParticipants);
        boolean canAdd;

        canAdd = validateParticipantsRegistered(app,participants);

        if (canAdd) canAdd = validateEventDoesNotExist(app,eventName);

        if (canAdd) canAdd = validateProposerAvailable(app,participants[0],day,startHour,endHour);

        if (canAdd) canAdd = validateAllUsersAvailable(app,participants,day,startHour,endHour);

        if (canAdd) createAndRegisterEvent(app,eventName,day,startHour,endHour,participants);
    }


    private static String[] readParticipants(Scanner in, int numOfParticipants) {
        String[] participants = new String[numOfParticipants];
        for (int i = 0; i < numOfParticipants; i++) {
            participants[i] = in.next().trim();
        }
        return participants;
    }

    private static boolean validateParticipantsRegistered(SharedCalendarApp app, String[] participants) {
        boolean allRegistered = true;
        for (int i = 0; i < participants.length; i++) {
            if (!app.doesUserExist(participants[i])) {
                allRegistered = false;
            }
        }
        if(!allRegistered){
            System.out.println(NOT_REGISTERED);
        }
        return allRegistered;
    }

    private static boolean validateEventDoesNotExist(SharedCalendarApp app, String eventName) {
        if (app.doesEventExist(eventName)) {
            System.out.println(EVENT_ALREADY_EXISTS);
            return false;
        }
        return true;
    }

    private static boolean validateProposerAvailable(SharedCalendarApp app, String proposer, int day,
                                                     int startHour, int endHour) {
        if (!app.isUserAvailable(proposer, day, startHour, endHour)) {
            System.out.println(PROPOSER_NOT_AVAILABLE);
            return false;
        }
        return true;
    }

    private static boolean validateAllUsersAvailable(SharedCalendarApp app, String[] participants,
                                                     int day, int startHour, int endHour) {

        boolean canAdd = true;

        for (int i = 0; i < participants.length; i++) {
            if (!app.isUserAvailable(participants[i], day, startHour, endHour)) {
                canAdd = false;
            }
        }
        if(!canAdd){
            System.out.println(USER_NOT_AVAILABLE);
        }
        return canAdd;
    }

    private static void createAndRegisterEvent(SharedCalendarApp app, String eventName, int day,
                                               int startHour, int endHour, String[] participants) {
        int numOfParticipants = participants.length;
        String proposer = participants[0];

        app.addEvent(eventName, day, startHour, endHour, numOfParticipants, proposer);
        addEventToUsers(app, eventName, participants);
        System.out.println(EVENT_CREATED);
    }

    private static void addEventToUsers(SharedCalendarApp app, String eventName, String[] participants){
        for (int i = 0; i < participants.length; i++) {
            String userName = participants[i];
            app.registerEventToUsers(eventName, userName);
        }
    }

    private static void handleCancel(Scanner in, SharedCalendarApp app){
        String eventName = in.next().trim();
        String userName = in.nextLine().trim();

        if(!app.doesUserExist(userName)){
            System.out.println(USER_NOT_REGISTERED);
        }else if(!app.isEventOnCalendar(eventName, userName)){
            System.out.printf("Event not found in calendar of %s.\n", userName);
        }else if(!app.isUserProposer(userName, eventName)){
            System.out.printf("User %s did not create event %s.\n", userName, eventName);
        }else{
            app.cancelEvent(eventName);
            System.out.println(EVENT_CANCELED);
        }
    }

    private static void handleShow(Scanner in, SharedCalendarApp app){
        String userName = in.nextLine().trim();

        if(!app.doesUserExist(userName)){
            System.out.println(USER_NOT_REGISTERED);
        }else if(app.isCalendarEmpty(userName)){
            System.out.printf("User %s has no events.\n", userName);
        }else{
            Iterator it = app.getShowEventsIterator(userName);
            while(it.hasNext()){
                Event event = it.next();
                String eventName = event.getName();
                int day = event.getDay();
                int startHour = event.getStartHour();
                int endHour = event.getEndHour();
                int numOfParticipants = event.getNumOfParticipants();
                System.out.printf("%s, day %d, %d-%d, %d participants.\n",
                        eventName, day, startHour, endHour, numOfParticipants);
            }
        }
    }

    private static void handleTop(SharedCalendarApp app){
        if(app.isEventArrayEmpty()){
            System.out.println(NO_EVENTS_REGISTERED);
        }else{
            Iterator it = app.getTopEventsIterator();
            while (it.hasNext()) {
                Event event = it.next();
                String eventName = event.getName();
                int numOfParticipants = event.getNumOfParticipants();
                int day = event.getDay();
                int startHour = event.getStartHour();
                int endHour = event.getEndHour();
                System.out.printf("%s, day %d, %d-%d, %d participants.\n",
                        eventName, day, startHour, endHour, numOfParticipants);
            }
        }
    }
    

}
