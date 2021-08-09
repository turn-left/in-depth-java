package com.ethen.chap03.james01.main;


import com.ethen.chap03.james01.redpack.GenRedPack;
import com.ethen.chap03.james01.redpack.GetRedPack;

public class MainTestRedPack {
	

	public static void main(String[] args) throws InterruptedException {
		GenRedPack.genHongBao();//初始化红包
		
		GetRedPack.getHongBao();//从红包池抢红包
	
	}
}