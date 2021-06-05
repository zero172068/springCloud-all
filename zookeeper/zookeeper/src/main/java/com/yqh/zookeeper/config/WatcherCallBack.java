package com.yqh.zookeeper.config;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * @Auther：yqh
 * @Date：2021/5/27
 * @Description：创建
 * @Version：1.0
 */
public class WatcherCallBack implements Watcher, AsyncCallback.StatCallback, AsyncCallback.DataCallback {

    private ZooKeeper zooKeeper;
    private TestConfig testConfig;
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    public void setZooKeeper(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    public TestConfig getTestConfig() {
        return testConfig;
    }

    public void setTestConfig(TestConfig testConfig) {
        this.testConfig = testConfig;
    }

    public void aWait() throws Exception {
        //异步执行的，需要线程等待其他回调线程执行完，
        zooKeeper.exists("/AppConfig", this, this, "1212");
        countDownLatch.await();
    }

    @Override
    public void processResult(int i, String s, Object o, byte[] bytes, Stat stat) {
        // 有数据的回调，如get操作
        if(bytes!=null){
            testConfig.setConf(new String(bytes));
            countDownLatch.countDown();
        }
    }

    @Override
    public void processResult(int i, String s, Object o, Stat stat) {
    // 无数据的回调，如exists操作
        if(stat!=null){
            //节点存在，获取节点
            //回调自己的watcher和callback
            zooKeeper.getData("/AppConfig",this,this,"111");
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
    // 监听一次当前的回调,如果有节点变化，再get最新节点信息，然后又会有监听，这样就会不停的监听
        Event.EventType type = watchedEvent.getType();
        switch (type) {
            case None:
                break;
            case NodeCreated:
                // 如果节点刚开始不存在，那么就不会执行countDownLatch.countDown();那么zooKeeper.exists(方法会一直阻塞countDownLatch.await();，
                // 需要 zooKeeper.getData()后会回调，就会执行countDownLatch.countDown
                zooKeeper.getData("/AppConfig",this,this,"111");
                break;
            case NodeDeleted:
                //节点被删除，返回空,数据容忍，或者返回错误
                testConfig.setConf("");
                countDownLatch=new CountDownLatch(1);//阻塞放行,让下一次的zooKeeper.exists可以执行并阻塞
                break;
            case NodeDataChanged:
                zooKeeper.getData("/AppConfig",this,this,"111");
                break;
            case NodeChildrenChanged:
                break;
            case DataWatchRemoved:
                break;
            case ChildWatchRemoved:
                break;
            case PersistentWatchRemoved:
                break;
        }
    }
}
