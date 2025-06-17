import java.util.*;
public class TypeDemo {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        while (true) {
            System.out.println("\nChoose storage type:");
            System.out.println("1 - ArrayList");
            System.out.println("2 - LinkedList");
            System.out.println("3 - Queue (LinkedList-based)");
            System.out.println("4 - Exit program");
            System.out.print("Enter choice (1/2/3/4): ");
            String choice = scanner.nextLine().trim();
            if (choice.equals("4")) {
                System.out.println("Exiting program. Goodbye!");
                break;
            }
            System.out.print("Enter numbers separated by space: ");
            String inputLine = scanner.nextLine().trim();
            String[] parts = inputLine.split("\\s+");
            List<Integer> numbers = new ArrayList<>();
            for (String part : parts) {
                try {
                    int num = Integer.parseInt(part);
                    numbers.add(num);
                } catch (NumberFormatException e) {
                    System.out.println("Ignoring invalid input: " + part);
                }
            }
            switch (choice) {
                case "1":
                    processArrayList(numbers);
                    break;
                case "2":
                    processLinkedList(numbers);
                    break;
                case "3":
                    processQueue(numbers);
                    break;
                default:
                    System.out.println("Invalid storage type choice.");
                    continue;
            }
            SinglyLinkedList sll = new SinglyLinkedList();
            for (int num : numbers) {
                sll.add(num);
            }
            System.out.println("\nSingly linked list size: " + sll.size());
            System.out.println("Singly linked list shape: " + sll.shape());
            singlyLinkedListOperations(sll);
        }
        scanner.close();
    }
    static void processArrayList(List<Integer> numbers) {
        System.out.println("Numbers in ArrayList: " + numbers);
    }
    static void processLinkedList(List<Integer> numbers) {
        System.out.println("Numbers in LinkedList: " + numbers);
    }
    static void processQueue(List<Integer> numbers) {
        List<Integer> odds = new ArrayList<>();
        List<Integer> evens = new ArrayList<>();

        for (int num : numbers) {
            if (num % 2 != 0) odds.add(num);
            else evens.add(num);
        }
        System.out.println("Odd numbers: " + joinList(odds));
        System.out.println("Even numbers: " + joinList(evens));
        Queue<Integer> queue = new LinkedList<>(odds);
        while (true) {
            System.out.println("\nQueue operations for Odd numbers:");
            System.out.println("1. Enqueue (Add element)");
            System.out.println("2. Dequeue (Remove element)");
            System.out.println("3. Peek (View front element)");
            System.out.println("4. Print queue");
            System.out.println("5. Exit queue operations");
            System.out.print("Choose option: ");
            String op = scanner.nextLine().trim();
            switch (op) {
                case "1":
                    System.out.print("Enter number to enqueue: ");
                    String toAdd = scanner.nextLine().trim();
                    try {
                        int numToAdd = Integer.parseInt(toAdd);
                        queue.offer(numToAdd);
                        System.out.println("Enqueued " + numToAdd);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number.");
                    }
                    break;
                case "2":
                    Integer removed = queue.poll();
                    if (removed != null) {
                        System.out.println("Dequeued " + removed);
                    } else {
                        System.out.println("Queue is empty.");
                    }
                    break;
                case "3":
                    Integer front = queue.peek();
                    if (front != null) {
                        System.out.println("Front element: " + front);
                    } else {
                        System.out.println("Queue is empty.");
                    }
                    break;
                case "4":
                    System.out.println("Queue contents: " + queue);
                    break;
                case "5":
                    System.out.println("Exiting queue operations.");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
    static String joinList(List<Integer> list) {
        if (list.isEmpty()) return "(none)";
        StringBuilder sb = new StringBuilder();
        for (int num : list) {
            sb.append(num).append(", ");
        }
        return sb.substring(0, sb.length() - 2);
    }
    static class SinglyLinkedList {
        static class Node {
            int data;
            Node next;
            Node(int data) {
                this.data = data;
                this.next = null;
            }
        }
        Node head;
        void add(int data) {
            if (head == null) {
                head = new Node(data);
            } else {
                Node temp = head;
                while (temp.next != null) temp = temp.next;
                temp.next = new Node(data);
            }
        }
        boolean insertAt(int index, int data) {
            if (index < 0) return false;
            if (index == 0) {
                Node newNode = new Node(data);
                newNode.next = head;
                head = newNode;
                return true;
            }
            Node temp = head;
            for (int i = 0; i < index - 1; i++) {
                if (temp == null) return false;
                temp = temp.next;
            }
            if (temp == null) return false;
            Node newNode = new Node(data);
            newNode.next = temp.next;
            temp.next = newNode;
            return true;
        }
        boolean deleteAt(int index) {
            if (index < 0 || head == null) return false;
            if (index == 0) {
                head = head.next;
                return true;
            }
            Node temp = head;
            for (int i = 0; i < index - 1; i++) {
                if (temp == null) return false;
                temp = temp.next;
            }
            if (temp == null || temp.next == null) return false;
            temp.next = temp.next.next;
            return true;
        }
        boolean updateAt(int index, int newData) {
            Node temp = head;
            for (int i = 0; i < index; i++) {
                if (temp == null) return false;
                temp = temp.next;
            }
            if (temp == null) return false;
            temp.data = newData;
            return true;
        }
        Integer getAt(int index) {
            Node temp = head;
            for (int i = 0; i < index; i++) {
                if (temp == null) return null;
                temp = temp.next;
            }
            return (temp == null) ? null : temp.data;
        }
        int size() {
            int count = 0;
            Node temp = head;
            while (temp != null) {
                count++;
                temp = temp.next;
            }
            return count;
        }
        String shape() {
            StringBuilder sb = new StringBuilder();
            Node temp = head;
            while (temp != null) {
                sb.append(temp.data).append(" -> ");
                temp = temp.next;
            }
            sb.append("null");
            return sb.toString();
        }
    }
    static void singlyLinkedListOperations(SinglyLinkedList sll) {
        while (true) {
            System.out.println("\nSingly Linked List Operations:");
            System.out.println("1. Insert at position");
            System.out.println("2. Delete at position");
            System.out.println("3. Update at position");
            System.out.println("4. Get value at position");
            System.out.println("5. Print list");
            System.out.println("6. Exit operations");
            System.out.print("Choose option: ");
            String option = scanner.nextLine().trim();
            try {
                switch (option) {
                    case "1":
                        System.out.print("Enter position (0-based): ");
                        int insertPos = Integer.parseInt(scanner.nextLine().trim());
                        System.out.print("Enter value to insert: ");
                        int insertVal = Integer.parseInt(scanner.nextLine().trim());
                        if (sll.insertAt(insertPos, insertVal))
                            System.out.println("Inserted " + insertVal + " at position " + insertPos);
                        else
                            System.out.println("Invalid position");
                        break;
                    case "2":
                        System.out.print("Enter position to delete (0-based): ");
                        int delPos = Integer.parseInt(scanner.nextLine().trim());
                        if (sll.deleteAt(delPos))
                            System.out.println("Deleted node at position " + delPos);
                        else
                            System.out.println("Invalid position");
                        break;
                    case "3":
                        System.out.print("Enter position to update (0-based): ");
                        int updPos = Integer.parseInt(scanner.nextLine().trim());
                        System.out.print("Enter new value: ");
                        int newVal = Integer.parseInt(scanner.nextLine().trim());
                        if (sll.updateAt(updPos, newVal))
                            System.out.println("Updated position " + updPos + " with value " + newVal);
                        else
                            System.out.println("Invalid position");
                        break;
                    case "4":
                        System.out.print("Enter position to get value (0-based): ");
                        int getPos = Integer.parseInt(scanner.nextLine().trim());
                        Integer val = sll.getAt(getPos);
                        if (val != null)
                            System.out.println("Value at position " + getPos + " is " + val);
                        else
                            System.out.println("Invalid position");
                        break;
                    case "5":
                        System.out.println("List: " + sll.shape());
                        break;
                    case "6":
                        System.out.println("Exiting singly linked list operations.");
                        return;
                    default:
                        System.out.println("Invalid option.");
                }
            } 
catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }
}
