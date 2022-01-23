package com.ethen.mybatis.quikstart;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class HelloTest {

    public static void main(String[] args) throws IOException {
        String resource = "mybatis.xml";
        final InputStream inputStream = Resources.getResourceAsStream(resource);
        final SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        final SqlSession sqlSession = sessionFactory.openSession();
        final Map result = sqlSession.selectOne("select now()'");
        System.err.println(result);
    }
}
