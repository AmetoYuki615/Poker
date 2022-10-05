package com.poker;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Bet extends Poker {

	public static List<Object> betList = new ArrayList<>();
	public static Map<String, Integer> betStatusMap = new LinkedHashMap<>();
	public static Map<Integer, Integer> betPlayerMap = new LinkedHashMap<>();
	
	public static int betTurnPlayer = 0;
	public static boolean betOverFlg = false;

	public Bet() {

	}

	public void betMake() {

		// BETTING状況マップ作成
		betStatusMap.put("allBetChip", 0);
		betStatusMap.put("turnBetChip", 0);

		// PLAYER数分BETTINGマップ作成
		for (int i = 0; i < playerNum; i++) {
			betPlayerMap.put(i, 0);
		}

	}

	@SuppressWarnings("unchecked")
	public void mainBet() {

		for (;;) {

			// betFlgがTRUEのPLAYERからBETTINGする
			for (int i = 0; i < playerNum; i++) {
				if ((boolean) ((Map<String, Object>) playerList.get(i)).get("betFlg")) {
					if (playerList.indexOf(user) == i) {
						betTurnPlayer = i;
						selectBet();
					} else {
						betTurnPlayer = i;
						selectBet();
					}
				}
			}

			for (int i = 0; i < playerNum; i++) {

			}
			betStatusMap.put("allBetChip", betStatusMap.get("allBetChip") + betStatusMap.get("turnBetChip"));
			break;
		}

	}

	public void selectBet() {

		//
		Random random = new Random();
		int betNum = 0;
		System.out.print("・SELECT TO RAISE(1), CHECK(2) OR FOLD(0)…");

		if (betTurnPlayer == 0) {
			for (;;) {
				try {
					betNum = scanner.nextInt();
					if (betNum == 1) {
						raise();
						break;
					} else if (betNum == 2) {
						check();
						break;
					} else if (betNum == 0) {
						fold();
						break;
					} else {
						System.out.print("!? ONLY 1,2,0 POSSIBLE, TRY AGAIN…");
						continue;
					}
				} catch (Exception e) {
					System.out.print("!? PLEASE INPUT NUMBER 1,2,0 ONLY, TRY AGAIN…");
					scanner.next();
				}
			}
		} else {
			//			if (betTurnPlayer == i) {
			//
			betNum = random.nextInt(3);
			if (betNum == 1) {
				raise();
			} else if (betNum == 2) {
				check();
			} else if (betNum == 0) {
				fold();
			}
			//			}
		}

	}

	public void raise() {

		//
		Random random = new Random();
		int raiseChip = minimChip;

		if (betTurnPlayer == 0) {
			for (;;) {
				System.out.print("・HOW MANY CHIPS DO YOU WANT TO RAISE(" + minimChip + " TO 10)?…");
				try {
					raiseChip = scanner.nextInt();
					if (raiseChip >= minimChip && raiseChip <= 10) {
						betPlayerMap.put(betTurnPlayer, betPlayerMap.get(betTurnPlayer) + raiseChip);
						betStatusMap.put("turnBetChip", betStatusMap.get("turnBetChip") + raiseChip);
						System.out.println("PLAYER (" + playerNum + ") RAISED (" + raiseChip + ") CHIP");
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
		} else {
			raiseChip = random.nextInt(4) + 1;
			betPlayerMap.put(betTurnPlayer, betPlayerMap.get(betTurnPlayer) + raiseChip);
			betStatusMap.put("turnBetChip", betStatusMap.get("turnBetChip") + raiseChip);
			System.out.println("PLAYER (" + playerNum + ") RAISED (" + raiseChip + ") CHIP");
		}

	}

	public void check() {

		System.out.println("PLAYER (" + playerNum + ") CHECKED");

	}

	public void call() {

		System.out.println("PLAYER (" + playerNum + ") CALLED");

	}

	@SuppressWarnings("unlikely-arg-type")
	public void fold() {

		betPlayerMap.remove("betTurnPlayer");
		System.out.println("PLAYER (" + playerNum + ") FOLD");

	}

}
