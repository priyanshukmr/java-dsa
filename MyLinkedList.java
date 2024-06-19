import java.io.*;
import java.util.*;

interface LinkedListI {
    LinkedListI insert(int data);
    void printList();
    LinkedListI deleteKey(int data); 
    int deletePosition(int pos);
}

class Node {
    private int data;
    private Node next;

    Node(int data) {
        this.data=data;
    }

    public int getData() {
        return this.data;
    }
    public Node getNext() {
        return next;
    }
    public void setNext(Node next) {
        this.next = next;
    }
}

class MyLinkedList implements LinkedListI{
    private Node head;
    
    public Node getHead() {
        return this.head;
    }  
    
    private Node setHead(Node newHead) {
        return this.head=newHead;
    }
    
    public MyLinkedList insert(int data) {
        Node head = this.getHead(); 
        if(head==null) {
            this.head = new Node(data);
            System.out.println("Inserted " + data + " at index 0" );
            this.printList();
            return this;
        }
        int index=1;
        while(head.getNext()!=null) {
            head = head.getNext();
            index++;
        }
        Node newNode = new Node(data);
        head.setNext(newNode);
        System.out.println("Inserted " + data + " at index=" + index );
        this.printList();
        return this;
    }
    
    public MyLinkedList deleteKey(int data) {
        Node head = this.getHead();
        if(head==null) {
            System.out.println("Failed to delete. Linked list is empty!");
        }
        Node prev=null;
        int index=0;
        while(head!=null && head.getData()!=data) {
            prev=head;
            head = head.getNext();
            index++;
        }
        if(head==null) {
            System.out.println(data + " not found, unable to delete");
            return this; 
        }
        if(prev==null)
            this.setHead(head.getNext());
        else
            prev.setNext(head.getNext());
        System.out.println("Deleted "+data+" at index="+index);
        printList();
        return this;
    }
    
    public int  deletePosition(int pos) {
        Node head = this.getHead();
        //case-1 pos=0
        if(pos==0) {
            this.setHead(head.getNext());
            System.out.println("Deleted "+head.getData() + " at index="+pos);
            printList();
            return head.getData();
        }
        //case-2 pos is within valid range
        int i=0;
        Node prev=null;
        while(head!=null && i<=pos){
            if(i==pos) {
                prev.setNext(head.getNext());
                System.out.println("Deleted "+head.getData() + " at index="+pos);
                printList();
                return head.getData();
            }
            prev=head;
            head=head.getNext();
            i++;
        }
        //case-3 pos is beyond last element
        System.out.println("position " +pos+ " is out of bounds");
        return -1;
    }
    
    public void printList() {
        Node head = this.getHead();
        while(head != null) {
            System.out.print(head.getData()+"->");
            head = head.getNext();
        }
        System.out.println("null");
    }
}

class Main {
    public static void main(String args[]) {
        System.out.println("running main()...");
        LinkedListI myList = new MyLinkedList();
        myList.insert(1)
            .insert(2)
            .insert(3)
            .insert(4)
            .insert(5)
            .insert(6)
            .insert(7)
            .insert(8);
        // 1->2->3->4->5->6->7->8->null
        myList.deleteKey(400)
            .deleteKey(1)
            .deleteKey(4)
            .deleteKey(10);
        // 2->3->5->6->7->8->null
        myList.deletePosition(3);
        // 2->3->5->7->8->null
        myList.deletePosition(5);
        myList.deletePosition(4);
        // 2->3->5->7->null
        myList.deletePosition(0);
        // 3->5->7->null
        myList.deletePosition(500);
   }
}
