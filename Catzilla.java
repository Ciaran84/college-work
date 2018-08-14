// this was a team effort, as such, code with commented asterisks above and below 
// is code that I had no input on. all other commented out code was due to different strategies
// we conceived for a competition. these are detailed in comments within the haveSolution method.

package bots;

import gameengine.Cards;
import gameengine.Deck;
import gameengine.Dice;
import gameengine.Log;
import gameengine.Map;
import gameengine.Names;
import gameengine.Player;
import gameengine.PlayersInfo;

public class Catzilla implements BotAPI {

	// The public API of Bot must not change
	// This is ONLY class that you can edit in the program
	// Rename Bot to the name of your team. Use camel case.
	// Bot may not alter the state of the board or the player objects
	// It may only inspect the state of the board and the player objects

	private Player player;
	private PlayersInfo playersInfo;
	private Map map;
	private Dice dice;
	private Log log;
	private Deck deck;

	Boolean moveOver=false;
	String position_player = null;
	String player_name_over = null;
	int diceCount = 0;
	private boolean moved = false;
	private boolean askedQuestion = false;
	private String[] logEntry;
	private String[] currentPath;
	private String currentPathString;
	String murderRoom = null;
	
	
//******************************************************
	int K_CONS = 0;
	int BALL_CONS = 1;
	int C_CONS = 2;
	int D_CONS = 3;
	int BILL_CONS = 4;
	int LIB_CONS = 5;
	int LOUN_CONS = 6;
	int HALL_CONS = 7;
	int STU_CONS = 8;
	int CENTRE_CONS = 9;
	
//*******************************************************

	String [][] moveFromKit = new String [10][1];
	String [][] moveFromBall = new String [10][2];
	String [][] moveFromBilliard = new String [10][2];
	String [][] moveFromLou = new String [10][1];
	String [][] moveFromHall = new String [10][2];
	String [][] moveFromStudy = new String [10][1];
	String [][] moveFromDin = new String [10][2];
	String [][] moveFromCon = new String [10][1];
	String [][] moveFromLib = new String [10][2];


	//moving Tokens from starting positions
	String [][] startWhite = {{"dllddddddlllu","0"},{"dllddddr","1"},{},{"dllddddddddrdddl","3"}};
	String [][] startGreen = {{},{"drrddddl","1"},{"drrddddrru","2"},{},{"drrddddddddrr","4"}};
	String [][] startPeacock = {{},{"lllllllul","1"},{"llllluu","2"},{},{"lllllldddr","4"}};
	String [][] startPlum = {{},{},{},{},{},{"llllllluuur","5"},{},{"lllllllldl","7"},{"lllllldd","8"}};
	String [][] startScarlett = {{},{},{},{"uuuuuuuulu","3"},{},{},{"uuuuuuld","6"},{"uuuuuuurrrrd","7"}};
	String [][] startMustard = {{},{},{},{"rrrrrruu","3"},{},{},{"rrrrrrdd","6"},{"rrrrrrrrrrrd","7"}};

//*********************************************************************************************
	//hardcoded paths for all possible movements of the tokens
	
	//Below are the ways to move from 1 room to another
	public void moveFromKitMethod()
	{
		moveFromKit[0][0] = "du";
		moveFromKit[1][0] = "drrruur";
		moveFromKit[2][0] = "ddrrrrrrrrrrrrruuuru";
		moveFromKit[3][0] = "ddrdrrrdddl";
		moveFromKit[4][0] = "ddrrrrrrrrrrrrrdr";
		moveFromKit[5][0] = "ddrrrrrrrrrrrrrdddddrrrd";
		moveFromKit[6][0] = "ddrdrrrdddddddddlld";
		moveFromKit[7][0] = "ddrdrrrddddddddrrrd";
		moveFromKit[8][0] = "passage";
		moveFromKit[9][0] = "ddrdrrrddddddddrrrru";
	}

	public void moveFromBallMethod()
	{
		moveFromBall[0][0] = "1";
		moveFromBall[1][0] = null;
		moveFromBall[2][0] = "4";
		moveFromBall[3][0] = "2";
		moveFromBall[4][0] = "3";
		moveFromBall[5][0] = "3";
		moveFromBall[6][0] = "2";
		moveFromBall[7][0] = "2";
		moveFromBall[8][0] = "3";
		moveFromBall[9][0] = "2";

		moveFromBall[0][1] = "lddlllu";
		moveFromBall[1][1] = "lr";
		moveFromBall[2][1] = "rrru";
		moveFromBall[3][1] = "dddddll";
		moveFromBall[4][1] = "ddrrrr";
		moveFromBall[5][1] = "ddrrdddddddr";
		moveFromBall[6][1] = "ddddddddddllldd";
		moveFromBall[7][1] = "ddddddddddrrd";
		moveFromBall[8][1] = "ddrrdddddddddddrd";
		moveFromBall[9][1] = "ddddddddddrrru";

	}

	public void moveFromBilliardMethod()
	{
		moveFromBilliard[0][0] = "1";
		moveFromBilliard[1][0] = "1";
		moveFromBilliard[2][0] = "1";
		moveFromBilliard[3][0] = "1";
		moveFromBilliard[4][0] = null;
		moveFromBilliard[5][0] = "2";
		moveFromBilliard[6][0] = "1";
		moveFromBilliard[7][0] = "1";
		moveFromBilliard[8][0] = "1";
		moveFromBilliard[9][0] = "1";

		moveFromBilliard[0][1] = "llllllllllllluulu";
		moveFromBilliard[1][1] = "lllluu";
		moveFromBilliard[2][1] = "luuuuru";
		moveFromBilliard[3][1] = "lllllllllldddl";
		moveFromBilliard[4][1] = "lr";
		moveFromBilliard[5][1] = "dlld";
		moveFromBilliard[6][1] = "ldddddldddlllllllllldd";
		moveFromBilliard[7][1] = "ldddddldddlllld";
		moveFromBilliard[8][1] = "ldddddlddddddrd";
		moveFromBilliard[9][1] = "ldddddldddllllu";

	}

	public void moveFromLouMethod()
	{
		moveFromLou[0][0] = "uuurruuuuuuuulllluu";
		moveFromLou[1][0] = "uuurruuuuuuuuru";
		moveFromLou[2][0] = "passage";
		moveFromLou[3][0] = "uuuu";
		moveFromLou[4][0] = "uurrrrrrrrrruuuuuuuurr";
		moveFromLou[5][0] = "uurrrrrrrrrrur";
		moveFromLou[6][0] = "uudd";
		moveFromLou[7][0] = "uurrrrrd";
		moveFromLou[8][0] = "uurrrrrrrrrrdddrd";
		moveFromLou[9][0] = "uurrrrrru";
	}

	public void moveFromHallMethod()
	{
		moveFromHall[0][0] = "1";
		moveFromHall[1][0] = "1";
		moveFromHall[2][0] = "2";
		moveFromHall[3][0] = "1";
		moveFromHall[4][0] = "2";
		moveFromHall[5][0] = "2";
		moveFromHall[6][0] = "1";
		moveFromHall[7][0] = null;
		moveFromHall[8][0] = "3";
		moveFromHall[9][0] = "2";

		moveFromHall[0][1] = "ullluuuuuuuuulllluu";
		moveFromHall[1][1] = "ulluuuuuuuuuu";
		moveFromHall[2][1] = "urrrruuuuuuuuuuuurru";
		moveFromHall[3][1] = "ullllluu";
		moveFromHall[4][1] = "urrrruuuuuuuuuuuurru";
		moveFromHall[5][1] = "urrrrur";
		moveFromHall[6][1] = "ullllldd";
		moveFromHall[7][1] = "ud";
		moveFromHall[8][1] = "rrrd";
		moveFromHall[9][1] = "uu";

	}

	public void moveFromStudyMethod()
	{
		moveFromStudy[0][0] = "passage";
		moveFromStudy[1][0] = "ulluuuuuuuuuuuulu";
		moveFromStudy[2][0] = "uluuuuuuuuuuuuuuurru";
		moveFromStudy[3][0] = "ulluuullllllllluu";
		moveFromStudy[4][0] = "uluuuuuuuuuuurr";
		moveFromStudy[5][0] = "uuuluur";
		moveFromStudy[6][0] = "uuullullllllllldd";
		moveFromStudy[7][0] = "ulll";
		moveFromStudy[8][0] = "uudd";
		moveFromStudy[9][0] = "uuulullllu";
	}

	public void moveFromDinMethod()
	{
		moveFromDin[0][0] = "2";
		moveFromDin[1][0] = "2";
		moveFromDin[2][0] = "2";
		moveFromDin[3][0] = null;
		moveFromDin[4][0] = "2";
		moveFromDin[5][0] = "1";
		moveFromDin[6][0] = "1";
		moveFromDin[7][0] = "1";
		moveFromDin[8][0] = "1";
		moveFromDin[9][0] = "1";

		moveFromDin[0][1] = "ruuuuluu";
		moveFromDin[1][1] = "rruuuuu";
		moveFromDin[2][1] = "rruuuurrrrrrrruuuru";
		moveFromDin[3][1] = "du";
		moveFromDin[4][1] = "rruuurrr";
		moveFromDin[5][1] = "ddrrrrrrrrrrur";
		moveFromDin[6][1] = "dddd";
		moveFromDin[7][1] = "ddrrrrrd";
		moveFromDin[8][1] = "ddrrrrrrrrrrdddrd";
		moveFromDin[9][1] = "ddrrrrrru";

	}

	public void moveFromConMethod()
	{
		moveFromCon[0][0] = "dddldllllllllllllluu";
		moveFromCon[1][0] = "dlll";
		moveFromCon[2][0] = "du";
		moveFromCon[3][0] = "dddlddllllllllldddl";
		moveFromCon[4][0] = "dddlddr";
		moveFromCon[5][0] = "dddlddddddrrrd";
		moveFromCon[6][0] = "passage";
		moveFromCon[7][0] = "dddlldddddddddddddll";
		moveFromCon[8][0] = "dddlldddddddddddddrd";
		moveFromCon[9][0] = "dddllddddddddddllllu";
	}

	public void moveFromLibMethod()
	{
		moveFromLib[0][0] = "1";
		moveFromLib[1][0] = "1";
		moveFromLib[2][0] = "1";
		moveFromLib[3][0] = "1";
		moveFromLib[4][0] = "2";
		moveFromLib[5][0] = null;
		moveFromLib[6][0] = "1";
		moveFromLib[7][0] = "1";
		moveFromLib[8][0] = "1";
		moveFromLib[9][0] = "1";

		moveFromLib[0][1] = "luuuuuuuulllllllllllluu";
		moveFromLib[1][1] = "luuuuuuuullu";
		moveFromLib[2][1] = "luuuuuuuuuuurru";
		moveFromLib[3][1] = "ldlllllllllluu";
		moveFromLib[4][1] = "urru";
		moveFromLib[5][1] = "lr";
		moveFromLib[6][1] = "llrllllllllldd";
		moveFromLib[7][1] = "lldllld";
		moveFromLib[8][1] = "lddddrd";
		moveFromLib[9][1] = "lldlllu";

	}
//************************************************************************************************	


	public Catzilla (Player player, PlayersInfo playersInfo, Map map, Dice dice, Log log, Deck deck) {
		this.player = player;
		this.playersInfo = playersInfo;
		this.map = map;
		this.dice = dice;
		this.log = log;
		this.deck = deck;
	}

	public String getName() {
		return "Catzilla"; // must match the class name
	}

	public String getCommand() {
		if(!player.getToken().isInRoom()) { //not in room
			if(!moved) { //have not moved yet
				if(atStart()) {//if at starting position add optimum path
					moveFromKitMethod();
					moveFromBallMethod();
					moveFromBilliardMethod();
					moveFromLouMethod();
					moveFromHallMethod();
					moveFromStudyMethod();
					moveFromDinMethod();
					moveFromConMethod();
					moveFromLibMethod();
					setPath();
				}
				moved = true;
				return "roll";
			}
			else { //have already moved
				moved = false;
				askedQuestion = false;
				return "done";
			}
		}
		else { //in a room

			if(player.getToken().getRoom().toString().equals("Cellar")) { //if you're in the cellar
				return "accuse";
			}
			else { //not in cellar
				if(!askedQuestion) { //haven't asked a question yet
					if(!known(player.getToken().getRoom().toString())) {
						//this room has not been checked off
						askedQuestion = true;
						return "question";
					}
					else { //room has been checked off
						if(!moved) { //not moved yet
							if(player.getToken().getRoom().hasPassage() && (!known(player.getToken().getRoom().getPassageDestination().toString()))) {
								//room has a passage and destination room has not been checked off
								moved = true;
								askedQuestion = false;
								return "passage";
							}
							else if(player.getToken().getRoom().toString().equals("Kitchen") && known("Ballroom") && known("Dining Room")) {
								moved = true;
								askedQuestion = false;
								return "passage";
							}
							else if(player.getToken().getRoom().toString().equals("Conservatory") && known("Ballroom") && known("Billiard Room")) {
								moved = true;
								askedQuestion = false;
								return "passage";
							}
							else if(player.getToken().getRoom().toString().equals("Study") && known("Hall") && known("Library")) {
								moved = true;
								askedQuestion = false;
								return "passage";
							}
							else if(player.getToken().getRoom().toString().equals("Lounge") && known("Hall") && known("Dining Room")) {
								moved = true;
								askedQuestion = false;
								return "passage";
							}
							else { //no passage or destination checked off
								moved = true;
								askedQuestion = false;
								setPath();
								return "roll";
							}
						}
						else { //can't move, might as well ask a question
							askedQuestion = true;
							return "question";
						}
					}
				}
				else { //have asked a question
					if(!moved) { //question was asked on previous turn
						moved = true;
						askedQuestion = false;
						setPath();
						return "roll";
					}
					else { //question asked on current turn
						moved = false;
						return "done";
					}
				}
			}
		}
	}


	public String getMove() {
		if(currentPathString==null) {
			return randomMove();
		}
		else {
			return followPath();
		}
	}
	public String randomMove() {
		// Add your code here

		int direction = 1 + (int)(Math.random() * 4);

		if(direction == 1) {
			if (map.isValidMove(player.getToken().getPosition(), "l")) {
				return "l";
			}
			else {
				return "r";
			}
		}	 
		else if (direction == 2) {
			if(map.isValidMove(player.getToken().getPosition(), "r")) {
				return "r";
			}
			else {
				return "l";
			}
		}
		else if (direction == 3) {
			if (map.isValidMove(player.getToken().getPosition(), "u")) {
				return "u";
			}
			else if(map.isValidMove(player.getToken().getPosition(), "d")) {
				return "d";
			}
			else {
				return "l";
			}
		}		
		else if (direction == 4) {
			if (map.isValidMove(player.getToken().getPosition(), "d")) {
				return "d";
			}
			else if (map.isValidMove(player.getToken().getPosition(), "u")) {
				return "u";
			}
			else {
				return "r";
			}
		}

		return null;
	}


	public String getSuspect() {
		int index = (int)(Math.random()*6);
		String suspect = Names.SUSPECT_NAMES[index];
		if(!known(suspect)) {
			return suspect;
		}
		else {
			return getSuspect();
		}
	}

	public String getWeapon() {
		int index = (int)(Math.random()*6);
		String weapon = Names.WEAPON_NAMES[index];
		if(!known(weapon)) {
			return weapon;
		}
		else {
			return getWeapon();
		}
	}

	public String getRoom() {
		int index = (int)(Math.random()*6);
		String room = Names.ROOM_CARD_NAMES[index];
		if(!known(room)) {
			return room;
		}
		else {
			return getRoom();
		}
	}

	public String getDoor() {
		if(player.getToken().getRoom().toString().equalsIgnoreCase("Ballroom")) {
			if(haveSolution()) {
				return "2";
			} 
			else {
				if(!known("Ballroom")) {
					return "1";
				}
				else if(!known("Kitchen")) {
					return "1";
				}
				else if(!known("Conservatory")) {
					return "4";
				}
				else if(!known("Billiard Room")){
					return "3";
				}
				else if(!known("Dining Room")) {
					return "2";
				}
				else if(!known("Library")){
					return "3";
				}
				else if(!known("Hall")){
					return "2";
				}
				else if(!known("Lounge")){
					return "2";
				}
				else {
					return "3";
				}
			}
		}
		else if(player.getToken().getRoom().toString().equalsIgnoreCase("Billiard Room")) {
			if(haveSolution()) {
				return "1";
			}
			else {
				if(!known("Billiard Room")) {
					return "1";
				}
				else if(!known("Library")) {
					return "2";
				}
				else {
					return "1";
				}
			}
		}
		else if(player.getToken().getRoom().toString().equalsIgnoreCase("Hall")) {
			if(haveSolution()) {
				return "2";
			}
			else {
				if(!known("Hall")) {
					return "1";
				}
				else if(!known("Study")) {
					return "3";
				}
				else if(!known("Library")){
					return "2";
				}
				else if((!known("Lounge"))||(!known("Dining Room"))) {
					return "1";
				}
				else if(!known("Ballroom")){
					return "1";
				}
				else if(!known("Billiard Room")){
					return "2";
				}
				else if(!known("Kitchen")){
					return "1";
				}
				else {
					return "2";
				}
			}
		}
		else if(player.getToken().getRoom().toString().equalsIgnoreCase("Dining Room")) {
			if(haveSolution()) {
				return "1";
			}
			else {
				if(!known("Dining Room")) {
					return "1";
				}
				else if(!known("Lounge")) {
					return "1";
				}
				else if(!known("Ballroom")){
					return "2";
				}
				else if(!known("Hall")) {
					return "1";
				}
				else if(!known("Kitchen")){
					return "2";
				}
				else if(!known("Library")){
					return "1";
				}
				else if(!known("Billiard Room")){
					return "2";
				}
				else if(!known("Study")){
					return "1";
				}
				else {
					return "2";
				}
			}
		}
		else {
			if(haveSolution()) {
				return "1";
			}
			else {
				if(!known("Library")) {
					return "1";
				}
				else if(!known("Billiard Room")) {
					return "2";
				}
				else {
					return "1";
				}
			}
		}

	}


	public String getCard(Cards matchingCards) {
		return matchingCards.get().toString();
	}

//********************************************************************************
	public void notifyResponse(Log response) {
		
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void notifyPlayerName(String playerName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyTurnOver(String playerName, String position) {
		// TODO Auto-generated method stub


		position_player = position;
		player_name_over = playerName;


	}

	@Override
	public void notifyQuery(String playerName, String query) {
		// TODO Auto-generated method stub


	}

	@Override
	public void notifyReply(String playerName, boolean cardShown) {
		// TODO Auto-generated method stub

	}
//**********************************************************************************
	
	public boolean haveSolution() {
// strategy 1: once suspect and weapon is confirmed, assume current room is correct and guess.
// 			   very fast, 64% success rate in 300 tests.
//		if(knowSuspect() && knowWeapon()) {
//			murderRoom = player.getToken().getRoom().toString();
//			return true;
//		}
//		else { 
//			return false;
//		}
		
// strategy 2: confirm all 3 cards are correct before making accusation
//			   much slower, 100% success rate in 300 tests.
		return knowSuspect() && knowWeapon() && knowRoom();
	}
		public boolean knowRoom() {
			int unknowns = 0;
			for(int i = 0;i<Names.ROOM_CARD_NAMES.length;i++) {
				if(!known(Names.ROOM_CARD_NAMES[i])) {
					unknowns++;
				}
			}
			return unknowns==1;
		}
	public boolean knowSuspect() {
		int unknowns = 0;
		for(int i = 0;i<Names.SUSPECT_NAMES.length;i++) {
			if(!known(Names.SUSPECT_NAMES[i])) {
				unknowns++;
			}
		}
		return unknowns==1;
	}
	public boolean knowWeapon() {
		int unknowns = 0;
		for(int i = 0;i<Names.WEAPON_NAMES.length;i++) {
			if(!known(Names.WEAPON_NAMES[i])) {
				unknowns++;
			}
		}
		return unknowns==1;
	}
	public boolean known(String name) {
		return (player.hasCard(name)||player.hasSeen(name)||deck.isSharedCard(name));
	}

	public void setPath() {
		if(player.getToken().isInRoom()) {
			if(player.getToken().getRoom().toString().equalsIgnoreCase("Ballroom")) {
				if(haveSolution()) {
					splitPath(moveFromBall[9][1]);
				} 
				else {
					if(!known("Kitchen")) {
						splitPath(moveFromBall[0][1]);
					}
					else if(!known("Conservatory")) {
						splitPath(moveFromBall[2][1]);
					}
					else if(!known("Billiard Room")){
						splitPath(moveFromBall[4][1]);
					}
					else if(!known("Dining Room")) {
						splitPath(moveFromBall[3][1]);
					}
					else if(!known("Library")){
						splitPath(moveFromBall[5][1]);
					}
					else if(!known("Hall")){
						splitPath(moveFromBall[7][1]);
					}
					else if(!known("Lounge")){
						splitPath(moveFromBall[6][1]);
					}
					else {
						splitPath(moveFromBall[8][1]);
					}
				}
			}
			else if(player.getToken().getRoom().toString().equalsIgnoreCase("Billiard Room")) {
				if(haveSolution()) {
					splitPath(moveFromBilliard[9][1]);
				}
				else {
					if(!known("Library")) {
						splitPath(moveFromBilliard[5][1]);
					}
					else if(!known("Ballroom")) {
						splitPath(moveFromBilliard[1][1]);
					}
					else if(!known("Conservatory")) {
						splitPath(moveFromBilliard[2][1]);
					}
					else if(!known("Dining Room")) {
						splitPath(moveFromBilliard[3][1]);
					}
					else if(!known("Hall")) {
						splitPath(moveFromBilliard[7][1]);
					}
					else if(!known("Study")) {
						splitPath(moveFromBilliard[8][1]);
					}
					else if(!known("Kitchen")) {
						splitPath(moveFromBilliard[0][1]);
					}
					else {
						splitPath(moveFromBilliard[6][1]);
					}
				}
			}
			else if(player.getToken().getRoom().toString().equalsIgnoreCase("Hall")) {
				if(haveSolution()) {
					splitPath(moveFromHall[9][1]);
				}
				else {
					if(!known("Study")) {
						splitPath(moveFromHall[8][1]);
					}
					else if(!known("Library")){
						splitPath(moveFromHall[5][1]);
					}
					else if(!known("Lounge")) {
						splitPath(moveFromHall[6][1]);
					}
					else if(!known("Dining Room")) {
						splitPath(moveFromHall[3][1]);
					}
					else if(!known("Ballroom")){
						splitPath(moveFromHall[1][1]);
					}
					else if(!known("Billiard Room")){
						splitPath(moveFromHall[4][1]);
					}
					else if(!known("Kitchen")){
						splitPath(moveFromHall[0][1]);
					}
					else {
						splitPath(moveFromHall[2][1]);
					}
				}
			}
			else if(player.getToken().getRoom().toString().equalsIgnoreCase("Dining Room")) {
				if(haveSolution()) {
					splitPath(moveFromDin[9][1]);
				}
				else {
					if(!known("Lounge")) {
						splitPath(moveFromDin[6][1]);
					}
					else if(!known("Ballroom")){
						splitPath(moveFromDin[1][1]);
					}
					else if(!known("Hall")) {
						splitPath(moveFromDin[7][1]);
					}
					else if(!known("Kitchen")){
						splitPath(moveFromDin[0][1]);
					}
					else if(!known("Library")){
						splitPath(moveFromDin[5][1]);
					}
					else if(!known("Billiard Room")){
						splitPath(moveFromDin[4][1]);
					}
					else if(!known("Study")){
						splitPath(moveFromDin[8][1]);
					}
					else {
						splitPath(moveFromDin[2][1]);
					}
				}
			}
			else if(player.getToken().getRoom().toString().equalsIgnoreCase("Library")) {
				if(haveSolution()) {
					splitPath(moveFromLib[9][1]);
				}
				else {
					if(!known("Billiard Room")) {
						splitPath(moveFromLib[4][1]);
					}
					else if(!known("Study")) {
						splitPath(moveFromLib[8][1]);
					}
					else if(!known("Hall")) {
						splitPath(moveFromLib[7][1]);
					}
					else if(!known("Ballroom")) {
						splitPath(moveFromLib[1][1]);
					}
					else if(!known("Lounge")) {
						splitPath(moveFromLib[6][1]);
					}
					else if(!known("Dining Room")) {
						splitPath(moveFromLib[3][1]);
					}
					else if(!known("Conservatory")) {
						splitPath(moveFromLib[2][1]);
					}
					else {
						splitPath(moveFromLib[0][1]);
					}
				}
			}
			else if(player.getToken().getRoom().toString().equalsIgnoreCase("Kitchen")) {
				if(haveSolution()) {
					splitPath(moveFromKit[9][0]);
				}
				else {
					if(!known("Ballroom")) {
						splitPath(moveFromKit[1][0]);
					}
					else if(!known("Dining Room")) {
						splitPath(moveFromKit[3][0]);
					}
					else if(!known("Billiard Room")) {
						splitPath(moveFromKit[4][0]);
					}
					else if(!known("Lounge")) {
						splitPath(moveFromKit[6][0]);
					}
					else if(!known("Hall")) {
						splitPath(moveFromKit[7][0]);
					}
					else if(!known("Conservatory")) {
						splitPath(moveFromKit[2][0]);
					}
					else {
						splitPath(moveFromKit[5][0]);
					}
				}
			}
			else if(player.getToken().getRoom().toString().equalsIgnoreCase("Conservatory")) {
				if(haveSolution()) {
					splitPath(moveFromCon[9][0]);
				}
				else {
					if(!known("Ballroom")) {
						splitPath(moveFromCon[1][0]);
					}
					else if(!known("Billiard Room")) {
						splitPath(moveFromCon[4][0]);
					}
					else if(!known("Library")) {
						splitPath(moveFromCon[5][0]);
					}
					else if(!known("Dining Room")) {
						splitPath(moveFromCon[3][0]);
					}
					else if(!known("Kitchen")) {
						splitPath(moveFromCon[0][0]);
					}
					else if(!known("Hall")) {
						splitPath(moveFromCon[7][0]);
					}
					else {
						splitPath(moveFromCon[8][0]);
					}
				}
			}
			else if(player.getToken().getRoom().toString().equalsIgnoreCase("Lounge")) {
				if(haveSolution()) {
					splitPath(moveFromLou[9][0]);
				}
				else {
					if(!known("Dining Room")) {
						splitPath(moveFromLou[3][0]);
					}
					else if(!known("Hall")) {
						splitPath(moveFromLou[7][0]);
					}
					else if(!known("Library")) {
						splitPath(moveFromLou[5][0]);
					}
					else if(!known("Ballroom")) {
						splitPath(moveFromLou[1][0]);
					}
					else if(!known("Study")) {
						splitPath(moveFromLou[8][0]);
					}
					else if(!known("Kitchen")) {
						splitPath(moveFromLou[0][0]);
					}
					else {
						splitPath(moveFromLou[4][0]);

					}
				}
			}
			else if(player.getToken().getRoom().toString().equalsIgnoreCase("Study")) {
				if(haveSolution()) {
					splitPath(moveFromStudy[9][0]);
				}
				else {
					if(!known("Hall")) {
						splitPath(moveFromStudy[7][0]);
					}
					else if(!known("Library")) {
						splitPath(moveFromStudy[5][0]);
					}
					else if(!known("Billiard Room")) {
						splitPath(moveFromStudy[4][0]);
					}
					else if(!known("Lounge")) {
						splitPath(moveFromStudy[6][0]);
					}
					else if(!known("Ballroom")) {
						splitPath(moveFromStudy[1][0]);
					}
					else if(!known("Dining Room")) {
						splitPath(moveFromStudy[3][0]);
					}
					else {
						splitPath(moveFromStudy[2][0]);
					}
				}
			}
		}
		else {
			if(player.getToken().getPosition().getCol()==9 && player.getToken().getPosition().getRow()==0) {
				if(!known("Ballroom")) {
					splitPath(startWhite[1][0]);
				}
				else if(!known("Kitchen")) {
					splitPath(startWhite[0][0]);
				}
				else if(!known("Dining Room")) {
					splitPath(startWhite[3][0]);
				}
				else {
					splitPath(startWhite[0][0]);
				}
			}
			else if(player.getToken().getPosition().getCol()==14 && player.getToken().getPosition().getRow()==0) {
				if(!known("Ballroom")) {
					splitPath(startGreen[1][0]);
				}
				else if(!known("Conservatory")) {
					splitPath(startGreen[2][0]);
				}
				else if(!known("Billiard Room")) {
					splitPath(startGreen[4][0]);
				}
				else {
					splitPath(startGreen[2][0]);
				}
			}
			else if(player.getToken().getPosition().getCol()==23 && player.getToken().getPosition().getRow()==6) {
				if(!known("Conservatory")) {
					splitPath(startPeacock[2][0]);
				}
				else if(!known("Ballroom")) {
					splitPath(startPeacock[1][0]);
				}
				else if(!known("Billiard Room")) {
					splitPath(startPeacock[4][0]);
				}
				else {
					splitPath(startPeacock[2][0]);
				}
			}
			else if(player.getToken().getPosition().getCol()==23 && player.getToken().getPosition().getRow()==19) {
				if(!known("Study")) {
					splitPath(startPlum[8][0]);
				}
				else if(!known("Hall")) {
					splitPath(startPlum[7][0]);
				}
				else if(!known("Library")) {
					splitPath(startPlum[5][0]);
				}
				else {
					splitPath(startPlum[8][0]);
				}
			}
			else if(player.getToken().getPosition().getCol()==7 && player.getToken().getPosition().getRow()==24) {
				if(!known("Lounge")) {
					splitPath(startScarlett[6][0]);
				}
				else if(!known("Dining Room")) {
					splitPath(startScarlett[7][0]);
				}
				else if(!known("Hall")) {
					splitPath(startScarlett[3][0]);
				}
				else {
					splitPath(startScarlett[6][0]);
				}
			}
			else {
				if(!known("Lounge")) {
					splitPath(startMustard[6][0]);
				}
				else if(!known("Dining Room")) {
					splitPath(startMustard[3][0]);
				}
				else if(!known("Hall")) {
					splitPath(startMustard[7][0]);
				}
				else {
					splitPath(startMustard[6][0]);
				}
			}
		}
	}

	public boolean atStart() {
		return (player.getToken().getPosition().getCol()==9 && player.getToken().getPosition().getRow()==0 ||
				player.getToken().getPosition().getCol()==14 && player.getToken().getPosition().getRow()==0 ||
				player.getToken().getPosition().getCol()==23 && player.getToken().getPosition().getRow()==6 ||
				player.getToken().getPosition().getCol()==23 && player.getToken().getPosition().getRow()==19 ||
				player.getToken().getPosition().getCol()==7 && player.getToken().getPosition().getRow()==24 ||
				player.getToken().getPosition().getCol()==0 && player.getToken().getPosition().getRow()==17);
	}

	public void splitPath(String path) {
		System.out.println(path);
		currentPathString = path;
		//currentPath = path.split("");
		//System.out.println(currentPath.toString());
	}

	//stores the currentPath
	public String followPath() {
		String move = String.valueOf(currentPathString.charAt(0));
		if(currentPathString.length()>1) {
			currentPathString = currentPathString.substring(1);
			System.out.println(currentPathString);
			splitPath(currentPathString);
		}
		else {
			currentPathString = null;
		}
		return move;
	}
}
