public class Iterator {

    Event[] events;
    int max;
    int index;

    public Iterator(Event[] events, int max){
        this.events = events;
        this.index = 0;
        this.max = max;
    }

    public boolean hasNext() {
        return index < max;
    }

    public Event next(){
        Event e = events[index];
        index++;
        return e;
    }
}
