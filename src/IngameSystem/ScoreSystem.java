package IngameSystem;

enum Winner{P1, P2, DRAW};

public class ScoreSystem {

	private static int Player1Score;
	private static int Player2Score;
	
	public static void initialize()
	{
		Player1Score=0;
		Player2Score=0;
	}
	
	public static void addScore(int playerIdx)
	{
		switch(playerIdx)
		{
		case 1:
			Player1Score++;
			break;
		case 2:
			Player2Score++;
			break;
		}
	}
	
	public static int getScore(int playerIdx)
	{
		switch(playerIdx)
		{
		case 1:
			return Player1Score;
		case 2:
			return Player2Score;
		default:
			return -1;			
		}
	}
	
	public static Winner whoWin()
	{
		if(Player1Score<Player2Score)
		{
			return Winner.P2;
		}
		else if(Player2Score>Player2Score)
		{
			return Winner.P1;
		}
		else
		{
			return Winner.DRAW;
		}
	}
	
	
}
