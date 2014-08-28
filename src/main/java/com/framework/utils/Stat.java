package com.framework.utils;
import java.util.Vector;

@SuppressWarnings({ "unchecked", "serial" })
public class Stat extends Vector {
	
	public void push(Object x) {
		super.add(x); // ���β������
		if(super.size()>10){
			super.removeElementAt(0);
		}
	}
	
	public Object pop() { // ����Ԫ�س���(�Ӷ���ɾ��)
		if(super.size()>0){
			Object x = super.elementAt(super.size()-1); // ����ָ�������������
			super.removeElementAt(super.size()-1); // ɾ��ָ�������������
			return x;
		}
		return null;
	}
	
	public void remove() {
		super.removeAllElements(); // removeAllElements()�Ƴ�ȫ��������������С����Ϊ��
	}
	
	public static void main(String[] args) throws java.io.IOException {
		Stat s = new Stat();
		s.push("1");
		s.push("2");
		s.push("3");
		s.push("4");
		s.push("5");
		s.push("6");
		s.push("7");
		System.out.println(s.pop());
		System.out.println(s.pop());
		System.out.println(s.pop());
		System.out.println(s.pop());
		System.out.println(s.pop());
	}
}