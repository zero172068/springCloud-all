package com.yqh.zookeeper.lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import sun.plugin2.jvm.RemoteJVMLauncher;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Auther：yqh
 * @Date：2021/5/28
 * @Description：创建
 * @Version：1.0
 */
public class WatcherCallBackLock implements Watcher, AsyncCallback.StringCallback,AsyncCallback.Children2Callback,AsyncCallback.StatCallback {

    private ZooKeeper zooKeeper;
    private String threadName;
    private String pathName;
    CountDownLatch countDownLatch = new CountDownLatch(1);

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    public void setZooKeeper(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public void tryLock() {
        try {
            zooKeeper.create("/lock", threadName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, this, "1232");

            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void unLock() {
        try {
            zooKeeper.delete(pathName, -1);
            System.out.println(threadName + " is done");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        // 如果第一个节点锁释放了。只有下一个节点收到此事件
        // 如果不是第一个节点，是某一个节点挂了，也可以造成他后边的节点收到通知，从而去监听挂掉的节点的上一个节点
        // 前面节点被删除了即锁释放了重新获取并排序判断。
        switch (watchedEvent.getType()) {
            case None:
                break;
            case NodeCreated:
                break;
            case NodeDeleted:
                // 监听到上一个节点被删除了即锁释放了，重新获取子节点排序获取锁
                zooKeeper.getChildren("/",false,this,"123");
                break;
            case NodeDataChanged:
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

    @Override
    public void processResult(int i, String s, Object o, String name) {

        // 新增节点，即创建锁，回调，得到节点名称pathName,当前节点及他前面创建好的节点他都能看到
        if(name!=null){
            zooKeeper.getChildren("/",false,this,"1232");
            pathName = name;
            System.out.println(threadName+"---create node: "+pathName);
        }


    }

    /**
     *
     * zooKeeper.getChildren 回调
     */
    @Override
    public void processResult(int i, String s, Object o, List<String> list, Stat stat) {
        //得到节点名称pathName,当前节点及他前面创建好的节点他都能看到
//        System.out.println(threadName +"---look node---");
//        for (String s1 : list) {
//            System.out.println(s1);
//        }

        Collections.sort(list);
        int i1 = list.indexOf(pathName.substring(1));
        // 是不是第一个
        if (i1==0){
            // 获得锁
            System.out.println(threadName+" is first and get lock");
            // 向根目录设置数据,用于判断重复获取锁，在创建锁的之前判断是否已经创建
            try {
                zooKeeper.setData("/",pathName.getBytes(),-1);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        }else {
            // 判断上一个节点是否存在，监听上一个节点
            try {
                zooKeeper.exists("/"+list.get(i1-1),this,this,"asa");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void processResult(int i, String s, Object o, Stat stat) {
        // 有可能前边的节点服务掉线了，获取不到，做别的处理
    }
}
