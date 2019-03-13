import java.util.ArrayList;
import java.util.Collections;

public class wordHeap 
{
	static class wordValue // 键值对
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
		heap = new ArrayList<wordValue>();
	}
	
	public void add(String word, Integer value)
	{
		wordValue wv = new wordValue(word, value);
		heap.add(wv);
	}
	
	public static int left(int index) // 左儿子
	{
		return 2*index;
	}
	public static int right(int index) // 右儿子
	{
		return 2*index+1;
	}
	
	public boolean compare(int a, int b) // 比较两个键值对的顺序，返回ture则a在前
	{
		if(heap.get(a).value > heap.get(b).value) // 比较值
			return true;
		else if(heap.get(a).value == heap.get(b).value)
		{
			int result = heap.get(a).word.compareTo(heap.get(b).word); //比较字典顺序
			if(result <= 0)
				return true;
		}
		
		return false;
	}
	
	public int isExist(String key) //主要用于在词堆中找相同词（查找权重）
	{
		for(int i = 0; i < heap.size(); i ++)
		{
			int result = heap.get(i).word.compareTo(key); //比较字典顺序
			if(result == 0)
				return i;
		}
		return -1;
	}
	
	public void adjustHeap(int i, int size) // 调整词堆
	{
		int index = i;
		int lchild = left(i);
		int rchild = right(i);
		if(index <= (size-1) / 2)
		{	
			// 确保不是叶子节点
			if(lchild <= size && !compare(index, lchild))
				index = lchild;
			if(rchild < size && !compare(index, rchild))
				index = rchild;
			// index此时为两个子节点中较大的值
			if(i != index)
			{
				Collections.swap(heap, i, index);
				//以index为根的子树往下继续调整
				adjustHeap(index, size);
			}
		}
	}
	
	public void insert(String word, Integer value) // 插入键值对
	{
		//在数组尾部添加,且注意下标为0的位置不放元素
		wordValue wv = new wordValue(word, value);
		if(heap.size()==0)
			add("", -1);
		heap.add(wv);
		heapUp(heap.size() - 1);
	}
		
	public void heapUp(int index) //上浮操作
	{
		if(index > 1)
		{
			// 求出其父亲节点
			int parent = index / 2;
			// 如果父亲节点的值小于index节点的值，交换两者的位置
			if(compare(index, parent))
			{
				Collections.swap(heap, parent, index);
				heapUp(parent);
			}
		}
	}
	
	public void delete(int index) // 删除键值对
	{
		heap.set(index, heap.get(heap.size() - 1)); //把最后的一个叶子的数值赋值给index位置
		heapDown(index);
		heap.remove(heap.size() - 1);   // 移除
	}

	public void heapDown(int index) // 下沉操作
	{
		// 因为第一个位置不存放数据，不考虑在内，最后一个也要删除，不考虑在内
		int n = heap.size()-2;
		//记录较大的儿子的位置
		int child = -1;
		if(2 * index>n)
		{   
			//2*index>n 说明该节点没有左右儿子节点了，则返回
			return;
		}
		else if
		(2 * index < n)
		{	
			//两个儿子都在
			child = 2*index;
			if(!compare(child, child + 1))
			{
				child++;
			}
		}
		else if(2 * index == n)
		{	
			//只有左儿子
			child = 2 * index;
		}
		//交换和递归
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
