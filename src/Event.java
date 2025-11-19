public class Event {

    private final String name;
    private final int startHour;
    private final int day;
    private final int endHour;
    private final int numOfParticipants;
    private final String proposer;

    public Event(String name, int day, int startHour, int endHour, int numOfParticipants, String proposer){
        this.name = name;
        this.startHour = startHour;
        this.day = day;
        this.endHour = endHour;
        this.numOfParticipants = numOfParticipants;
        this.proposer = proposer;
    }

    public String getName(){
        return this.name;
    }

    public int getStartHour(){
        return this.startHour;
    }

    public int getDay(){
        return this.day;
    }

    public int getEndHour(){
        return this.endHour;
    }

    public int getNumOfParticipants(){
        return this.numOfParticipants;
    }

    public String getProposer(){
        return this.proposer;
    }

}
