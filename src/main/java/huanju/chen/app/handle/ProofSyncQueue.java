package huanju.chen.app.handle;

import huanju.chen.app.domain.dto.Proof;

/**
 * @author HuanJu
 */
public class ProofSyncQueue {


    private static ProofSyncQueue instance;

    /**
     * 队列头
     */
    private Node front;

    /**
     * 队列尾
     */
    private Node rear;


    public static ProofSyncQueue getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (ProofSyncQueue.class) {
            if (instance != null) {
                return instance;
            }
            instance = new ProofSyncQueue();
            return instance;
        }
    }

    private ProofSyncQueue() {
    }

    public synchronized void add(Proof proof) {
        Node newNode = new Node(null, proof, null);
        if (this.front == null) {
            this.front = newNode;
            this.rear = newNode;
        } else {
            this.rear.next = newNode;
            newNode.pre = this.rear;
            this.rear = newNode;
        }
    }

    public boolean isEmpty() {
        return this.front == null;
    }

    public Proof get() {
        Proof proof;
        if (this.front == null) {
            return null;
        }
        return this.front.proof;
    }

    public Proof remove() {
        if (this.front == null) {
            return null;
        }
        Proof proof;
        synchronized (this) {
            if (this.front == null) {
                return null;
            } else {
                proof = this.front.proof;
                if (this.front == this.rear) {
                    this.front = null;
                    this.rear = null;
                } else {
                    this.front.pre = null;
                    this.front = this.front.next;
                }
            }
        }
        return proof;
    }


    /**
     * 队列节点
     */
    private static class Node {

        Proof proof;

        Node next;

        Node pre;

        public Node(Node pre, Proof proof, Node next) {
            this.proof = proof;
            this.next = next;
            this.pre = pre;
        }
    }


}
