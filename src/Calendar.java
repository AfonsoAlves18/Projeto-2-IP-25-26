public class Calendar {

    Event[] calendar;
    private static final int DEFAULT_CAPACITY = 10;
    private int size;

    public Calendar(){
        this.calendar = new Event[DEFAULT_CAPACITY];
        this.size = 0;
    }


    public void addEvent(Event event){
        if(isFull())
            resize();


        this.calendar[this.size] = event;
        this.size++;
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

}
