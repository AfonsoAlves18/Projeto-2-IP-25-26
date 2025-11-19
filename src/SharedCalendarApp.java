public class SharedCalendarApp {

    User[] users;
    Event[] events;
    int index;

    public SharedCalendarApp(){
        this.index = 0;
    }

    private User getUser(String userName ){

        for(int i = 0; i<users.length; i++){
            if(userName.equals(users[i].getName())){
                return users[i];
            }
        }
        return null;
    }

    public void addUser(String name){
        users[index]= new User(name);
        index++;
    }

    public void addEvent(String name, int day, int startHour, int endHour, int numOfParticipants, String proposer){

    }

    public void initUsersArray(int numberUsers){
        this.users = new User[numberUsers];
    }

    public void initEventsArray(int numberEvents){
        this.events = new Event[numberEvents];
    }

    public void addEventToUser(String userName, String eventName){

    }


}
