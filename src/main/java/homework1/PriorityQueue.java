package homework1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class PriorityQueue<T> {
     private final ArrayList<Node<T>> heap;
     private final HashMap<T, Integer> position;
     public int size;
     private final int capacity;

     private static class Node<T> {
          T item;
          int priority;

          Node(T item, int priority) {
               this.item = item;
               this.priority = priority;
          }
     }

     public PriorityQueue(int capacity, Comparator<? super T> comparator) {
          this.heap = new ArrayList<>(capacity + 1);
          this.heap.add(null); // Add a dummy element at index 0
          this.position = new HashMap<>();
          this.size = 0;
          this.capacity = capacity;
     }

     private void swap(int i, int j) {
          Node<T> temp = heap.get(i);
          heap.set(i, heap.get(j));
          heap.set(j, temp);
          position.put(heap.get(i).item, i);
          position.put(heap.get(j).item, j);
     }

     private int parent(int i) {
          return i / 2;
     }

     private int leftChild(int i) {
          return 2 * i;
     }

     private int rightChild(int i) {
          return 2 * i + 1;
     }

     private void heapifyUp(int index) {
          while (index > 1 && heap.get(parent(index)).priority > heap.get(index).priority) {
               swap(index, parent(index));
               index = parent(index);
          }
     }

     private void heapifyDown(int index) {
          int minIndex = index;
          int left = leftChild(index);
          int right = rightChild(index);

          if (left <= size && heap.get(left).priority < heap.get(minIndex).priority) {
               minIndex = left;
          }
          if (right <= size && heap.get(right).priority < heap.get(minIndex).priority) {
               minIndex = right;
          }

          if (minIndex != index) {
               swap(index, minIndex);
               heapifyDown(minIndex);
          }
     }

     public void insert(T item, int priority) {
          if (size == capacity) {
               throw new IllegalStateException("Heap is full");
          }
          size++;
          heap.add(new Node<>(item, priority));
          position.put(item, size);
          heapifyUp(size);
     }

     public T extractMin() {
          if (size == 0) {
               throw new IllegalStateException("Heap is empty");
          }
          T min = heap.get(1).item;
          delete(1);
          return min;
     }

     private void delete(int index) {
          if (index < 1 || index > size) {
               throw new IllegalArgumentException("Invalid index");
          }
          T item = heap.get(index).item;
          position.remove(item);
          heap.set(index, heap.get(size));
          position.put(heap.get(size).item, index);
          heap.remove(size);
          size--;
          if (index <= size) {
               heapifyDown(index);
          }
     }

     public void changePriority(T item, int newPriority) {
          Integer index = position.get(item);
          if (index == null) {
               throw new IllegalArgumentException("Item not found in heap");
          }
          int oldPriority = heap.get(index).priority;
          heap.get(index).priority = newPriority;
          if (newPriority < oldPriority) {
               heapifyUp(index);
          } else if (newPriority > oldPriority) {
               heapifyDown(index);
          }
     }

     public boolean contains(T item) {
          return position.containsKey(item);
     }
}
