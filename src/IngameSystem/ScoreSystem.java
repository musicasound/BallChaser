package IngameSystem;

import Main.GlobalDataManager;

enum Winner{P1, P2, DRAW};

public class ScoreSystem {

	private static int Player1Score;
	private static int Player2Score;
	
	private static int currentCatchPlayerIdx=-1;
	private static int catchStartTileIdx=-1;
	private static int currentCatchingTileIdx=-1;
	
	public static void initialize()
	{
		Player1Score=0;
		Player2Score=0;
	}
	
	//현재 볼을 소유하고 있는 플레이어를 변경한다.
	public static void setCatchingPlayer(int playerIdx, int catchStartTileIdx)
	{
		currentCatchPlayerIdx=playerIdx;
		currentCatchingTileIdx=catchStartTileIdx;
		ScoreSystem.catchStartTileIdx=catchStartTileIdx;
	}
	
	//그 누구도 볼을 소유하지 않아서, 스코어를 더하지 않음
	public static void missTheBall()
	{
		currentCatchPlayerIdx=-1;
		currentCatchingTileIdx=-1;
		ScoreSystem.catchStartTileIdx=-1;
	}
	
	//플레이어가 다음 타일에 있는지 검사하고, 스코어 시스템을 갱신한다.
	public static void playerOnTile(int currentTileIdx)
	{
		int nextTileIdx=(currentCatchingTileIdx+1)%GlobalDataManager.TILES_COUNT;
		if(nextTileIdx==currentTileIdx)
		{
			//다음 타일의 번호가 처음 밟은 타일의 번호와 일치
			if(nextTileIdx==catchStartTileIdx)
			{
				//현재 볼을 잡고 있는 플레이어의 스코어에 1을 더한다.
				addScore(currentCatchPlayerIdx);
			}
			
			currentCatchingTileIdx=nextTileIdx;
		}
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
