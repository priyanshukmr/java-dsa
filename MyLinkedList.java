import java.io.*;
import java.util.*;

interface ILinkedList {
    ILinkedList insert(int data); //return ref
    void printList();
    //int delete(int data); //deleted index
}

class Node {
    int data;
    Node next;

    Node(int data) {
        data=data;
    }

    public int getData() {
        return this.data;
    }
    public Node getNext() {
        return next;
    }
    public Node setNext(Node next) {
        this.next = next;
    }
}

class MyLinkedList implements ILinkedList{
    Node head;
    
    Node getHead() {
        return this.head;
    }  
    
    public int insert(int data) {
        Node head = this.getHead();
        if(head==null) {
            this.head = new Node(data);
            return 0;
        }
        while(head.getNext()!=null) {
            head = head.getNext();
        }
        Node newNode = new Node(data);
        head.setNext(newNode);
        return this;
    }
    
    public void printList() {
        LinkedList head = this.getHead();
        while(head != null) {
            System.out.println(head.getData());
            head = head.getNext();
        }
    }
}

class Main {
    public static void main(String args[]) {
        System.out.println("executing main()...");
    }
}
