package com.blog;

import org.flywaydb.test.FlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

// 使用了 http://mybatis.org/spring-boot-starter/mybatis-spring-boot-test-autoconfigure 便于对 MyBatis 的 Mapper 进行与数据库间的集成测试
@MybatisTest
//由于在 application.properties 中配置了数据库信息，所以关闭自动配置测试数据库的功能
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
/* 使用 flyway-test-extensions 的配置要求，由于覆盖了默认的 @TestExecutionListeners 配置，
   所以需要显式加入 DependencyInjectionTestExecutionListener.class，
   为了MybatisTest的事务回滚机制生效，需要显式加入 TransactionalTestExecutionListener.class，
   默认配置请看 @TestExecutionListeners 的实现*/
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class
})
public abstract class MapperTest {

    // src/test/proto/dogpay.proto
    service Payment {
        rpc Pay (DogpayRequest) returns (DogReply) {}
    }

    message DogpayRequest {
        string returnUrl = 1;
        string currencyCode = 2;
        string cancelUrl = 3;
        string actionType = 4;
        string receiverEmail = 5;
        string amount = 6;
    }

    message DogReply {
        string timestamp = 1;
        string ack = 2;
        string correlationId = 3;
        string payKey = 4;
        string paymentExecStatus = 5;
    }
    //抽象的测试基类用于减少每一个测试类编写时的重复代码，每一个测试类必须继承该基类
}
