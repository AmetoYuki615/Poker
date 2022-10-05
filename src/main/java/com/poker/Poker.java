package com.poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Poker {

	public static List<String> pokerCardList = new ArrayList<>();
	public static Map<String, String> suitMap = new LinkedHashMap<>();
	public static Scanner scanner = new Scanner(System.in);

	public static List<Object> playerList = new ArrayList<>();
	public static Map<String, Object> user = new LinkedHashMap<>();
	public static Map<String, Object> com1 = new LinkedHashMap<>();
	public static Map<String, Object> com2 = new LinkedHashMap<>();
	public static Map<String, Object> com3 = new LinkedHashMap<>();

	public static int gameLevel = 1;
	public static int minimChip = 1;

	public static int playerNum;
	public static int giveCardNum;

	public static boolean continueFlg = false;
	public static boolean openCardFlg = false;

	@SuppressWarnings("unchecked")
	public static void start() {

		Bet bet = new Bet();
		Rank rank = new Rank();

		System.out.println("♠♢ LET US BEGIN POKER ♡♣");

		System.out.print("・SELECT PLAYER NUMBER(2~4)…");
		selectPlayerNum();

		System.out.print("・SELECT GAME LEVEL, LOW(1) MIDDLE(2) HIGH(3)…");
		selectLevel();

		for (;;) {

			System.out.print("・SELECT MINIMUM BETTING CHIP, (1) TO (10)…");
			selectMinimChip();

			prepareCard();

			System.out.println("GIVING THE CARD FIRST…");
			openCardFlg = false;
			giveCardNum = 4;
			giveCard();

			System.out.println(
					"THE CARD YOU HAVE IS… " + (List<String>) ((Map<String, Object>) playerList.get(0)).get("allCard"));
			System.out.print("・SELECT TO THROW THE CARD AWAY(1~4)…");
			throwCard();

			System.out.println(
					"THE CARD YOU HAVE IS… " + (List<String>) ((Map<String, Object>) playerList.get(0)).get("allCard"));
			System.out.print("・SELECT TO OPEN THE CARD(1~3)…");
			openCard();
			showOpenCard();

			rank.setRank();
			rank.mainRank();

			bet.betMake();
			bet.mainBet();

			System.out.println("GIVING THE CARD SECOND…");
			openCardFlg = true;
			giveCardNum = 1;
			giveCard();
			showOpenCard();
			System.out.println(
					"THE CARD YOU HAVE IS… " + (List<String>) ((Map<String, Object>) playerList.get(0)).get("allCard"));

			rank.mainRank();
			bet.mainBet();

			System.out.println("GIVING THE CARD THIRD…");
			giveCard();
			showOpenCard();
			System.out.println(
					"THE CARD YOU HAVE IS… " + (List<String>) ((Map<String, Object>) playerList.get(0)).get("allCard"));

			rank.mainRank();
			bet.mainBet();

			System.out.println("GIVING THE CARD FOURTH…");
			giveCard();
			showOpenCard();
			System.out.println(
					"THE CARD YOU HAVE IS… " + (List<String>) ((Map<String, Object>) playerList.get(0)).get("allCard"));

			rank.mainRank();
			bet.mainBet();

			System.out.println("GIVING THE CARD LAST…");
			openCardFlg = false;
			giveCard();
			showOpenCard();
			System.out.println(
					"THE CARD YOU HAVE IS… " + (List<String>) ((Map<String, Object>) playerList.get(0)).get("allCard"));

			rank.mainRank();
			bet.mainBet();

			showAll();

			System.out.println("THE WINNER IS…！！！");

			System.out.print("CONTINUE(1) THE GAME? OR LEAVE?(0)…");
			continueGame();

			if (continueFlg) {
				cardListReset();
				continue;
			} else {
				break;
			}

		}

		scanner.close();

	}

	public static void selectLevel() {

		for (;;) {
			try {
				gameLevel = scanner.nextInt();
				if (gameLevel > 0 && gameLevel < 4) {
					break;
				} else {
					System.out.print("!? ONLY 1,2,3 POSSIBLE, TRY AGAIN…");
					continue;
				}
			} catch (Exception e) {
				System.out.print("!? PLEASE INPUT NUMBER 1,2,3 ONLY, TRY AGAIN…");
				scanner.next();
			}
		}

	}

	public static void selectMinimChip() {

		for (;;) {
			try {
				minimChip = scanner.nextInt();
				if (minimChip > 0 && minimChip < 11) {
					break;
				} else {
					System.out.print("!? ONLY " + minimChip + " TO 10 POSSIBLE, TRY AGAIN…");
					continue;
				}
			} catch (Exception e) {
				System.out.print("!? PLEASE INPUT NUMBER " + minimChip + " TO 10 ONLY, TRY AGAIN…");
				scanner.next();
			}
		}

	}

	public static void prepareCard() {

		pokerCardList = new ArrayList<>();
		suitMap = new LinkedHashMap<>();

		suitMap.put("spade", "♠");
		suitMap.put("daimond", "♢");
		suitMap.put("clover", "♣");
		suitMap.put("heart", "♡");

		for (int i = 1; i <= 13; i++) {
			for (String suit : suitMap.values()) {
				if (i == 1) {
					pokerCardList.add(suit + "A");
				} else if (i == 11) {
					pokerCardList.add(suit + "J");
				} else if (i == 12) {
					pokerCardList.add(suit + "Q");
				} else if (i == 13) {
					pokerCardList.add(suit + "K");
				} else {
					pokerCardList.add(suit + String.valueOf(i));
				}
			}
		}

		Collections.shuffle(pokerCardList);
		//		System.out.println(pokerCardList);

	}

	public static void selectPlayerNum() {

		for (;;) {
			try {
				playerNum = scanner.nextInt();
				if (playerNum > 1 && playerNum < 5) {
					break;
				} else {
					System.out.print("!? ONLY 2,3,4 POSSIBLE, TRY AGAIN…");
					continue;
				}
			} catch (Exception e) {
				System.out.print("!? PLEASE INPUT NUMBER 2,3,4 ONLY, TRY AGAIN…");
				scanner.next();
			}
		}

		List<String> allCard = new ArrayList<>();
		List<String> openCard = new ArrayList<>();
		Map<String, Object> rank = new LinkedHashMap<>();
		int winRate = 0;
		int chip = 1000000;
		boolean betFlg = true;
		boolean foldFlg = false;

		// USER SETTING
		user.put("allCard", allCard);
		user.put("openCard", openCard);
		user.put("rank", rank);
		user.put("winRate", winRate);
		user.put("chip", chip);
		user.put("betFlg", betFlg);
		user.put("foldFlg", foldFlg);

		playerList.add(user);

		// COM1 SETTING
		allCard = new ArrayList<>();
		openCard = new ArrayList<>();
		betFlg = false;

		com1.put("allCard", allCard);
		com1.put("openCard", openCard);
		com1.put("rank", rank);
		com1.put("winRate", winRate);
		com1.put("chip", chip);
		com1.put("betFlg", betFlg);
		com1.put("foldFlg", foldFlg);

		playerList.add(com1);

		if (playerNum == 3) {
			// COM2 SETTING
			allCard = new ArrayList<>();
			openCard = new ArrayList<>();
			betFlg = false;

			com2.put("allCard", allCard);
			com2.put("openCard", openCard);
			com2.put("rank", rank);
			com2.put("winRate", winRate);
			com2.put("chip", chip);
			com2.put("betFlg", betFlg);
			com2.put("foldFlg", foldFlg);

			playerList.add(com2);
		} else if (playerNum == 4) {
			allCard = new ArrayList<>();
			openCard = new ArrayList<>();
			betFlg = false;

			com2.put("allCard", allCard);
			com2.put("openCard", openCard);
			com2.put("rank", rank);
			com2.put("winRate", winRate);
			com2.put("chip", chip);
			com2.put("betFlg", betFlg);
			com2.put("foldFlg", foldFlg);

			playerList.add(com2);

			// COM3 SETTING
			allCard = new ArrayList<>();
			openCard = new ArrayList<>();
			betFlg = false;

			com3.put("allCard", allCard);
			com3.put("openCard", openCard);
			com3.put("rank", rank);
			com3.put("winRate", winRate);
			com3.put("chip", chip);
			com3.put("betFlg", betFlg);
			com3.put("foldFlg", foldFlg);

			playerList.add(com3);
		}

	}

	@SuppressWarnings("unchecked")
	public static void showOpenCard() {

		//
		for (int i = 0; i < playerList.size(); i++) {
			System.out.println("PLAYER OPEN CARD IS (" + i + ")…"
					+ (List<String>) ((Map<String, Object>) playerList.get(i)).get("openCard"));
		}

	}

	@SuppressWarnings("unchecked")
	public static void giveCard() {

		for (int i = 0; i < playerList.size(); i++) {
			for (int j = 0; j < giveCardNum; j++) {
				if (openCardFlg) {
					((List<String>) ((Map<String, Object>) playerList.get(i)).get("allCard")).add(pokerCardList.get(0));
					((List<String>) ((Map<String, Object>) playerList.get(i)).get("openCard"))
							.add(pokerCardList.get(0));
					pokerCardList.remove(0);
				} else {
					((List<String>) ((Map<String, Object>) playerList.get(i)).get("allCard")).add(pokerCardList.get(0));
					pokerCardList.remove(0);
				}
			}
		}

		// 
		//		System.out.println("※REMAINING POKER CARD NUMBER… " + pokerCardList.size());
		//		System.out.println("※REMAINING POKER CARD… " + pokerCardList);

	}

	@SuppressWarnings("unchecked")
	public static void throwCard() {

		int throwCardNum = 0;

		// USER入力
		for (;;) {
			try {
				throwCardNum = scanner.nextInt();
				if (throwCardNum > 0 && throwCardNum < 5) {
					((List<String>) ((Map<String, Object>) playerList.get(0)).get("allCard"))
							.remove(throwCardNum - 1);
					break;
				} else {
					System.out.print("!? ONLY 1,2,3,4 POSSIBLE, TRY AGAIN…");
					continue;
				}
			} catch (Exception e) {
				System.out.print("!? PLEASE INPUT NUMBER 1,2,3,4 ONLY, TRY AGAIN…");
				scanner.next();
			}
		}

		// TEMPORARY
		Random random = new Random();

		for (int i = 1; i < playerList.size(); i++) {
			throwCardNum = random
					.nextInt(((List<String>) ((Map<String, Object>) playerList.get(i)).get("allCard")).size());
			((List<String>) ((Map<String, Object>) playerList.get(i)).get("allCard"))
					.remove(throwCardNum);
		}

	}

	@SuppressWarnings("unchecked")
	public static void openCard() {

		int openCardNum = 0;

		// USER入力
		for (;;) {
			try {
				openCardNum = scanner.nextInt();
				if (openCardNum > 0 && openCardNum < 4) {
					((List<String>) ((Map<String, Object>) playerList.get(0)).get("openCard"))
							.add(((List<String>) ((Map<String, Object>) playerList.get(0)).get("allCard"))
									.get(openCardNum - 1));
					break;
				} else {
					System.out.print("!? ONLY 1,2,3 POSSIBLE, TRY AGAIN…");
					continue;
				}
			} catch (Exception e) {
				System.out.print("!? PLEASE INPUT NUMBER 1,2,3 ONLY, TRY AGAIN…");
				scanner.next();
			}
		}

		// TEMPORARY
		Random random = new Random();

		for (int i = 1; i < playerList.size(); i++) {
			openCardNum = random
					.nextInt(((List<String>) ((Map<String, Object>) playerList.get(i)).get("allCard")).size());
			((List<String>) ((Map<String, Object>) playerList.get(i)).get("openCard"))
					.add(((List<String>) ((Map<String, Object>) playerList.get(i)).get("allCard"))
							.get(openCardNum));
		}

	}

	public static void continueGame() {

		int continueNum = 1;

		for (;;) {
			try {
				continueNum = scanner.nextInt();
				if (continueNum == 1) {
					continueFlg = true;
					break;
				} else if (continueNum == 0) {
					continueFlg = false;
					break;
				} else {
					System.out.print("!? ONLY 1,0 POSSIBLE, TRY AGAIN…");
					continue;
				}
			} catch (Exception e) {
				System.out.print("!? PLEASE INPUT NUMBER 1,0 ONLY, TRY AGAIN…");
				scanner.next();
			}
		}

	}

	@SuppressWarnings("unchecked")
	public static void cardListReset() {

		for (int i = 0; i < playerNum; i++) {

			List<String> allCard = new ArrayList<>();
			List<String> openCard = new ArrayList<>();
			String rank = "UNKNOWN";
			int winRate = 0;
			boolean foldFlg = false;

			((Map<String, Object>) playerList.get(i)).put("allCard", allCard);
			((Map<String, Object>) playerList.get(i)).put("openCard", openCard);
			((Map<String, Object>) playerList.get(i)).put("rank", rank);
			((Map<String, Object>) playerList.get(i)).put("winRate", winRate);
			((Map<String, Object>) playerList.get(i)).put("foldFlg", foldFlg);

		}

	}

	@SuppressWarnings("unchecked")
	public static void showAll() {

		for (int i = 0; i < playerNum; i++) {
			System.out.println(
					"PLAYER (" + i + ") ALL CARD IS：" + ((Map<String, Object>) playerList.get(i)).get("allCard")
							+ ((Map<String, Object>) playerList.get(i)).get("rank"));
		}

	}

}
