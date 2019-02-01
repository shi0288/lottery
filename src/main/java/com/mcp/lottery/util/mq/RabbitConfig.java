//package com.mcp.lottery.util.mq;
//
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitConfig {
//
//    //任务direct交换器
//    @Bean
//    public DirectExchange taskExchange() {
//        return new DirectExchange("error_test_exchange", true, false);
//    }
//
//    //创建需执行的持久化任务队列
//    @Bean
//    public Queue taskExecuteQueue() {
//        return new Queue(MqCons.BROKER_TASK_DIRECT_EXCHANGE_FLAG.WAIT_QUEUE, true);
//    }
//
//    //绑定队列，交换器和路由
//    @Bean
//    public Binding pluginExecuteBinding() {
//        return BindingBuilder.bind(taskExecuteQueue()).to(taskExchange()).with(MqCons.BROKER_TASK_DIRECT_EXCHANGE_FLAG.ROUTE_KEY);
//    }
//
//
//}
