public class Calendar {

    private Event[] calendar;
    private static final int DEFAULT_CAPACITY = 10;
    private int size;

    public Calendar(){
        this.calendar = new Event[DEFAULT_CAPACITY];
        this.size = 0;
    }


    public void addEvent(Event event){
        if(isFull()) resize();

        this.calendar[this.size] = event;
        this.size++;
    }


    public void removeEvent(String eventName){
        for(int i = 0; i < this.size; i++){
            if(this.calendar[i].getName().equals(eventName)){
                for(int j = i; j < this.size - 1; j++){
                    this.calendar[j] = this.calendar[j + 1];
                }
                this.calendar[this.size - 1] = null;
                this.size--;
                return; //perguntar na aula
            }
        }
    }

    public boolean isFree(int day, int startHour, int endHour) {
        for (int i = 0; i < this.size; i++) {
            Event event = this.calendar[i];
            if (event.getDay() == day) {
                if (startHour < event.getEndHour()   &&   endHour > event.getStartHour()) {
                    return false;
                }
            }
        }
        return true;
    }



    private void resize(){
        Event[] temp = new Event[this.calendar.length*2];
        for(int i = 0; i<this.calendar.length; i++){
            temp[i] = this.calendar[i];
        }
        this.calendar = temp;
    }

    private boolean isFull(){
        return this.size == this.calendar.length;
    }

    public boolean containsEvent(String eventName) {
        for(int i = 0; i < this.size; i++){
            if(this.calendar[i].getName().equals(eventName)){
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    private void sortEvents() {
        Event[] sortedEvents = new Event[this.size];
        for (int i = 0; i < this.size; i++) {
            sortedEvents[i] = this.calendar[i];
        }
        for(int i = 0; i < this.size - 1; i++){
            for(int j = i + 1; j < this.size; j++){
                boolean shouldSwap = false;

                if(sortedEvents[j].getDay() < sortedEvents[i].getDay()){
                    shouldSwap = true;
                } else if(sortedEvents[j].getDay() == sortedEvents[i].getDay()
                        && sortedEvents[j].getStartHour() < sortedEvents[i].getStartHour()){
                    shouldSwap = true;
                }

                if(shouldSwap){
                    Event temp = sortedEvents[i];
                    sortedEvents[i] = sortedEvents[j];
                    sortedEvents[j] = temp;
                }
            }
        }
        this.calendar = sortedEvents;
    }

    public Iterator getSortedIterator() {
        sortEvents();
        return new Iterator(this.calendar, this.size);
    }

}
