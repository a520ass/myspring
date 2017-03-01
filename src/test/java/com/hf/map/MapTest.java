package com.hf.map;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.*;
import org.junit.Test;

public class MapTest {
	
	public static void main(String[] args) {
		new MapTest().mapTest1();
	}
	
	@Test
	public void mapTest1(){
		Map<String, String> hashMap = new HashMap<>();
		hashMap.put("1", "何锋");
		String hf="何锋";
		System.out.println(hf==hashMap.get("1"));//true
	}

	@Test
	public void listmaptestgroup(){
		List<Map> listMap=Lists.newArrayList();
		for (int i=0;i<3;i++){
			Map map= Maps.newHashMap();
			map.put("type",i);
			map.put("name",i+"何锋");
			listMap.add(map);
		}
		ImmutableSet digits= ImmutableSet.of(listMap.toArray(new HashMap[0]));
		Function<Map,String> type=new Function<Map,String>(){
			public String apply(Map map){
				return ((Map)map).get("type").toString();
			}
		};
		ImmutableListMultimap typeList= Multimaps.index(digits,type);
		System.out.print(typeList);
	}
}
