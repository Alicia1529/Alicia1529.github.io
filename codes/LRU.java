import java.util.*;

public class LRU {

    class Node {
        public int key, val;
        public Node prev, next;
        public Node (int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    class DoubleLinkedList {
        int size;
        Node head, tail;
        public DoubleLinkedList() {
            this.head = new Node(0,0);
            this.tail = new Node(0,0);
            this.head.next = this.tail;
            this.tail.prev = this.head;
            this.size = 0;
        }
        
        public int size() {
            return this.size;
        }
        public boolean isEmpty() {
            return this.size==0;
        }
        public void addFirst(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
            size++;
        }
        public Node removeLast() {
            if (tail.prev == head)
                return null;
            Node last = tail.prev;
            remove(last);
            return last;
        }
        public void remove(Node node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
            this.size--;
        }
    }

    class LRUCache {
        int cap;
        Map<Integer, Node> map = new HashMap<>();
        DoubleLinkedList cache = new DoubleLinkedList() ;

        public LRUCache(int capacity) {
            this.cap = capacity; 
        }
        
        public int get(int key) {
            if (!map.containsKey(key))
                return -1;
            int val = this.map.get(key).val;
            put(key, val);
            return val;
        }
        
        public void put(int key, int value) {
            Node x = new Node(key, value);
            // System.out.println("hit here2!!!");
            if (map.containsKey(key)) {
                // System.out.println("hit here!!!");
                cache.remove(map.get(key));
            } else if (cap == cache.size()) {
                Node last = cache.removeLast();
                map.remove(last.key);
            }
            cache.addFirst(x);
            map.put(key, x);
        }

        public void showCache() {
            Node curr = cache.head;
            while (curr != null) {
                System.out.print("(" + curr.key + "," + curr.val + ")");
                curr = curr.next;
            }
            System.out.println();
        }
    }

    public static void main(String args[]) {
        /* 缓存容量为 2 */
        LRUCache cache = new LRUCache(2);
        // 你可以把 cache 理解成一个队列
        // 假设左边是队头，右边是队尾
        // 最近使用的排在队头，久未使用的排在队尾
        // 圆括号表示键值对 (key, val)

        cache.put(1, 1);
        cache.showCache();
        // cache = [(1, 1)]
        cache.put(2, 2);
        cache.showCache();
        // cache = [(2, 2), (1, 1)]
        cache.get(1);       // 返回 1
        cache.showCache();
        // cache = [(1, 1), (2, 2)]
        // 解释：因为最近访问了键 1，所以提前至队头
        // 返回键 1 对应的值 1
        cache.put(3, 3);
        cache.showCache();
        // cache = [(3, 3), (1, 1)]
        // 解释：缓存容量已满，需要删除内容空出位置
        // 优先删除久未使用的数据，也就是队尾的数据
        // 然后把新的数据插入队头
        cache.get(2);       // 返回 -1 (未找到)
        cache.showCache();
        // cache = [(3, 3), (1, 1)]
        // 解释：cache 中不存在键为 2 的数据
        cache.put(1, 4);
        cache.showCache();
        // cache = [(1, 4), (3, 3)]
        // 解释：键 1 已存在，把原始值 1 覆盖为 4
        // 不要忘了也要将键值对提前到队头
    }

}

