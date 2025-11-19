public class Calendar {

    Event[] calendar;
    private static final int DEFAULT_CAPACITY = 10;
    private int size;

    public Calendar(){
        this.calendar = new Event[DEFAULT_CAPACITY];
        this.size = 0;
    }

}
