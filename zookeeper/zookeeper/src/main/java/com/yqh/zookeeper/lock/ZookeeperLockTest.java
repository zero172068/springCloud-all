package com.yqh.zookeeper.lock;

import com.yqh.zookeeper.config.ZkUtils;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @Auther：yqh
 * @Date：2021/5/28
 * @Description：创建
 * @Version：1.0
 */
public class ZookeeperLockTest {

    private static ZooKeeper zooKeeper;

    @Before
    public void conn() {
        try {
            zooKeeper = ZkUtils.getZK();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void close() {
        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void zkLockTest() {

        for (int i = 0; i < 10; i++) {

            new Thread() {
                @Override
                public void run() {

                    WatcherCallBackLock watcherCallBackLock = new WatcherCallBackLock();
                    watcherCallBackLock.setThreadName(Thread.currentThread().getName());
                    watcherCallBackLock.setZooKeeper(zooKeeper);
                    // 抢锁
                    watcherCallBackLock.tryLock();

                    // 干活
                    System.out.println(Thread.currentThread().getName()+" working>>>>>>>>>");
                    try {
                        // 慢一点执行，避免太快导致第一个线程跑的太快，已经完成把锁释放了，
                        // 但是后面的线程还在获取子节点排序啥的，导致监听还没设置号，第一个节点就走完了
                        // 或者可以在获取锁的时候即第一个是自己的话，做别的事，如setdata,但这个的最终目的是为了判断重入锁
                        // 如果即没有hread.sleep(1000);也没有如setdata操作，执行结果只能看到第一个线程执行完的效果，看不到其他线程执行
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // 释放锁
                    watcherCallBackLock.unLock();

                }
            }.start();


        }
        while (true){}

    }

}
