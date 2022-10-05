package com.poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Rank extends Poker {

	public static Map<String, Integer> cardMap = new LinkedHashMap<>();
	public static Map<Integer, Object> rankNumMap = new LinkedHashMap<>();
	public static Map<String, Object> rankMap = new LinkedHashMap<>();

	public static List<Object> cardMapList = new ArrayList<>();

	public static List<String> cardSuitList = new ArrayList<>();
	public static List<Integer> cardNumberList = new ArrayList<>();

	public static int rankPlayerNum = 0;
	public static int allCardSize = 0;
	public static int highRankNum = 0;

	public void setRank() {

		rankNumMap = new LinkedHashMap<>();

		rankNumMap.put(10, "ROYAL STRAIGHT FLUSH");
		rankNumMap.put(9, "STRAIGHT FLUSH");
		rankNumMap.put(8, "FOUR CARD");
		rankNumMap.put(7, "FULL HOUSE");
		rankNumMap.put(6, "FLUSH");
		rankNumMap.put(5, "STRAIGHT");
		rankNumMap.put(4, "THREE OF A KIND");
		rankNumMap.put(3, "TWO PAIR");
		rankNumMap.put(2, "ONE PAIR");
		rankNumMap.put(1, "NO PAIR");

	}

	@SuppressWarnings("unchecked")
	public void mainRank() {

		for (int i = 0; i < playerList.size(); i++) {
			allCardSize = ((List<String>) ((Map<String, Object>) playerList.get(i)).get("allCard")).size();
			cardNumberList = new ArrayList<>();
			cardSuitList = new ArrayList<>();
			rankPlayerNum = i;
			for (int j = 0; j < allCardSize; j++) {
				cardSuitList.add(
						((List<String>) ((Map<String, Object>) playerList.get(i)).get("allCard")).get(j)
								.substring(0, 1));
				if (((List<String>) ((Map<String, Object>) playerList.get(i)).get("allCard")).get(j).length() == 2) {
					if (((List<String>) ((Map<String, Object>) playerList.get(i)).get("allCard")).get(j)
							.contains("A")) {
						cardNumberList.add(1);
					} else if (((List<String>) ((Map<String, Object>) playerList.get(i)).get("allCard")).get(j)
							.contains("J")) {
						cardNumberList.add(11);
					} else if (((List<String>) ((Map<String, Object>) playerList.get(i)).get("allCard")).get(j)
							.contains("Q")) {
						cardNumberList.add(12);
					} else if (((List<String>) ((Map<String, Object>) playerList.get(i)).get("allCard")).get(j)
							.contains("K")) {
						cardNumberList.add(13);
					} else {
						cardNumberList.add(Integer.valueOf(
								((List<String>) ((Map<String, Object>) playerList.get(i)).get("allCard")).get(j)
										.substring(1, 2)));
					}
				} else {
					cardNumberList.add(10);
				}
			}
			//
			//			System.out.println("PLAYER(" + rankPlayerNum + ") CARD NUMBER LIST：" + cardNumberList);
			checkRank();
		}

	}

	@SuppressWarnings("unchecked")
	public void checkRank() {

		rankMap = new LinkedHashMap<>();

		List<Object> highNumList = new ArrayList<>();
		List<Object> highRankList = new ArrayList<>();
		Map<Integer, Object> highRankMap = new ConcurrentHashMap<>();

		List<Object> onePairList = new ArrayList<>();
		List<Object> tripleList = new ArrayList<>();
		List<Object> flushList = new ArrayList<>();

		//		for (int i = 0; i < allCardSize; i++) {

		Map<Integer, Integer> cardNumCntMap = cardNumberList.stream().collect(
				Collectors.groupingBy(
						//MapのキーにはListの要素をそのままセットする
						Function.identity(),
						//Mapの値にはListの要素を1に置き換えて、それをカウントするようにする
						Collectors.summingInt(s -> 1)));
		System.out.println("PLAYER(" + rankPlayerNum + ") NUMBER COUNT MAP：" + cardNumCntMap);

		//		}

		Map<String, Integer> cardPatCntMap = cardSuitList.stream().collect(
				Collectors.groupingBy(
						//MapのキーにはListの要素をそのままセットする
						Function.identity(),
						//Mapの値にはListの要素を1に置き換えて、それをカウントするようにする
						Collectors.summingInt(s -> 1)));
		System.out.println("PLAYER(" + rankPlayerNum + ") SUIT COUNT MAP：" + cardPatCntMap);

		((Map<String, Object>) playerList.get(rankPlayerNum)).put("rank", rankNumMap.get(highRankNum));

		// STRAIGHT
		for (int i = 0; i < cardNumberList.size(); i++) {
			highNumList = new ArrayList<>();
			int firstNum = cardNumberList.get(i);
			int secondNum = firstNum + 1;
			int thirdNum = secondNum + 1;
			int fourthNum = thirdNum + 1;
			int fifthNum = fourthNum + 1;
			if (cardNumberList.contains(firstNum) && cardNumberList.contains(secondNum)
					&& cardNumberList.contains(thirdNum) && cardNumberList.contains(fourthNum)
					&& cardNumberList.contains(fifthNum)) {
				highRankList.add(5);
				highNumList.add(fifthNum);
				highRankMap.put(5, highNumList);
			}
		}
		if (cardNumberList.contains(10) && cardNumberList.contains(11)
				&& cardNumberList.contains(12) && cardNumberList.contains(13)
				&& cardNumberList.contains(1)) {
			highRankList.add(5);
			highNumList.add(1);
			highRankMap.put(5, highNumList);
		}

		// FLUSH
		int suitCnt = 0;
		for (int i = 0; i < allCardSize; i++) {
			highNumList = new ArrayList<>();
			flushList = new ArrayList<>();
			String flushNum = "";
			suitCnt = 0;
			String suit = ((List<String>) ((Map<String, Object>) playerList.get(rankPlayerNum)).get("allCard")).get(i)
					.substring(0, 1);
			for (int j = 0; j < allCardSize; j++) {
				if (((List<String>) ((Map<String, Object>) playerList.get(rankPlayerNum)).get("allCard")).get(j)
						.contains(suit)) {
					suitCnt += 1;
					flushNum = ((List<String>) ((Map<String, Object>) playerList.get(rankPlayerNum)).get("allCard"))
							.get(j)
							.substring(1);
					if (flushNum.equals("A")) {
						flushList.add(1);
					} else if (flushNum.equals("J")) {
						flushList.add(11);
					} else if (flushNum.equals("Q")) {
						flushList.add(12);
					} else if (flushNum.equals("K")) {
						flushList.add(13);
					} else {
						flushList.add(Integer.valueOf(flushNum));
					}
				}
			}
			if (suitCnt >= 5) {
				// STRAIGHT FLUSH
				for (int k = 0; k < flushList.size(); k++) {
					int firstNum = (int) flushList.get(k);
					int secondNum = firstNum + 1;
					int thirdNum = secondNum + 1;
					int fourthNum = thirdNum + 1;
					int fifthNum = fourthNum + 1;
					if (flushList.contains(firstNum) && flushList.contains(secondNum) && flushList.contains(thirdNum)
							&& flushList.contains(fourthNum) && flushList.contains(fifthNum)) {
						highRankList.add(9);
						highNumList.add(fifthNum);
						highRankMap.put(9, highNumList);
					}
				}
				// ROYAL STRAIGHT FLUSH
				if (flushList.contains(1) && flushList.contains(10) && flushList.contains(11)
						&& flushList.contains(12) && flushList.contains(13)) {
					highRankList.add(10);
					highNumList.add(suit + "1");
					highRankMap.put(10, highNumList);
				} else {
					Collections.sort(flushList, Collections.reverseOrder());
					highRankList.add(6);
					if (flushList.contains(1)) {
						highNumList.add(1);
					} else {
						highNumList.add(flushList.get(0));
					}
					highRankMap.put(6, highNumList);
				}
			}
		}

		for (Entry<Integer, Integer> entry : cardNumCntMap.entrySet()) {
			highNumList = new ArrayList<>();
			// FOUR CARD
			if (entry.getValue() == 4) {
				highRankList.add(8);
				highNumList.add(entry.getKey());
				highRankMap.put(8, highNumList);
				// THREE OF A KIND
			} else if (entry.getValue() == 3) {
				highRankList.add(4);
				tripleList.add(entry.getKey());
				highRankMap.put(4, tripleList);
				// ONE PAIR
			} else if (entry.getValue() == 2) {
				highRankList.add(2);
				onePairList.add(entry.getKey());
				highRankMap.put(2, onePairList);
				// NO PAIR
			} else {
				highRankList.add(1);
				highNumList.add(entry.getKey());
				highRankMap.put(1, highNumList);
			}
		}

		for (Entry<Integer, Object> entry : highRankMap.entrySet()) {
			highNumList = new ArrayList<>();
			if (entry.getKey() == 4) {
				// FULL HOUSE
				if (((List<Object>) entry.getValue()).size() >= 2) {
					highRankList.add(7);
					highNumList = (List<Object>) entry.getValue();
					highRankMap.put(7, highNumList);
				}
				// TWO PAIR
			} else if (entry.getKey() == 2) {
				if (((List<Object>) entry.getValue()).size() >= 2) {
					highRankList.add(3);
					highNumList = (List<Object>) entry.getValue();
					highRankMap.put(3, highNumList);
				}
			}

		}

		// FULL HOUSE
		if (highRankList.contains(4) && highRankList.contains(2)) {
			highNumList = new ArrayList<>();
			highRankList.add(7);
			highNumList.add(tripleList.get(tripleList.size() - 1));
			highNumList.add(onePairList.get(tripleList.size() - 1));
			highRankMap.put(7, highNumList);
		}

		Object[] mapKey = highRankMap.keySet().toArray();
		Arrays.sort(mapKey);
		System.out.println(
				"PLAYER(" + rankPlayerNum + ") HIGH RANK MAP：" + highRankMap);

		Collections.sort((List<Object>) highRankMap.get(highRankList.get(0)), Collections.reverseOrder());
		Collections.sort(highRankList, Collections.reverseOrder());
		rankMap.put((String) rankNumMap.get(highRankList.get(0)), highRankMap.get(highRankList.get(0)));
		((Map<String, Object>) playerList.get(rankPlayerNum)).put("rank", rankMap);

	}

}
