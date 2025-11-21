import java.io.*;
import java.util.Scanner;


public class Main {

    private static final String NOT_REGISTERED = "Some user not registered.";
    private static final String EVENT_ALREADY_EXISTS = "Event already exists.";
    private static final String PROPOSER_NOT_AVAILABLE = "Proposer not available.";
    private static final String USER_NOT_AVAILABLE = "Some user not available.";
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

        //String fileName = input.nextLine();

       // FileReader reader = new FileReader("src/" + fileName);
       // Scanner in = new Scanner(reader);

        SharedCalendarApp app = new SharedCalendarApp();
        initialize(in, app);

        handleInput(input, app);
        //in.close();
        input.close();
    }

    private static void handleInput(Scanner input, SharedCalendarApp app){

        String command = input.next().trim();

        do {
            switch (command) {
                case CREATE -> handleCreate(input, app);
                case SCHEDULE -> handleSchedule(input, app);
                case CANCEL -> handleCancel(input, app);
                case SHOW -> handleShow(input, app);
                case TOP -> handleTop(app);
                default -> System.out.println(INVALID_COMMAND);
            }
            command = input.next().trim();

        }while (!command.equals(EXIT));

        System.out.println(APP_EXITED);
    }

    private static void initialize(Scanner in, SharedCalendarApp app){
        int numberOfUsers = in.nextInt();
        in.nextLine();
        for(int i=0; i<numberOfUsers; i++){
            handleCreate(in,app);
        }
        int numberOfEvents = in.nextInt();
        for(int i=0; i<numberOfEvents; i++){
            handleSchedule(in,app);
        }
    }

    private static void handleCreate(Scanner in, SharedCalendarApp app){
        String name = in.nextLine().trim();
        if(app.doesUserExist(name)){
            System.out.println("User already registered.");
        }else{
            app.addUser(name);
        }
    }

    private static void handleSchedule(Scanner in,SharedCalendarApp app){
        String eventName = in.next().trim();
        int day = in.nextInt();
        int startHour = in.nextInt();
        int endHour = in.nextInt();
        in.nextLine();
        int numOfParticipants = in.nextInt();
        in.nextLine();
        String proposer = in.next().trim();

        String[] participants = new String[numOfParticipants];
        boolean canAdd = true;

        for(int i=0; i<numOfParticipants; i++){
            participants[i] = in.next().trim();
            if(!app.doesUserExist(participants[i])){
                System.out.println(NOT_REGISTERED);
                canAdd = false;
            }
        }

        if(canAdd && app.doesEventExist(eventName)){
            System.out.println(EVENT_ALREADY_EXISTS);
            canAdd = false;
        }

        if(canAdd && !app.isUserAvailable(proposer)){
            System.out.println(PROPOSER_NOT_AVAILABLE);
            canAdd = false;
        }

        if(canAdd){
            boolean userUnavailable = false;
            for(int i=0; i<numOfParticipants; i++){
                if(!app.isUserAvailable(participants[i], day, startHour, endHour)){
                    if(!userUnavailable){
                        System.out.println(USER_NOT_AVAILABLE);
                        userUnavailable = true;
                    }
                    canAdd = false;
                }
            }
        }

        if(canAdd){
            app.addEvent(eventName, day, startHour, endHour, numOfParticipants, proposer);
        }
    }

    private static void handleCancel(Scanner in, SharedCalendarApp app){
        String eventName = in.next().trim();
        String userName = in.nextLine().trim();
        if(!app.doesUserExist(userName)){
            System.out.println("User not registered.");
        }else if(!app.isEventOnCalendar(eventName, userName)){
            System.out.printf("Event not found in calendar of %s.\n", userName);
        }else if(!app.isUserProposer(eventName, userName)){
            System.out.printf("User %s did not create event %s.\n", userName, eventName);
        }else{
            app.cancelEvent(eventName);
        }
    }

    private static void handleShow(Scanner in, SharedCalendarApp app){
        String userName = in.nextLine().trim();
        if(!app.doesUserExist(userName)){
            System.out.println("User not registered.");
        }else if(app.isCalendarEmpty(userName)){
            System.out.printf("User %s has no events.\n", userName);
        }else{
            Iterator it = app.getEventsIterator(userName);
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
        if(app.isEventCalendarEmpty()){
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
                System.out.printf("%s: %d participants",eventName, numOfParticipants);
                System.out.printf(", day %d, %d-%d\n", day, startHour, endHour);
            }
        }
    }
    
    private static void addEventToUsers(SharedCalendarApp app, String eventName, String[] participants){
        for (int i = 0; i < participants.length; i++) {
            String userName = participants[i];
            app.registerEventToUsers(eventName, userName);
        }
    }
}


