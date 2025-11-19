public class SharedCalendarApp {

    User[] users;
    Event[] events;
    private static final int DEFAULT_CAPACITY = 100;
    int numOfUsers;
    int numOfEvents;

    public SharedCalendarApp(){
        this.users = new User[DEFAULT_CAPACITY];
        this.events = new Event[DEFAULT_CAPACITY];
        this.numOfUsers = 0;
        this.numOfEvents = 0;
    }

    private User getUser(String userName ){
        for(int i = 0; i<users.length; i++){
            if(userName.equals(users[i].getName())){
                return users[i];
            }
        }
        return null;
    }

    private Event getEvent(String eventName ){
        for(int i = 0; i<events.length; i++){
            if(eventName.equals(events[i].getName())){
                return events[i];
            }
        }
        return null;
    }

    public void addUser(String name){
        if(isUsersFull())
            resize();

        users[numOfUsers]= new User(name);
        numOfUsers++;
    }

    public boolean doesUserExist(String name){
        return getUser(name) != null;
    }

    public boolean doesEventExist(String name){
        return getEvent(name) != null;
    }

    public void addEvent(String eventName ,int day, int startHour, int endHour, int numOfParticipants, String proposer){
        Event event = new Event(eventName,day,startHour,endHour,numOfParticipants,proposer);
        events[numOfEvents] = event;
        numOfEvents++;
    }

    public void addEventToUser(String userName, String eventName){
        User user = getUser(userName);
        Event event =
        user.addEventToCalendar();
    }

    private boolean isUsersFull(){
        return numOfUsers == users.length;
    }

    private boolean isEventsFull(){
        return numOfEvents == events.length;
    }

    private void resize(){
        User[] temp = new User[users.length*2];
        for(int i = 0; i<users.length; i++){
            temp[i] = users[i];
        }
        this.users = temp;
    }


}
