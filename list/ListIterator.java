package list;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListIterator implements Iterator<ListNode> {
    ListNode current;

    public ListIterator(List list) {
        current = list.front();
    }

    public boolean hasNext() {
        try {
            return current.isValidNode() && current.next().isValidNode();
        } catch (InvalidNodeException e) {
        }
        return false;
    }

    public ListNode next() {
        if (!current.isValidNode()) {
            return null;
        }
        ListNode temp = current;
        try {
            current = current.next();
        } catch (InvalidNodeException e) {
        }
        return temp;
    }

    public void remove() {
        if (!current.isValidNode()) {
            return;
        }
        try {
            current.remove();
        } catch (InvalidNodeException e) {
        }
    }
}