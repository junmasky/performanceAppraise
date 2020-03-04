package com.util.datastructure;

import java.util.LinkedList;

/**
 * 基于LinkedList实现栈
 * 只选择部分基于栈实现的接口
 */
public class StackList<E> {

    private LinkedList<E> ll = new LinkedList<E>();

    //入栈
    public void push(E e){
        ll.addFirst(e);
    }

    //查看栈顶元素但不移除
    public E peek(){
        return ll.getFirst();
    }

    //出栈
    public E pop(){
        return ll.removeFirst();
    }

    //判空
    public boolean isEmpty(){
        return ll.isEmpty();
    }

    //清空
    public void clear(){
         ll.clear();
    }

    //打印栈元素
    public String toString(){
        return ll.toString();
    }

}
