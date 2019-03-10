//Rebecka Skareng, resk5800
//Ossian Gustafsson, osgu7991 

import java.util.HashSet;
import java.util.LinkedList;

public class ChessBoard {

	private LinkedList<Queen> queens = new LinkedList<>();
	private HashSet<Integer> columnData = new HashSet<>();
	private HashSet<Integer> rowData = new HashSet<>();
	private HashSet<Integer> diagonalData = new HashSet<>();
	private HashSet<Integer> otherDiagonalData = new HashSet<>();
	private int size;
	
	public ChessBoard(int sizeOfProblem) {
		size = sizeOfProblem;
	}
	
	/**
	 * När en drottning kan placeras så anropas denna metod. Parametrarna är 
	 * rad och kolumn för drottningens plats på spelplanen. 
	 * Sparar data för vilken kolumn, rad och diagonal som drottningen upptar. 
	 * 
	 * Tillexempel på hur datat för diagonalerna generaliseras: 
	 * Drottning1 på rad=2 och kolumn=4, diagonal ett=-2, diagonal två=6
	 * Drottning2 på rad=0 och kolumn=2, diagonal ett=-2, diagonal två=2
	 * 
	 * Utefter detta resultat kan vi dra slutsats att två drottningar på 
	 * samma diagonal kan fås ut rad-kolumnen eller rad+kolumn. 
	 * 
	 * 
	 * @param row heltal som motsvarar en rad i spelbrädet
	 * @param col heltal som motsvarar en kolumn i spelbrädet
	 */
	
	private void insertQueen(int row, int col) {
		queens.push(new Queen(row,col));
		columnData.add(col);
		rowData.add(row);
		diagonalData.add(row - col);
		otherDiagonalData.add(col + row);
	}
	
	private void removeQueen(int row, int col) {
		queens.pop();
		columnData.remove(col);
		rowData.remove(row);
		diagonalData.remove(row - col);
		otherDiagonalData.remove(col + row);
	}
	
	boolean isPlacementSafe(int row, int col) {
		if(rowData.contains(row) || columnData.contains(col) || diagonalData.contains(row-col) || otherDiagonalData.contains(row+col)) {
			
			return false;
		}
		return true;
	}
	
	/**
	 * Rekursiv privat metod som tar emot den kolumnen som ska kollas. 
	 * Ifall parametern col blir åtta så har en lösning hittats. 
	 * För varje rekursivt anrop som resulterar i att en drottning
	 * inte kan placeras så kommer tidigare drottning som placerades i det 
	 * förra anropet att försöka placeras om.
	 * @param col ett heltal som representeras en viss kolumn, bör vara noll vid start.
	 * @return en boolean, sann för lösning som hittats, falsk för lösning som inte hittats.
	 */
	
	private boolean findSolution(int col) {
		if(col==size) {
			return true;
		}
		
		for(int row=0; row<size; row++) {
			if(isPlacementSafe(row, col)) {
				insertQueen(row, col);
				if(findSolution(col+1)) {
					return true;
				}
				removeQueen(row,col);
			}
		}
		
		return false;
	}
	/**
	 * Exempel på en representation av ett resultat.
	 * Placerar alla drottningar i en matris som ettor på det index som 
	 * motsvarar deras koordinater. Skriver även ut matrisen. 
	 */
	public void getSolution() {
		if(findSolution(0)) {
			
			
			int[][] boardRep = new int[size][size];
			for(Queen q : queens) {
				boardRep[q.row][q.col] = 1;
			}
			
			for(int row=0; row<size; row++) {
				for(int col=0; col<size; col++) {
					System.out.print(boardRep[row][col] + " ");
				}
				System.out.println();
			}
		}
	}

	public static void main(String[] args) {
		ChessBoard c = new ChessBoard(8);
		c.getSolution();
	}
	class Queen {
		private int row;
		private int col;
		
		public Queen(int x, int y){
			this.row = x;
			this.col = y;
		}

		public String toString() {
			return row + " " + col;
		} 
	}
}

