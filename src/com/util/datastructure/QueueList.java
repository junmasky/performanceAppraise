package com.util.datastructure;


import java.util.LinkedList;
import java.util.Queue;

/**
 * 使用java.util.Queue接口，其底层关联到一个LinkedList（双端队列）实例
 * @param <E>
 */
public class QueueList<E> {

    private Queue<E> queue  = new LinkedList<E>();

    //将指定的元素插入此队列(如果立即可行且不会违反容量限制)，在成功是返回true
    //如果当前没有可用的空间，则抛出IllegalStateException
    public boolean add(E e){
        return queue.add(e);
    }

    //获取，但是不移除此队列的头
    public E element(){
        return queue.element();
    }

    //将指定的元素插入此队列(如果立即可行且不会违反容量限制)，当使用有容量限制的队列时，
    //此方法通常要优于add(E)，后者可能无法插入元素，而只是抛出一个异常
    public boolean offer(E e){
        return queue.offer(e);
    }

    //获取但不移除此队列的头，如果此队列为空，则返回null
    public E peek(){
        return queue.peek();
    }

    //获取并移除此队列的头，如果此队列为空，则返回null
    public E poll(){
        return queue.poll();
    }

    //获取并移除此队列的头
    public E remove(){
        return queue.remove();
    }

    //判空
    public  boolean isEmpty(){
        return queue.isEmpty();
    }
}
