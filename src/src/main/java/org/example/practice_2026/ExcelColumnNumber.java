public class ExcelColumnNumber {

	public static void main(String[] args) {
		int columnNumber = 52;
		System.out.println(returnColumn_optimized(columnNumber));
		System.out.println(returnColumn(columnNumber));

	}

	private static String returnColumn_optimized(int colNo) {
		String colName = "";
		while (colNo > 0) {
			char ch = (char) ('A' + (colNo - 1) % 26);
			colName = ch + colName;
			colNo = ( colNo - 1 ) / 26;
		}
		return colName;
	}
	
	private static String returnColumn(int colNo) {
		StringBuilder colName = new StringBuilder();
		while (colNo > 0) {			
			colName.append((char) ('A' + (colNo - 1) % 26));
			colNo = ((colNo - 1) / 26);
		}
		return colName.reverse().toString();
	}
	
/*	private static String returnColumn(int colNo) {
		StringBuilder colName = new StringBuilder();
		while (colNo > 0) {
			int rem = colNo % 26;

			if (rem == 0) {
				colName.append("Z");
				colNo = (colNo / 26) - 1; // 26 = Z,
			} else {
				colName.append((char) ((rem - 1) + 'A'));
				colNo = (colNo / 26);
			}
		}
		return colName.reverse().toString();
	}
*/	
	

}















/*

*/