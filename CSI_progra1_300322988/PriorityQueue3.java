// Ablouh Mohamed Amine (300322988)
import java.util.PriorityQueue;

public class PriorityQueue3<E extends LabelledPoint> implements PriorityQueueIF<E> {
    private PriorityQueue<E> queue;
    private int capacity;

    public PriorityQueue3(int capacity) {
        this.capacity = capacity;
        queue = new PriorityQueue<>(capacity, (a, b) -> Double.compare(b.getKey(), a.getKey())); 
    }

    @Override
    public boolean offer(E e) {
        if (queue.size() < capacity) {
            return queue.offer(e);
        } else {
            if (!queue.isEmpty() && e.getKey() < queue.peek().getKey()) { 
                queue.poll(); 
                return queue.offer(e);
            }
        }
        return true;
    }

    @Override
    public E poll() {
        return queue.poll();
    }

    @Override
    public E peek() {
        return queue.peek();
    }

    @Override
    public int size() {
        return queue.size();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
