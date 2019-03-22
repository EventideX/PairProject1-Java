package com.wordCount;

import java.util.ArrayList;
import java.util.Collections;

public class wordHeap 
{
	static class wordValue // ��ֵ��
	{
		public String word;
		public Integer value;
		
		wordValue(String word, Integer value)
		{
			this.word = word;
			this.value = value;
		}
	}
	
	ArrayList<wordValue> heap;
	
	wordHeap()
	{
		heap = new ArrayList<>();
	}
	
	public void add(String word, Integer value)
	{
		wordValue wv = new wordValue(word, value);
		heap.add(wv);
	}

	public boolean compare(int a, int b) // �Ƚ�������ֵ�Ե�˳�򣬷���ture��a��ǰ
	{
		if(heap.get(a).value > heap.get(b).value) // �Ƚ�ֵ
			return true;
		else if(heap.get(a).value.equals(heap.get(b).value))
		{
			int result = heap.get(a).word.compareTo(heap.get(b).word); //�Ƚ��ֵ�˳��
			return result <= 0;
		}
		
		return false;
	}
	
	int isExist(String key) //��Ҫ�����ڴʶ�������ͬ�ʣ�����Ȩ�أ�
	{
		for(int i = 0; i < heap.size(); i ++)
		{
			int result = heap.get(i).word.compareTo(key); //�Ƚ��ֵ�˳��
			if(result == 0)
				return i;
		}
		return -1;
	}

	public void insert(String word, Integer value) // �����ֵ��
	{
		//������β�����,��ע���±�Ϊ0��λ�ò���Ԫ��
		wordValue wv = new wordValue(word, value);
		if(heap.size()==0)
			add("", -1);
		heap.add(wv);
		heapUp(heap.size() - 1);
	}
		
	private void heapUp(int index) //�ϸ�����
	{
		if(index > 1)
		{
			// ����丸�׽ڵ�
			int parent = index / 2;
			// ������׽ڵ��ֵС��index�ڵ��ֵ���������ߵ�λ��
			if(compare(index, parent))
			{
				Collections.swap(heap, parent, index);
				heapUp(parent);
			}
		}
	}
	
	void delete() // ɾ����ֵ��
	{
		heap.set(1, heap.get(heap.size() - 1)); //������һ��Ҷ�ӵ���ֵ��ֵ��indexλ��
		heapDown(1);
		heap.remove(heap.size() - 1);   // �Ƴ�
	}

	private void heapDown(int index) // �³�����
	{
		// ��Ϊ��һ��λ�ò�������ݣ����������ڣ����һ��ҲҪɾ��������������
		int n = heap.size()-2;
		//��¼�ϴ�Ķ��ӵ�λ��
		int child = -1;
		if(2 * index>n)
		{   
			//2*index>n ˵���ýڵ�û�����Ҷ��ӽڵ��ˣ��򷵻�
			return;
		}
		else if
		(2 * index < n)
		{	
			//�������Ӷ���
			child = 2*index;
			if(!compare(child, child + 1))
			{
				child++;
			}
		}
		else if(2 * index == n)
		{	
			//ֻ�������
			child = 2 * index;
		}
		//�����͵ݹ�
		if(compare(child, index))
		{
			Collections.swap(heap, child, index);
			heapDown(child);
		}
	}
	
	public int size()
	{
		return heap.size();
	}

}