static class Event implements Comparable<Event>{
    boolean isOpen;
    int x;
    int idx;

    Event(boolean isOpen, int x, int idx) {
        this.isOpen = isOpen;
        this.x = x;
        this.idx = idx;
    }

    @Override
    public int compareTo(Event other) {
        if(this.x!=other.x) 
            return this.x<other.x ?-1 : +1;
        if(this.isOpen!=other.isOpen) {
            return this.isOpen?-1:+1;
        }
        return this.idx-other.idx;
    }
}
