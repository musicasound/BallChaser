package Input;

import org.lwjgl.input.Keyboard;


//���� ���ȳ� �ȴ��ȳ� ���¸� üũ�ϴ°��� �ƴ϶� ������ ���� �̺�Ʈ�� ó��
public class String_Input {
	private StringBuffer strBuf=new StringBuffer();
	private boolean isEnterKeyPressed=false;
	
	
	
	//���ӷ����� �ѷ������� Ű���� �̺�Ʈ�°��� üũ�ϰ� strBuf�� ���ڿ�����
	//����Ű�� �޾Ҵٸ� clean�ɶ����� update�����ʴ´�.
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
	
	//���� strbuf �����ϰ� clean 
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
