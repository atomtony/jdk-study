package com.mistray.hashmap;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Mutex implements Lock {

    private static class Sync extends AbstractQueuedSynchronizer {

        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        @Override
        protected boolean tryAcquire(int arg) {
            // 如果期望值是0表示获取锁成功，否则获取失败
            if (compareAndSetState(0, 1)) {
                System.out.println("aaa1");
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            if (getState() == 0) {
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }
    }

    private final Sync sync = new Sync();

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    private static class MyTask implements Runnable {
        private final int index;
        public static Mutex mutex = new Mutex();

        private MyTask(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            mutex.lock();
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(index);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mutex.unlock();
            }
        }
    }

    private static class MyTask1 implements Runnable {
        private final int index;
        public static Mutex mutex = new Mutex();

        private MyTask1(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            while (true) {
                if (mutex.tryLock()) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println(index);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        mutex.unlock();
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(new MyTask(i)).start();
        }
        TimeUnit.SECONDS.sleep(10000);
    }
}
