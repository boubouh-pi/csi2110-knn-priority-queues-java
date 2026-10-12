// Ablouh Mohamed Amine (300322988)
import java.util.ArrayList;
import java.util.Comparator;

public class PriorityQueue1<E extends LabelledPoint> implements PriorityQueueIF<E> {
    private ArrayList<E> elements;
    private int capacity;

    public PriorityQueue1(int capacity) {
        this.capacity = capacity;
        this.elements = new ArrayList<>(capacity);
    }

    @Override
    public boolean offer(E element) {
        if (element == null) {
            return false;
        }
    
        if (elements.size() < capacity) {
            elements.add(element);
            sortElements();
        } else {
            if (element.getKey() < elements.get(elements.size() - 1).getKey()) {
                elements.set(elements.size() - 1, element);
                sortElements();
            }
        }
        return true;
    }
    

    @Override
    public E poll() {
        if (elements.isEmpty()) {
            return null;
        }

        return elements.remove(elements.size() - 1); 
    }

    @Override
    public E peek() {
        if (elements.isEmpty()) {
            return null;
        }
        return elements.get(elements.size() - 1); 
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    private void sortElements() {
        int index = elements.size() - 1;
        while (index > 0 && elements.get(index).getKey() < elements.get(index - 1).getKey()) {
            
            elements.sort(Comparator.comparingDouble(LabelledPoint::getKey));
        }
    }
    
}
