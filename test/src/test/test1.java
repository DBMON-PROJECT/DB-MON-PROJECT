package test;
import java.util.*;

public class test1 {
	public static void main(String[] args) throws InterruptedException
	{
		BlockingQueue q = new BlockingQueue(10);
		ThreadPut put = new ThreadPut(q);
		ThreadTake take = new ThreadTake(q);
		q.setPutSpeed(100);
		q.setTakeSpeed(1000);
		put.start();
		take.start();	
		Thread.sleep(5000);
		q.setExit(true);
		
	}
}



class ThreadPut extends Thread{
	BlockingQueue q;
	ThreadPut(BlockingQueue q){
		this.q = q;
	}
	@Override
	public void run() {
		while(true){
			try {
				q.put(new Object());
				Thread.sleep(q.getPutSpeed());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(q.isExit()){
				break;
			}
			
		}
		System.out.println("End - ThreadPut");
	}
}
class ThreadTake extends Thread{
	BlockingQueue q;
	ThreadTake(BlockingQueue q){
		this.q = q;
	}
	@Override
	public void run() {
		while(true){
			try {
				q.take();
//				Thread.sleep((int)(Math.random()*3+1)*1000);
				Thread.sleep(q.getTakeSpeed());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(q.isExit()){
				break;
			}
		}
		System.out.println("End - ThreadTake");
	}
}

class BlockingQueue<T> {

    private Queue<T> queue = new LinkedList<T>();
    private int capacity;
    private int putSpeed;
    private int takeSpeed;
    private boolean isExit;    

    public synchronized boolean isExit() {
    	notifyAll();
		return isExit;
	}

	public void setExit(boolean isExit) {
		this.isExit = isExit;
	}

	public int getPutSpeed() {
		return putSpeed;
	}

	public void setPutSpeed(int putSpeed) {
		this.putSpeed = putSpeed;
	}

	public int getTakeSpeed() {
		return takeSpeed;
	}

	public void setTakeSpeed(int takeSpeed) {
		this.takeSpeed = takeSpeed;
	}

	public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void put(T element) throws InterruptedException {
        while(queue.size() == capacity) {
        	putSpeed = 1000;
        	takeSpeed = 100;
            wait();
        }

        queue.offer(element);
        System.out.println("put size : "+queue.size());
        notify(); // notifyAll() for multiple producer/consumer threads
    }

    public synchronized T take() throws InterruptedException {
        while(queue.isEmpty()) {
        	putSpeed = 100;
        	takeSpeed = 1000;
            wait();
        }

        T item = queue.poll();
        System.out.println("take size() : "+queue.size());
        notify(); // notifyAll() for multiple producer/consumer threads
        return item;
    }
}
