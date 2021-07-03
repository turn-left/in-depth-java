package com.ethen.chap02;

/**
 * description: �����ֱ�ӽ��������
 * <p>
 * -Xms20m ���ڴ��ʼ��С20MB
 * -Xmx20m ���ڴ����ֵΪ20MB
 * -Xmn10m �������ڴ�����10MB
 * -Xss5m ����Java�̶߳�ջ��СΪ5MB
 * <p>
 * -XX:+PrintGCDetails
 * -XX:PretenureSizeThreshold=2m
 * -XX:+UseSerialGC
 *
 * @author ethenyang@126.com
 * @since 2021/07/03
 */
public class BigAllocation {
    private static final int _1MB = 1024 * 1024; //1M�Ĵ�С

    // * �����ֱ�ӽ��������(Eden  2m  +1 )
    public static void main(String[] args) {
        byte[] b1, b2, b3;
        b1 = new byte[_1MB]; //���������eden��
        b2 = new byte[_1MB]; //���������eden��
        b3 = new byte[5 * _1MB];//�������ֱ�ӽ��������
        b3 = new byte[5 * _1MB];//�������ֱ�ӽ��������
        b3 = new byte[5 * _1MB];//�������ֱ�ӽ��������
        b3 = new byte[5 * _1MB];//�������ֱ�ӽ��������
        b3 = new byte[5 * _1MB];//�������ֱ�ӽ��������
        b3 = new byte[5 * _1MB];//�������ֱ�ӽ��������
        b3 = new byte[5 * _1MB];//�������ֱ�ӽ��������
        b3 = new byte[5 * _1MB];//�������ֱ�ӽ��������
        b3 = new byte[5 * _1MB];//�������ֱ�ӽ��������
    }
}
