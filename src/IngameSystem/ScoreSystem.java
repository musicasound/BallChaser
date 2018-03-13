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
	
	//���� ���� �����ϰ� �ִ� �÷��̾ �����Ѵ�.
	public static void setCatchingPlayer(int playerIdx, int catchStartTileIdx)
	{
		currentCatchPlayerIdx=playerIdx;
		currentCatchingTileIdx=catchStartTileIdx;
		ScoreSystem.catchStartTileIdx=catchStartTileIdx;
	}
	
	//�� ������ ���� �������� �ʾƼ�, ���ھ ������ ����
	public static void missTheBall()
	{
		currentCatchPlayerIdx=-1;
		currentCatchingTileIdx=-1;
		ScoreSystem.catchStartTileIdx=-1;
	}
	
	//�÷��̾ ���� Ÿ�Ͽ� �ִ��� �˻��ϰ�, ���ھ� �ý����� �����Ѵ�.
	public static void playerOnTile(int currentTileIdx)
	{
		int nextTileIdx=(currentCatchingTileIdx+1)%GlobalDataManager.TILES_COUNT;
		if(nextTileIdx==currentTileIdx)
		{
			//���� Ÿ���� ��ȣ�� ó�� ���� Ÿ���� ��ȣ�� ��ġ
			if(nextTileIdx==catchStartTileIdx)
			{
				//���� ���� ��� �ִ� �÷��̾��� ���ھ 1�� ���Ѵ�.
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
