package com.os.core;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.os.ui.MainFrame;

public class AProducer {
	private static int bufferSize = 20;
	private Semaphore mutex = new Semaphore(1);
	private Semaphore empty = new Semaphore(bufferSize);
	private Semaphore full = new Semaphore(0);
	int in = 0, out = 0;
	static Lock lock = new ReentrantLock();
	static Condition condition = lock.newCondition();

	public static void AProducer(String[] args) {
		AProducer AProducer = new AProducer();
		Buffer[] buffers = new Buffer[bufferSize];
		for (int i = 0; i < bufferSize; i++) {
			buffers[i] = AProducer.new Buffer();
			buffers[i].setNumber(i + 1);
		}
		Producer p = AProducer.new Producer(buffers);
		p.start();
		Customer c = AProducer.new Customer(buffers);
		c.start();
	}

	public void SWAIT(Semaphore... list) {
		lock.lock();
		while (true) {
			int count = 0;
			for (Semaphore semaphore : list) {
				if (semaphore.availablePermits() > 0) {
					count++;
				}
			}
			if (count == list.length) {
				break;
			}
			try {
				condition.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (Semaphore semaphore : list) {
			try {
				semaphore.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		lock.unlock();
	}

	public static void loooooog() {
		SuperSampler.DoLog();
	}

	public void SSIGNAL(Semaphore... list) {
		try {
			lock.tryLock(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (Semaphore semaphore : list) {
			semaphore.release();
		}
		condition.signal();
		lock.unlock();
	}

	class Buffer {
		private int number;

		public int getNumber() {
			return number;
		}

		public void setNumber(int number) {
			this.number = number;
		}

		public void in() {
			System.out.println("放入第" + number + "号缓冲区");
		}

		public void out() {
			System.out.println("从第" + number + "号缓冲区拿出");
		}
	}

	class Producer extends Thread {
		Buffer[] buffers;

		public Producer(Buffer[] buffers) {
			this.buffers = buffers;
		}

		@Override
		public void run() {
			while (true) {
				SWAIT(empty, mutex);
				buffers[in].in();
				in = (in + 1) % bufferSize;
				SSIGNAL(mutex, full);
			}
		}
	}

	class Customer extends Thread {
		Buffer[] buffers;

		public Customer(Buffer[] buffers) {
			this.buffers = buffers;
		}

		@Override
		public void run() {
			while (true) {
				SWAIT(full, mutex);
				buffers[out].out();
				out = (out + 1) % bufferSize;
				SSIGNAL(mutex, empty);
			}
		}
	}

}

class SuperSampler {
	public static void DoLog() {
		for (int i = 1; i <= 10; i++) {
			// Log writing to buffer i
			DoLittleLog("向" + i + "号缓冲区写入" + "\n");

			// Log taking from buffer i
			DoLittleLog("从" + i + "号缓冲区拿出" + "\n");
		}
	}

	static void DoLittleLog(String meassage) {
		var d = MainFrame.getInstance();
		d.logForProdu(meassage);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}