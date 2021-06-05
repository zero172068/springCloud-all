package com.yqh.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 *
 */
public class ZookeeperMain {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello World!");

        // zk是有session概念的，没有连接池的概念
        //session 3秒消失在session消失后
        // 在new zk的时候的watch是随session级别的，与node节点无关，就是session变化的watch
        // watch主从只发生在读方法，如get exits
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final ZooKeeper zooKeeper = new ZooKeeper("192.168.66.102:2181,192.168.66.103:2181,192.168.66.104:2181", 3000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                String path = watchedEvent.getPath();
                Event.KeeperState state = watchedEvent.getState();
                Event.EventType type = watchedEvent.getType();
                System.out.println("new zk watch"+watchedEvent.toString());

                switch (state) {
                    case Unknown:
                        break;
                    case Disconnected:
                        break;
                    case NoSyncConnected:
                        break;
                    case SyncConnected://同步链接成功
                        countDownLatch.countDown();
                        System.out.println("Connected");
                        break;
                    case AuthFailed:
                        break;
                    case ConnectedReadOnly:
                        break;
                    case SaslAuthenticated:
                        break;
                    case Expired:
                        break;
                    case Closed:
                        break;
                }

                switch (type) {
                    case None:
                        break;
                    case NodeCreated:
                        break;
                    case NodeDeleted:
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
        });
        countDownLatch.await();//等待直到减一后，即zk链接成功
        ZooKeeper.States state = zooKeeper.getState();
        switch (state) {
            case CONNECTING:
                System.out.println("ing>>>>>>>>>>>>");//new zk 是异步的，在执行时，会打印此状态，说明zk还在new当中
                break;
            case ASSOCIATING:
                break;
            case CONNECTED:
                System.out.println("ed>>>>>>>>>>>>");//使用线程异步，则可在zk new完成后打印此状态 countDown
                break;
            case CONNECTEDREADONLY:
                break;
            case CLOSED:
                break;
            case AUTH_FAILED:
                break;
            case NOT_CONNECTED:
                break;
        }
        //阻塞创建 acl:访问控制权限  createMode：类型 临时节点、持久、带序列号的等
        String s = zooKeeper.create("/ooxx12", "yqh".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println(s + ">>>>>>>>>>>>>>>>>>>");
        final Stat stat = new Stat();

        byte[] data = zooKeeper.getData("/ooxx12", new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {// 针对当前节点的watch,监听一次，true表示session的watch
                System.out.println("get data watch: " + watchedEvent.toString());
                try {
                    zooKeeper.getData("/ooxx12",this,stat);// 监听里继续注册监听
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, stat);
        System.out.println("old data "+new String(data));
        // 触发节点watch
        Stat ooxx12 = zooKeeper.setData("/ooxx12", "yqh2131".getBytes(), 0);
        // 还会触发watch吗不会，需要重复注册监听，在getdata的监听里继续注册监听
        Stat ooxx121 = zooKeeper.setData("/ooxx12", "yqh2131".getBytes(), 1);

        // 非阻塞异步获取 AsyncCallback.DataCallback:回调 ctx：上下文
        // 方法快速执行，返回结果后执行回调函数回去结果
        System.out.println("Async get start");
        zooKeeper.getData("/ooxx12", false, new AsyncCallback.DataCallback() {
            @Override
            public void processResult(int i, String s, Object ctx, byte[] bytes, Stat stat) {
                System.out.println("Async get>>>>>>>>>> "+ctx.toString());
                System.out.println("Async get>>>>>>>>>> "+new String(bytes));
            }
        },"12312");
        System.out.println("Async get end");
        Thread.sleep(30000);//等待异步线程跑完
    }
}
