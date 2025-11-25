public class SharedCalendarApp {

    private static final int DEFAULT_CAPACITY = 100;

    User[] users;
    Event[] events;
    int numOfUsers;
    int numOfEvents;

    public SharedCalendarApp(){
        this.users = new User[DEFAULT_CAPACITY];
        this.events = new Event[DEFAULT_CAPACITY];
        this.numOfUsers = 0;
        this.numOfEvents = 0;
    }

    private User getUser(String userName ) {
        for(int i = 0; i<numOfUsers; i++){
            if(userName.equals(users[i].getName())){
                return users[i];
            }
        }
        return null;
    }

    public boolean doesUserExist(String name){
        return getUser(name) != null;
    }

    public void addUser(String name){
        if(isUsersFull())
            resizeUsers();

        users[numOfUsers]= new User(name);
        numOfUsers++;
    }

    private boolean isUsersFull(){
        return numOfUsers == users.length;
    }

    private void resizeUsers(){
        User[] temp = new User[users.length*2];
        for(int i = 0; i<users.length; i++){
            temp[i] = users[i];
        }
        this.users = temp;
    }

    private Event getEvent(String eventName ){
        for(int i = 0; i<numOfEvents; i++){
            if(eventName.equals(events[i].getName())){
                return events[i];
            }
        }
        return null;
    }

    public boolean doesEventExist(String name){
        return getEvent(name) != null;
    }

    public void addEvent(String eventName ,int day, int startHour, int endHour, int numOfParticipants, String proposer){
        if(isEventsFull())
            resizeEvents();

        Event event = new Event(eventName,day,startHour,endHour,numOfParticipants,proposer);
        events[numOfEvents] = event;
        numOfEvents++;
    }

    private boolean isEventsFull(){
        return numOfEvents == events.length;
    }

    private void resizeEvents(){
        Event[] temp = new Event[events.length*2];
        for(int i = 0; i<events.length; i++){
            temp[i] = events[i];
        }
        this.events = temp;
    }

    public void registerEventToUsers(String eventName, String userName){
        Event event = getEvent(eventName);
        User user = getUser(userName);
        user.addEvent(event);
    }


    public boolean isUserAvailable(String participant, int day, int startHour, int endHour){
        User user = getUser(participant);
        return user.isFree(day, startHour, endHour);
    }

    public boolean isEventOnCalendar(String eventName, String userName) {
        User user = getUser(userName);
        return user.hasEvent(eventName);
    }

    public boolean isUserProposer(String userName, String eventName) {
        Event event = getEvent(eventName);
        return event.getProposer().equals(userName);
    }

    public boolean isCalendarEmpty(String userName) {
        User user = getUser(userName);
        return user.isCalendarEmpty();
    }

    public boolean isEventArrayEmpty() {
        return numOfEvents == 0;
    }

    public void cancelEvent(String eventName) {
        for(int i=0; i<numOfUsers; i++){
            User user = users[i];
            if(user.hasEvent(eventName)){
                user.cancelEvent(eventName);
            }
        }
        removeEvent(eventName);
    }

    private void removeEvent(String eventName) {
        for(int i = 0; i < numOfEvents; i++){
            if(events[i].getName().equals(eventName)){
                for(int j = i; j < numOfEvents - 1; j++){
                    events[j] = events[j + 1];
                }
                events[numOfEvents - 1] = null;
                numOfEvents--;
                return;
            }
        }
    }

    public Iterator getShowEventsIterator(String userName) {
        User user = getUser(userName);
        return user.getSortedIterator();
    }

    public Iterator getTopEventsIterator(){
        Event[] topEvents = extractTopEvents();
        sortEvents(topEvents);
        return new Iterator(topEvents,topEvents.length);
    }

    private Event[] extractTopEvents(){
        int max = getMostParticipants();
        int count = 0;

        for(int i = 0; i < numOfEvents; i++){
            if(events[i].getNumOfParticipants() == max) count++;
        }

        Event[] top = new Event[count];
        int index = 0;
        for(int i = 0; i<numOfEvents; i++){
            if(events[i].getNumOfParticipants() == max){
                top[index] = events[i];
                index++;
            }
        }
        return top;
    }

    private int getMostParticipants(){
        int max = 0;
        for (int i = 0; i < numOfEvents; i++) {
            max = Math.max(max, events[i].getNumOfParticipants());
        }
        return max;
    }

    private void sortEvents(Event[] topEvents){
        for(int i = 0; i < topEvents.length - 1; i++){
            for(int j = i + 1; j < topEvents.length; j++){
                if(needsSwap(topEvents, j, i)){
                    Event temp = topEvents[i];
                    topEvents[i] = topEvents[j];
                    topEvents[j] = temp;
                }
            }
        }
    }


    private boolean needsSwap(Event[] topEvents, int j, int i) {
        Event a = topEvents[i];
        Event b = topEvents[j];

        if (b.getDay() < a.getDay()) return true;
        if (b.getDay() > a.getDay()) return false;

        if (b.getStartHour() < a.getStartHour()) return true;
        if (b.getStartHour() > a.getStartHour()) return false;

        if (b.getEndHour() < a.getEndHour()) return true;
        if (b.getEndHour() > a.getEndHour()) return false;

        return b.getName().compareTo(a.getName()) < 0;
    }

}
