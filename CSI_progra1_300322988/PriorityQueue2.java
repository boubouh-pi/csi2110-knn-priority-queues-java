// Ablouh Mohamed Amine (300322988)
import java.util.ArrayList;

public class PriorityQueue2<E extends LabelledPoint> implements PriorityQueueIF<E> {
    private ArrayList<E> elements;

    public PriorityQueue2(int capacity) {
        elements = new ArrayList<>(capacity);
    }

    public PriorityQueue2(ArrayList<E> list, int capacity) {
        this(capacity);
        for (E item : list) {
            offer(item);
        }
    }

    @Override
    public boolean offer(E e) {
        elements.add(e);
        upHeap();
        return true;
    }

    @Override
    public E poll() {
        if (isEmpty()) {
            return null;
        }
        E max = elements.get(0);
        E lastElement = elements.remove(elements.size() - 1);
        if (!isEmpty()) {
            elements.set(0, lastElement);
            downHeap(0);
        }
        return max;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return elements.get(0);
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    private void upHeap() {
        int index = elements.size() - 1;
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            E current = elements.get(index);
            E parent = elements.get(parentIndex);
            if (current.getKey() <= parent.getKey()) {
                break;
            }
            elements.set(index, parent);
            elements.set(parentIndex, current);
            index = parentIndex;
        }
    }

    private void downHeap(int index) {
        int leftChild = 2 * index ;
        int rightChild = 2 * index + 1;
        int largest = index;

        if (leftChild < elements.size() && elements.get(leftChild).getKey() > elements.get(largest).getKey()) {
            largest = leftChild;
        }

        if (rightChild < elements.size() && elements.get(rightChild).getKey() > elements.get(largest).getKey()) {
            largest = rightChild;
        }

        if (largest != index) {
            E temp = elements.get(index);
            elements.set(index, elements.get(largest));
            elements.set(largest, temp);
            downHeap(largest);
        }
    }

    
}
