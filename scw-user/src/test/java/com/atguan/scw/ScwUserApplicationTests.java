package com.atguan.scw;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Ignore
@SpringBootTest
class ScwUserApplicationTests {

    @Autowired
    DataSource dataSource;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    void contextLoads() throws SQLException {
        Connection connection = dataSource.getConnection();

        System.out.println(connection);

        connection.close();

    }

    @Test
    public void test02() {
        stringRedisTemplate.opsForValue().set("key11","value11");
    }

    @Test
    public void test03() {
        String key = stringRedisTemplate.opsForValue().get("key11");
        System.out.println(key);
    }

}
