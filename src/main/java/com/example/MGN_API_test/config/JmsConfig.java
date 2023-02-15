package com.example.MGN_API_test.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.util.backoff.FixedBackOff;

import javax.jms.ConnectionFactory;

@Configuration
@EnableJms
public class JmsConfig {

//    @Bean
//    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory){
//        DefaultJmsListenerContainerFactory jmsListenerContainerFactory = new DefaultJmsListenerContainerFactory();
//
//        jmsListenerContainerFactory.setConnectionFactory(connectionFactory);
//        jmsListenerContainerFactory.setConcurrency("5-10");
//
//        return jmsListenerContainerFactory;
//    }

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;
    @Bean //消息的發送
    public JmsTemplate jmsTemplate() {
        return new JmsTemplate(activeMQConnectionFactory());
    }
    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        //建立ConnectionFactory工廠
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();

        activeMQConnectionFactory.setBrokerURL(brokerUrl);
        //若消息达到尝试次数消费失败或者超时等，会进入死信队列ActiveMQ.DLQ
//        RedeliveryPolicy queuePolicy = new RedeliveryPolicy();
//        queuePolicy.setInitialRedeliveryDelay(0); //初始重發延遲時間
//        queuePolicy.setRedeliveryDelay(1000);//重發延遲時間
//        queuePolicy.setUseExponentialBackOff(false); //是否在每次失敗重發時，增長等待時間
//        queuePolicy.setMaximumRedeliveries(5);// 最大重傳次数
//
//        activeMQConnectionFactory.setRedeliveryPolicy(queuePolicy);

        return activeMQConnectionFactory;
    }

    @Bean
    public JmsListenerContainerFactory<?> queueConnectionFactory(ConnectionFactory connectionFactory,
                                                                 DefaultJmsListenerContainerFactoryConfigurer configure) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configure.configure(factory, connectionFactory);
//        factory.setBackOff(new FixedBackOff(5000,5));//activeMQ配置監聽器容器重連服務器策略
        factory.setPubSubDomain(false);

        return factory;
    }
}
