import java.util.ArrayList;
import java.util.List;

public class MinHeap {
    private final int capacity;
    private int size;
    public ReservationNode[] heap;

    public MinHeap(int capacity) {
        this.capacity = capacity;
        this.heap = new ReservationNode[capacity];
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public int getLeftChildIdx(int parentIdx) {
        return 2 * parentIdx + 1;
    }

    public int getRightChildIdx(int parentIdx) {
        return 2 * parentIdx + 2;
    }

    public int getParentIdx(int childIdx) {
        return (childIdx - 1) / 2;
    }

    public ReservationNode leftChild(int parentIdx) {
        return heap[getLeftChildIdx(parentIdx)];
    }

    public ReservationNode rightChild(int parentIdx) {
        return heap[getRightChildIdx(parentIdx)];
    }

    public ReservationNode parent(int childIdx) {
        return heap[getParentIdx(childIdx)];
    }

    public ReservationNode peek() {
        if (size == 0) {
            System.out.println("MinHeap empty, invalid peek()");
            return null;
        }
        return heap[0];
    }

    public ReservationNode poll() {
        if (size == 0) {
            System.out.println("MinHeap empty, invalid poll()");
            return null;
        }
        ReservationNode minNode = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown();
        return minNode;
    }

    public void insertNode(ReservationNode reservation) {
        if (size == capacity) {
            System.out.println("The Reservation Heap is full and cannot accept any more reservations");
        } else {
            heap[size] = reservation;
            size++;
            heapifyUp();
        }
    }

    public void heapifyUp() {
        int idx = size - 1;
        while (getParentIdx(idx) >= 0 && heap[idx].compareTo(parent(idx)) < 0) {
            swap(getParentIdx(idx), idx);
            idx = getParentIdx(idx);
        }
    }

    public void heapifyDown() {
        int idx = 0;
        while (getLeftChildIdx(idx) < size) {
            int smallestChild = getLeftChildIdx(idx);

            if (getRightChildIdx(idx) < size && rightChild(idx).compareTo(leftChild(idx)) < 0) {
                smallestChild = getRightChildIdx(idx);
            }

            if (heap[idx].compareTo(heap[smallestChild]) < 0) {
                break;
            } else {
                swap(idx, smallestChild);
            }
            idx = smallestChild;
        }
    }

    public void swap(int x, int y) {
        ReservationNode temp = heap[x];
        heap[x] = heap[y];
        heap[y] = temp;
    }

    public void printHeap() {
        if (size == 0) {
            System.out.println("MinHeap is empty, nothing to print");
            return;
        }
        System.out.print("{ ");
        for (int i = 0; i < size; i++) {
            System.out.print(heap[i] + " ");
        }
        System.out.println(" }");
    }

    @Override
    public String toString() {
        String res = "";
        List<ReservationNode> tempList = new ArrayList<>();
        while (this.size > 1) {
            ReservationNode heapNode = poll();
            tempList.add(heapNode);
            res += heapNode.getPatronId() + ",";
        }
        if (!isEmpty()) {
            ReservationNode heapNode = poll();
            tempList.add(heapNode);
            res += heapNode.getPatronId();
        }
        for (ReservationNode heapNode : tempList)
            insertNode(heapNode);
        return res;
    }

}
