package Input;

import org.lwjgl.input.Keyboard;


//현재 눌렸냐 안눌렸냐 상태를 체크하는것이 아니라 눌리고 떼는 이벤트로 처리
public class String_Input {
	private StringBuffer strBuf=new StringBuffer();
	private boolean isEnterKeyPressed=false;
	
	
	
	//게임루프의 한루프에서 키보드 이벤트온것을 체크하고 strBuf에 문자열저장
	//엔터키를 받았다면 clean될때까지 update하지않는다.
	public void update() {
		while (Keyboard.next()) {
			
			if (isEnterKeyPressed == false) {
				// KeyDown
				if (Keyboard.getEventKeyState()) {
					char c = Keyboard.getEventCharacter();
					if (c == 13)// Enterkey
					{
						isEnterKeyPressed = true;
						break;
					}
					
					else if(64< c && c<127) {
						strBuf.append(c);
					}
					else {
						
					}
				}

				// KeyUp
				else {

				}
			}

		}

	}
	
	public  boolean IsEnterKeyPressed() {
		return isEnterKeyPressed;
	}
	
	//현재 strbuf 리턴하고 clean 
	public  String Pop() {
		String str=getString();
		cleanStrBuf();
		return str;
	}
	
	public  String getString() {
		return strBuf.toString();
	}
	
	public  void cleanStrBuf() {
		isEnterKeyPressed=false;
		strBuf=new StringBuffer();
	}

}
