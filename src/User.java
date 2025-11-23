public class User {

    private String name;
    private Calendar calendar;

    public User(String name){
        this.name = name;
        this.calendar = new Calendar();
    }

    public String getName() {
        return this.name;
    }

    public boolean isFree(int day, int startHour, int endHour){
        return calendar.isFree(day, startHour, endHour);
    }

    public boolean hasEvent(String eventName) {
        return calendar.containsEvent(eventName);
    }

    public boolean isCalendarEmpty() {
        return calendar.isEmpty();
    }

    public void cancelEvent(String eventName) {
        calendar.removeEvent(eventName);
    }

    public Iterator getSortedIterator() {
        return calendar.getSortedIterator();
    }

    public void addEvent(Event event) {
        calendar.addEvent(event);
    }
}
