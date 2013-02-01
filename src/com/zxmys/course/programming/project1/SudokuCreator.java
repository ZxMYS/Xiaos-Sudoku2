package com.zxmys.course.programming.project1;

/**
 * Ԥ��������Ϸ��
 * 
 * @author Zx.MYS
 * @version 1.0 (2009.11.25)
 */
public class SudokuCreator {

	/**
	 * ������ʵ����
	 */
	private SudokuCreator() {
	}

	/**
	 * Ԥ�����Ϸ���ַ��������(0,0)��(9,9)���������Ҵ��ϵ��µ������������ݣ�0��ʾ�ո� 
	 */
	private static final String[] games = {
			//"389215674421763985657489321715328469843956217296147853564831792938572146172694530",
			//"000726495076145832245398761354219678792683514861574923487932156100457289520861347",
			"008026000900100000005390060300200070702000504060004003080032100000007009500860300",
			"209000060010463020004005700000008039000579000980300000002600400070984050090000806",
			"500000940000804501410006028000068000170000083000740000950400036604102000027000004",
			"060000300001504000004800600800000064020080000600090070000703086000020400000609013",
			"007040960000002805800900030090007002610000059700200080070005008204700000063090100",
			"308040000000000000000907060103000000000500790400000000090806000000000003000000001"
			};

	/*
	 * public static String createRandomGame(){ //TODO: �����������Ϸ return ""; }
	 */

	/**
	 * �������Ԥ�����Ϸ�е�һ��
	 * 
	 * @return ��String���ͱ������Ϸ
	 */
	public static String getStoredGame() {
		int ID = (int) (Math.random() * games.length);
		return getStoredGame(ID);
	}

	/**
	 * ����ָ��ID��Ԥ����Ϸ
	 * 
	 * @param ID
	 * @return ��String���ͱ������Ϸ
	 */
	public static String getStoredGame(int ID) {
		return games[ID];
	}
	
	/**
	 * ���ɾ���ָ���ո�������������
	 * 
	 * @param numberOfBlank �ո�����
	 * @return ������
	 */
	public static SudokuBoard getRandomGame(int numberOfBlank){
		if(numberOfBlank>81||numberOfBlank<=0)
			throw new IllegalArgumentException();
		StringBuilder line=new StringBuilder("123456789");
		
		while(true){
			for(int i=0;i<9;i++){
				int j=(int)(Math.random()*9);
				char tmp=line.charAt(i);
				line.setCharAt(i, line.charAt(j)); 
				line.setCharAt(j, tmp); 
			}
			String lineLeft=line.toString();
			for(int i=1;i<9;i++){
				int j=(int)(Math.random()*8)+1;
				char tmp=line.charAt(i);
				line.setCharAt(i, line.charAt(j)); 
				line.setCharAt(j, tmp); 
			}
			String lineTop=line.toString();

			SudokuBoard board=new SudokuBoard();
			try {
				for(int i=0;i<9;i++)
					board.setVal(i+1, 1, lineLeft.charAt(i)-'0');
				for(int i=1;i<9;i++)
					board.setVal(1, i+1, lineTop.charAt(i)-'0');
			} catch (IllegalArgumentException e) {
				continue;
			}
			SudokuSolver ss = new SudokuSolver();
			ss.setBoard(board);
			if (ss.solveBoard()) {
				while(numberOfBlank>0){
					int x=(int)(Math.random()*9)+1,y=(int)(Math.random()*9)+1;
					if(board.getVal(x, y)!=0){
						board.setVal(x,y,0);
						numberOfBlank--;
					}
				}
				return board;
			}
		}
	}
}
