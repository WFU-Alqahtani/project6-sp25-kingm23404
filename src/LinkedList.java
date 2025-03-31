import java.util.Random;

// linked list class for a deck of cards
public class LinkedList {

    public Node head;
    public Node tail;
    public int size = 0;

    LinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public void shuffle(int shuffle_count) {

        Random rand = new Random();
        for (int i = 0; i < shuffle_count; i++) {
            // pick two random integers
            int r1 = rand.nextInt(52);
            int r2 = rand.nextInt(52);

            swap(r1, r2); // swap nodes at these indices
        }
    }

    // remove a card from a specific index
    public Card remove_from_index(int index) {
        if (index < 0 || index >= size) return null;

        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        if (current.prev != null) {
            current.prev.next = current.next;
        } else {
            head = current.next;
        }
        if (current.next != null) {
            current.next.prev = current.prev;
        } else {
            tail = current.prev;
        }
        size--;
        return current.data;
    }


    // insert a card at a specific index
    public void insert_at_index(Card x, int index) {
        if (index < 0 || index > size) return; // Invalid index

        Node newNode = new Node(x);
        if (index == 0) {
            newNode.next = head;
            if (head != null) {
                head.prev = newNode;
            }
            head = newNode;
            if (tail == null) {
                tail = newNode;
            }
        } else {
            Node current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }

            newNode.next = current.next;
            if (current.next != null) {
                current.next.prev = newNode;
            }
            current.next = newNode;
            newNode.prev = current;

            if (newNode.next == null) {
                tail = newNode;
            }
        }
        size++;
    }


    // swap two cards in the deck at the specific indices
    public void swap(int index1, int index2) {
        if (index1 == index2 || index1 < 0 || index1 >= size || index2 < 0 || index2 >= size) return;

        // Find the nodes at index1 and index2
        Node node1 = head;
        Node node2 = head;
        for (int i = 0; i < index1; i++) node1 = node1.next;
        for (int i = 0; i < index2; i++) node2 = node2.next;

        // If node1 is right before node2
        if (node1.next == node2) {
            Node prev1 = node1.prev;
            Node next2 = node2.next;

            if (prev1 != null) prev1.next = node2;
            node2.prev = prev1;
            node2.next = node1;
            node1.prev = node2;
            node1.next = next2;
            if (next2 != null) next2.prev = node1;

            // Update head and tail if needed
            if (node1 == head) head = node2;
            if (node2 == tail) tail = node1;
        }
        // If node2 is right before node1
        else if (node2.next == node1) {
            swap(index2, index1);
        }

        else {
            Node prev1 = node1.prev;
            Node next1 = node1.next;
            Node prev2 = node2.prev;
            Node next2 = node2.next;


            if (prev1 != null) prev1.next = node2;
            if (prev2 != null) prev2.next = node1;
            node1.prev = prev2;
            node2.prev = prev1;


            if (next1 != null) next1.prev = node2;
            if (next2 != null) next2.prev = node1;
            node1.next = next2;
            node2.next = next1;


            if (node1 == head) head = node2;
            else if (node2 == head) head = node1;
            if (node1 == tail) tail = node2;
            else if (node2 == tail) tail = node1;
        }
    }



    // add card at the end of the list
    public void add_at_tail(Card data) {
        Node newNode = new Node(data);
        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    // remove a card from the beginning of the list
    public Card remove_from_head() {
        if (head == null) {
            return null;
        }

        Card data = head.data;
        head = head.next;
        if (head != null) {
            head.prev = null;
        } else {
            tail = null; // if the list is now empty
        }
        size--;
        return data;
    }



    // check to make sure the linked list is implemented correctly by iterating forwards and backwards
        // and verifying that the size of the list is the same when counted both ways.
        // 1) if a node is incorrectly removed
        // 2) and head and tail are correctly updated
        // 3) each node's prev and next elements are correctly updated
        public void sanity_check () {
            // count nodes, counting forward
            Node curr = head;
            int count_forward = 0;
            while (curr != null) {
                curr = curr.next;
                count_forward++;
            }

            // count nodes, counting backward
            curr = tail;
            int count_backward = 0;
            while (curr != null) {
                curr = curr.prev;
                count_backward++;
            }

            // check that forward count, backward count, and internal size of the list match
            if (count_backward == count_forward && count_backward == size) {
                System.out.println("Basic sanity Checks passed");
            } else {
                // there was an error, here are the stats
                System.out.println("Count forward:  " + count_forward);
                System.out.println("Count backward: " + count_backward);
                System.out.println("Size of LL:     " + size);
                System.out.println("Sanity checks failed");
                System.exit(-1);
            }
        }

        // print the deck
        public void print () {
            Node curr = head;
            int i = 1;
            while (curr != null) {
                curr.data.print_card();
                if (curr.next != null)
                    System.out.print(" -->  ");
                else
                    System.out.println(" X");

                if (i % 7 == 0) System.out.println("");
                i = i + 1;
                curr = curr.next;
            }
            System.out.println("");
        }
    }
