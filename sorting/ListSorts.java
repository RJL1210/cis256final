/* ListSorts.java */

package sorting;

import list.*;

public class ListSorts {

    private final static int SORTSIZE = 1000000;

    /**
     *  makeQueueOfQueues() makes a queue of queues, each containing one item
     *  of q.  Upon completion of this method, q is empty.
     *  @param q is a LinkedQueue of objects.
     *  @return a LinkedQueue containing LinkedQueue objects, each of which
     *    contains one object from q.
     **/
    public static LinkedQueue makeQueueOfQueues(LinkedQueue q) {
        // Replace the following line with your solution.
        int size = q.size();
        LinkedQueue newQueue = new LinkedQueue();
        while (q.size() > 0)
        {
            try {
                LinkedQueue individualQueue = new LinkedQueue();
                individualQueue.enqueue(q.dequeue());
                newQueue.enqueue(individualQueue);
            } catch (QueueEmptyException e) {
                System.err.println(e);
            }
        }
        return newQueue;
    }

    /**
     *  mergeSortedQueues() merges two sorted queues into a third.  On completion
     *  of this method, q1 and q2 are empty, and their items have been merged
     *  into the returned queue.
     *  @param q1 is LinkedQueue of Comparable objects, sorted from smallest
     *    to largest.
     *  @param q2 is LinkedQueue of Comparable objects, sorted from smallest
     *    to largest.
     *  @return a LinkedQueue containing all the Comparable objects from q1
     *   and q2 (and nothing else), sorted from smallest to largest.
     **/
    public static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2) {
        LinkedQueue mergedQueue = new LinkedQueue();
        int one = 0;
        int two = 0;

        try {
            while (q1.size() > 0 && q2.size() > 0)
            {
                Comparable first = (Comparable)q1.front();
                Comparable second = (Comparable)q2.front();
                int compare = first.compareTo(second);
                if (compare <= 0)
                {
                    mergedQueue.enqueue(q1.dequeue());
                    one++;
                }
                else if (compare > 0)
                {
                    mergedQueue.enqueue(q2.dequeue());
                    two++;
                }
            }

            while (q1.size() > 0)
                mergedQueue.enqueue(q1.dequeue());
            while (q2.size() > 0)
                mergedQueue.enqueue(q2.dequeue());
        } catch (QueueEmptyException e) {
            System.err.println(e);
        }

        return mergedQueue;
    }

    /**
     *  partition() partitions qIn using the pivot item.  On completion of
     *  this method, qIn is empty, and its items have been moved to qSmall,
     *  qEquals, and qLarge, according to their relationship to the pivot.
     *  @param qIn is a LinkedQueue of Comparable objects.
     *  @param pivot is a Comparable item used for partitioning.
     *  @param qSmall is a LinkedQueue, in which all items less than pivot
     *    will be enqueued.
     *  @param qEquals is a LinkedQueue, in which all items equal to the pivot
     *    will be enqueued.
     *  @param qLarge is a LinkedQueue, in which all items greater than pivot
     *    will be enqueued.
     **/
    public static void partition(LinkedQueue qIn, Comparable pivot,
                                 LinkedQueue qSmall, LinkedQueue qEquals,
                                 LinkedQueue qLarge) {
        while (qIn.size() > 0)
        {
            try {
                Comparable element = (Comparable)qIn.dequeue();
                int compare = element.compareTo(pivot);
                if (compare == 0)
                    qEquals.enqueue(element);
                else if (compare < 0)
                    qSmall.enqueue(element);
                else if (compare > 0)
                    qLarge.enqueue(element);
            } catch (QueueEmptyException e) {
                System.err.println(e);
            }
        }
    }

    /**
     *  mergeSort() sorts q from smallest to largest using mergesort.
     *  @param q is a LinkedQueue of Comparable objects.
     **/
    public static void mergeSort(LinkedQueue q) {
        LinkedQueue queueOfQueues = makeQueueOfQueues(q);
        while (queueOfQueues.size() > 1)
        {
            try {
                LinkedQueue first = (LinkedQueue)queueOfQueues.dequeue();
                LinkedQueue second = (LinkedQueue)queueOfQueues.dequeue();
                // System.out.println("first: " + first.toString() + " second: " + second.toString());
                LinkedQueue merged = (LinkedQueue)mergeSortedQueues(first, second);
                // System.out.println("merged: " + merged.toString());
                queueOfQueues.enqueue(merged);
            } catch (QueueEmptyException e) {
                System.err.println(e);
            }
        }
        if (queueOfQueues.size() > 0)
            q.append((LinkedQueue)queueOfQueues.nth(1));
    }

    /**
     *  quickSort() sorts q from smallest to largest using quicksort.
     *  @param q is a LinkedQueue of Comparable objects.
     **/
    public static void quickSort(LinkedQueue q) {
        if (q.size() == 0)
            return;
        int random = (int)(Math.random()*1.0*(q.size()-1));
        Comparable pivot = (Comparable)q.nth(random);
        LinkedQueue qSmall = new LinkedQueue();
        LinkedQueue qEquals = new LinkedQueue();
        LinkedQueue qLarge = new LinkedQueue();
        partition(q, pivot, qSmall, qEquals, qLarge);
        quickSort(qSmall);
        quickSort(qLarge);
        q.append(qSmall);
        q.append(qEquals);
        q.append(qLarge);
        // Your solution here.
    }

    /**
     *  makeRandom() builds a LinkedQueue of the indicated size containing
     *  Integer items.  The items are randomly chosen between 0 and size - 1.
     *  @param size is the size of the resulting LinkedQueue.
     **/
    public static LinkedQueue makeRandom(int size) {
        LinkedQueue q = new LinkedQueue();
        for (int i = 0; i < size; i++) {
            q.enqueue(new Integer((int) (size * Math.random())));
        }
        return q;
    }

    /**
     *  main() performs some tests on mergesort and quicksort.  Feel free to add
     *  more tests of your own to make sure your algorithms works on boundary
     *  cases.  Your test code will not be graded.
     **/
    public static void main(String [] args) {

        LinkedQueue q = makeRandom(10);
        System.out.println(q.toString());
        mergeSort(q);
        System.out.println(q.toString());

        q = makeRandom(0);
        System.out.println(q.toString());
        mergeSort(q);
        System.out.println(q.toString());

        q = makeRandom(1);
        System.out.println(q.toString());
        mergeSort(q);
        System.out.println(q.toString());

        q = makeRandom(10);
        System.out.println(q.toString());
        quickSort(q);
        System.out.println(q.toString());

        q = makeRandom(0);
        System.out.println(q.toString());
        quickSort(q);
        System.out.println(q.toString());

        q = makeRandom(1);
        System.out.println(q.toString());
        quickSort(q);
        System.out.println(q.toString());

        int[] sizes = new int[]{100,1000,10000,100000,1000000};

        for (int size : sizes)
        {
            Timer stopWatch = new Timer();
            q = makeRandom(size);
            stopWatch.start();
            mergeSort(q);
            stopWatch.stop();
            System.out.println("Mergesort time, " + size + " Integers:  " +
                    stopWatch.elapsed() + " msec.");

            stopWatch.reset();
            q = makeRandom(size);
            stopWatch.start();
            quickSort(q);
            stopWatch.stop();
            System.out.println("Quicksort time, " + size + " Integers:  " +
                    stopWatch.elapsed() + " msec.");
        }
    }

}