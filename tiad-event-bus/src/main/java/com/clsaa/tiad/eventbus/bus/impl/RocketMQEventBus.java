/*
 *    Copyright 2019 Clsaa Group
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.clsaa.tiad.eventbus.bus.impl;

import com.clsaa.tiad.eventbus.bus.EventBus;
import com.clsaa.tiad.eventbus.bus.EventBusOptions;
import com.clsaa.tiad.eventbus.bus.EventOptions;
import com.clsaa.tiad.eventbus.coder.Coder;
import com.clsaa.tiad.eventbus.enums.EventFeatures;
import com.clsaa.tiad.transaction.listener.ListenerCache;
import com.clsaa.tiad.transaction.listener.TiadTransactionListener;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.impl.MessageImpl;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class RocketMQEventBus implements DistributeEventBus {
    static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    private String namesrvAddr;
    private Coder coder;
    private Map<String, DefaultMQProducer> cacheDefault = new ConcurrentHashMap<>();
    private Map<String, TransactionMQProducer> cacheTransaction = new ConcurrentHashMap<>();

    @SneakyThrows
    @Override
    public EventBus send(String topic, String group, Object event, EventOptions options) {
        DefaultMQProducer producer = null;
        TransactionMQProducer transactionMQProducer = null;
        if (cacheDefault.containsKey(group) || cacheTransaction.containsKey(group)) {
            producer = cacheDefault.get(group) == null ? cacheTransaction.get(group) : cacheDefault.get(group);
        } else if (!options.getEventFeatures().equals(EventFeatures.TRANSACTIONAL)) {
            producer = new DefaultMQProducer(group);
            producer.setNamesrvAddr(namesrvAddr);
            producer.start();
            cacheDefault.put(group, producer);
        } else {
            transactionMQProducer = new TransactionMQProducer("please_rename_unique_group_name");
        }
        String str = coder.encode(event);
        final org.apache.rocketmq.common.message.Message msg =
                new org.apache.rocketmq.common.message.Message(topic, "topic", str.getBytes(RemotingHelper.DEFAULT_CHARSET));
        SendResult sendResult = null;
        if (options.getEventFeatures().equals(EventFeatures.TRANSACTIONAL)) {
            final String tag = threadLocal.get();
            if (tag != null && tag.startsWith("transaction_tag")) {
                final String key = tag.split("-")[1];
                final Class<? extends TiadTransactionListener> aClass = ListenerCache.get(key);
                final TiadTransactionListener tiadTransactionListener = aClass.newInstance();
                transactionMQProducer.setTransactionListener(tiadTransactionListener);
                sendResult = producer.sendMessageInTransaction(msg, null);
            }
        } else {
            sendResult = producer.send(msg);
        }
        log.info(sendResult.toString());
        return this;
    }

    @Override
    public <T> EventBus send(String topic, String group, Object event, EventOptions options, Handler<AsyncResult<Message<T>>> replyHandler) {
        throw new UnsupportedOperationException("not support reply");
    }

    @SneakyThrows
    @Override
    public <T> EventBus consumer(String topic, String group, Handler<Message<T>> handler) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(group);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.subscribe(topic, "*");
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            for (MessageExt msg : msgs) {
                final String s = new String(msg.getBody());
                final Message message = new MessageImpl(null, null, null, coder.decode(s),
                        null, false, null, null);
                handler.handle(message);
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        return this;
    }


    @Override
    public EventBus init(EventBusOptions eventBusOptions) {
        this.namesrvAddr = eventBusOptions.getNamesrvAddr();
        this.coder = eventBusOptions.getCoder();
        return this;
    }
}
