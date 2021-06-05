package com.yqh.zookeeper.config;

import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @Auther：yqh
 * @Date：2021/5/27
 * @Description：创建
 * @Version：1.0
 */
public class ZookeeperConfigTest {


    ZooKeeper zooKeeper;

    @Before
    public void conn() throws Exception {
        zooKeeper = ZkUtils.getZK();
    }

    @After
    public void closeConn() {
        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getConfig() throws Exception {

        WatcherCallBack watcherCallBack = new WatcherCallBack();
        watcherCallBack.setZooKeeper(zooKeeper);
        TestConfig testConfig = new TestConfig();
        watcherCallBack.setTestConfig(testConfig);
        watcherCallBack.aWait();

        while (true) {
            if (testConfig.getConf().equals("")){
                // 执行一遍后，删除节点，Conf为空，如果节点不存在，继续一边上述监听aWait
                System.out.println("node is delete----------");
                watcherCallBack.aWait();
            }else {
                System.out.println(testConfig.getConf());
            }
            Thread.sleep(1000);
        }
    }

}
