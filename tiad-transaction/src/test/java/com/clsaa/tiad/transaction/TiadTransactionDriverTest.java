///*
// *    Copyright 2019 Clsaa Group
// *
// *    Licensed under the Apache License, Version 2.0 (the "License");
// *    you may not use this file except in compliance with the License.
// *    You may obtain a copy of the License at
// *
// *        http://www.apache.org/licenses/LICENSE-2.0
// *
// *    Unless required by applicable law or agreed to in writing, software
// *    distributed under the License is distributed on an "AS IS" BASIS,
// *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// *    See the License for the specific language governing permissions and
// *    limitations under the License.
// */
//
//package com.clsaa.tiad.transaction;
//
//
//import com.clsaa.tiad.eventbus.TiadEventDriver;
//import com.clsaa.tiad.eventbus.bus.EventOptions;
//import com.clsaa.tiad.transaction.annotation.Transaction;
//import com.clsaa.tiad.transaction.listener.TiadTransactionListener;
//import org.apache.rocketmq.client.producer.LocalTransactionState;
//import org.apache.rocketmq.common.message.Message;
//import org.apache.rocketmq.common.message.MessageExt;
//
//public class TiadTransactionDriverTest {
//    public static void main(String[] args) {
//        TiadTransactionDriver.tiad("tiad-app").init(TiadTransactionDriverTest.class.getPackage().getName());
//    }
//
//    //字节码插入：static ThreadLocal transaction_tag = new ThreadLocal();
//    @Transaction(listener = TestTransactionListener.class)
//    public void buy() {
//        //字节码插入：transaction_tag.set("transaction_tag-com.clsaa.tiad.transaction.TiadTransactionDriverTest#buy");
//        //模拟用户逻辑：消耗优惠券
//        TiadEventDriver.tiad("tiad-app")
//                .eventBus()
//                //发送事件时从transaction_tag取出值，判断若以transaction_tag开头则发送事务事件
//                .send("buy", "shop", "CouponsUsedEvent", new EventOptions());
//        //模拟用户逻辑：扣库存
//        //this.RepoDao.reduce(1);
//    }
//
//    static class TestTransactionListener implements TiadTransactionListener {
//        @Override
//        public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
//            //查询数据...确认当前事务状态...假设当前事务状态
//            return LocalTransactionState.UNKNOW;
//        }
//        @Override
//        public LocalTransactionState checkLocalTransaction(MessageExt msg) {
//            //查询数据...确认当前事务状态
//            return null;
//        }
//    }
//}
