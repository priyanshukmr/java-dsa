import java.io.*;
import java.util.*;

interface StackI<T> {
    void push(T data);
    T pop();
    T top();
    boolean empty();
}

class Stackk<T> implements StackI<T>{
    ArrayList<T> array;
    int top=-1;
    int size=0;
    
    Stackk(int size){
        this.array= new ArrayList<T>(size);
        this.size=size;
    }
    
    public void push(T data) {
        if(top == this.size-1) {
            System.out.println("Stack already full");
            return;
        }
        this.top++;
        if(this.array.size()-1 >= this.top) 
            this.array.set(this.top, data);
        else
            this.array.add(data);
        
        System.out.println("Pushed "+ data);
    }
    
    public T pop() {
        if(this.top==-1) {
            System.out.println("Stack already empty");
            return null;
        }
        return this.array.get(this.top--);
    }
    
    public boolean empty() {
        return this.top==-1;
    }
    
    public T top() {
        if(!this.empty()) {
            return this.array.get(this.top);
        }
        return null;
    }

}

class Main {
    public static void main(String args[]) {
        StackI<Integer> myStack = new Stackk<Integer>(3);
        myStack.push(2);
        myStack.push(3);
        myStack.push(100);
        myStack.push(121);
        System.out.println("Popped "+ myStack.pop());
        System.out.println("Popped "+ myStack.pop());
        System.out.println("Popped "+ myStack.pop());
        myStack.pop();
        myStack.pop();
        System.out.println(myStack.empty());
        System.out.println(myStack.top());
        myStack.push(2023);
        System.out.println(myStack.top());
   }
}
