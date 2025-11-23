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

    private User getUser(String userName ) {
        for(int i = 0; i<numOfUsers; i++){
            if(userName.equals(users[i].getName())){
                return users[i];
            }
        }
        return null;
    }

    private Event getEvent(String eventName ){
        for(int i = 0; i<numOfEvents; i++){
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

    public void registerEventToUsers(String eventName, String userName){
        Event event = getEvent(eventName);
        User user = getUser(userName);
        user.addEvent(event);
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

    public void cancelEvent(String eventName) {
        for(int i=0; i<numOfUsers; i++){
            User user = users[i];
            if(user.hasEvent(eventName)){
                user.cancelEvent(eventName);
                removeEvent(eventName);
            }
        }
    }

    public boolean isCalendarEmpty(String userName) {
        User user = getUser(userName);
        return user.isCalendarEmpty();
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

    public boolean isEventCalendarEmpty() {
        return numOfEvents == 0;
    }

    public Iterator getShowEventsIterator(String userName) {
        User user = getUser(userName);
        return user.getSortedIterator();
    }

    public Iterator getTopEventsIterator(){
        Event[] sortedEvents = new Event[numOfEvents];
        int index = 0;
        for(int i = 0; i<numOfEvents; i++){
            if(events[i].getNumOfParticipants() == getMostParticipants()){
                sortedEvents[index] = events[i];
                index++;
            }
        }

        for(int i = 0; i < index - 1; i++){
            for(int j = i + 1; j < index; j++){
                boolean needsSwapping = needsSwap(sortedEvents, j, i);

                if (needsSwapping) {
                    Event temp = sortedEvents[i];
                    sortedEvents[i] = sortedEvents[j];
                    sortedEvents[j] = temp;
                }
            }
        }
        return new Iterator(sortedEvents,index);
    }

    private int getMostParticipants(){
        int mostParticipants=0;
        for (int i = 0; i < numOfEvents; i++) {
            if(events[i].getNumOfParticipants() > mostParticipants){
                mostParticipants = events[i].getNumOfParticipants();
            }
        }
        return mostParticipants;
    }


    private boolean needsSwap(Event[] sortedEvents, int j, int i) {
        boolean needsSwapping = false;

        if (sortedEvents[j].getDay() < sortedEvents[i].getDay()) {
            needsSwapping = true;
        } else if (sortedEvents[j].getDay() == sortedEvents[i].getDay()
                && sortedEvents[j].getStartHour() < sortedEvents[i].getStartHour()) {
            needsSwapping = true;
        }else if (sortedEvents[j].getStartHour() == sortedEvents[i].getStartHour()
                && sortedEvents[j].getName().compareTo(sortedEvents[i].getName()) < 0) {
            needsSwapping = true;
        }
        return needsSwapping;
    }

}
