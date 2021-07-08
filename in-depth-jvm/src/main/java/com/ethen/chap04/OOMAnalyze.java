package com.ethen.chap04;

import com.ethen.common.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * description: OutOfMemoryError��������
 * <p>
 * JVM����:
 * -Xms30m
 * -xmx30m
 * -XX:+HeapDumpOnOutOfMemoryError ���ڴ�һ��ʱ��ӡ�߳�dump��Ϣ
 * -XX:+PrintGCDetails
 * {@linkplain} MAT��������
 * <p>
 * �ڴ����:�����������ڴ�ʱ��û���㹻���ڴ�ռ�
 * �ڴ�й©ԭ�������
 * 1.���������ڶ�����ж��������ڶ�������
 * 2.����δ�ͷ�
 * 3.���������򲻺���
 * 4.�ڲ�������ⲿ��
 * 5.hashֵ�ı�
 *
 * @author ethenyang@126.com
 * @since 2021/07/04
 */
public class OOMAnalyze {
    public static void main(String[] args) {
        List<byte[]> bigObjects = new ArrayList<>();
        while (true) {
            byte[] bigObj = new byte[Constants._1M];
            bigObjects.add(bigObj);
        }
    }
}
