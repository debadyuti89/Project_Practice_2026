public class Brackets {

	public static void main(String[] args) {
		// Check whether the string contains valid parentheses or not. check left parentheses is closed properly with 
		// right parentheses, also count will be even for combo parentheses.
		// Valid Test Cases
		// (){}[]
		// [({})([{}]){()[]()}([])]
		// {()}(()())[()]
		// (()()())(())
		System.out.println(isValid("[()(({})([])){()}]"));
		System.out.println(isValid("([{}])"));
		//testData();
	}

	private static boolean isValid(String s) {
		
		return true;
	}
	
	private static void test(String str, boolean expected) {
		boolean isValid = isValid(str);
		String status = isValid == expected ? "SUCCESS" : "FAIL";
		System.out.println(str +" - " + isValid + " - "+ status);
	}
	
	private static void testData() {
		test("[{)", false);
		test("({}]", false);
		test("())({]}[", false);
		test(")(}{][", false);
		test(")()(", false);
		test("()(()()))(()", false);
		test("()()((()))))", false);
		
		test("[{()}]", true);
		test("[{(({})([]))}]", true);
		test("[{()()}](()())({})([])(()())()", true);
		test("((({})))[]", true);
		test("()((()))()[{()({})}]", true);
		test("()((()()))()", true);
		test("[{()()((()()))[({}){()}([{()}])]}]", true);
		
	}
	private static boolean operation(String s) {
		int sp = 0;
		char[] stack = new char[s.length()];
		
		for(char c : s.toCharArray()) {
			if(c == '(') {
				stack[sp] = '(';
				sp++;
			}else {
				if(sp == 0) 
					return false;
				if(stack[sp-1] == '(')
					sp--;
				else
					return false;
			}
		}
		if(sp != 0) return false;
		return true;
	}
}
