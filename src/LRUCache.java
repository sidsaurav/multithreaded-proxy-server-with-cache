import java.util.*;

class LRUCache {
    private class Node {
        String key;
        String value;
        long timestamp;
        Node prev;
        Node next;

        Node(String key, String value) {
            this.key = key;
            this.value = value;
            this.timestamp = System.currentTimeMillis();
        }
    }

    private int capacity;
    private Map<String, Node> cache;
    private Node head;
    private Node tail;
    private static final long CACHE_EXPIRATION_MS = 600000; // 10 minutes

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.head = new Node("", "");
        this.tail = new Node("", "");
        head.next = tail;
        tail.prev = head;
    }

    public synchronized String get(String key) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            if (System.currentTimeMillis() - node.timestamp > CACHE_EXPIRATION_MS) {
                remove(node);
                return null;
            }
            remove(node);
            add(node);
            return node.value;
        }
        return null;
    }

    public synchronized void put(String key, String value) {
        if (cache.containsKey(key)) {
            remove(cache.get(key));
        }
        if (cache.size() >= capacity) {
            remove(tail.prev);
        }
        add(new Node(key, value));
    }

    private void add(Node node) {
        Node headNext = head.next;
        head.next = node;
        node.prev = head;
        node.next = headNext;
        headNext.prev = node;
        cache.put(node.key, node);
    }

    private void remove(Node node) {
        cache.remove(node.key);
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
}