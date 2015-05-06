package main.java;

public class RunJAVA {

	public static void main(String[] args) {

		RunJAVA forRun = new RunJAVA();
		forRun.RunMe();
	}

	private void RunMe() {
		TestingUserData testing = new TestingUserData();
		String a,b;
		String currFolder=System.getProperty("user.dir");
		System.out.println("currFolder=");
		a=currFolder.concat("\\TEST\\Test.java");//"F:/Lev_PRIVATE_data/JAVA_projects/REAL_PROJECT/Eclipse_JEE/eclipse/CROC/TEMP/Test.java";
		b=currFolder.concat("\\TEST\\Answer.java");//"F:/Lev_PRIVATE_data/JAVA_projects/REAL_PROJECT/Eclipse_JEE/eclipse/CROC/TEMP/Answer.java";
		
		if(testing.IsCorrect(a, b))
			System.out.println("YES");
		else
			System.out.println("NO");
		
	}

}
