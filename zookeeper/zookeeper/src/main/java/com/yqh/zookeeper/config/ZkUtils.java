package com.yqh.zookeeper.config;

import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * @Auther：yqh
 * @Date：2021/5/27
 * @Description：创建
 * @Version：1.0
 */
public class ZkUtils {

    private static ZooKeeper zooKeeper;

//    private static String ZK_Path = "192.168.66.102:2181,192.168.66.103:2181,192.168.66.104:2181/testConfig";
    private static String ZK_Path = "192.168.66.102:2181,192.168.66.103:2181,192.168.66.104:2181/testLock";

    private static DefaulWatcher defaulWatcher = new DefaulWatcher();
    /**
     * 异步创建ZK，等待watcher监听到ZK创建完成了再释放线程，然后返回ZK实例
     */
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static ZooKeeper getZK() throws Exception {
        defaulWatcher.setCountDownLatch(countDownLatch);
        zooKeeper = new ZooKeeper(ZK_Path, 6000, defaulWatcher);
        countDownLatch.await();
        return zooKeeper;

    }
}
