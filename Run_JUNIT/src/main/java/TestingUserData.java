package main.java;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.FileUtils;
import org.gradle.tooling.GradleConnector;
import org.gradle.tooling.ProjectConnection;

public class TestingUserData {

	private static final String TEMP_FILES_PATH = System.getProperty("user.dir").concat("\\TEST");

	private ProjectConnection connection;
	private ByteArrayOutputStream errorLog = new ByteArrayOutputStream();

	public boolean IsCorrect(String junitFilePath, String userAnswerFilePath) {

		deleteTempData("/src");
		deleteTempData("/.gradle");
		deleteTempData("/build");

		copyFile(junitFilePath, "/src/test/java/org/gradle/example/simple",
				"TestPerson.java");
		copyFile(userAnswerFilePath,
				"/src/main/java/org/gradle/example/simple", "Person.java");
		try {
			connectToGradle();
			runTest();
		} catch (Exception e) {

		} finally {
			return checkLog();
		}

	}

	private void deleteTempData(String str) {
		(new FileUtils()).deleteQuietly(new File(TEMP_FILES_PATH.concat(str)));

	}

	private void copyFile(String parentFolder, String distFolder,
			String fileName) {

		String distFolderPath = TEMP_FILES_PATH.concat(distFolder);
		new File(distFolderPath).mkdirs();

		FileOutputStream output = null;
		FileInputStream input = null;
		try {
			output = new FileOutputStream(new File(distFolderPath.concat("/"
					+ fileName)));
			input = new FileInputStream(new File(parentFolder));

			byte[] transfer = new byte[1];
			int go;
			while ((go = input.read(transfer)) > 0) {
				output.write(transfer);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				input.close();
				output.close();
			} catch (IOException e) {

				e.printStackTrace();
			}

		}

	}

	private boolean checkLog() {
		if (errorLog.size() != 0) {
			System.out.println("-----------ERROR:" + errorLog.toString());
			return false;
		}
		return true;
	}

	private void connectToGradle() {
		connection = GradleConnector.newConnector()
				.forProjectDirectory(new File(TEMP_FILES_PATH)).connect();

	}

	private void runTest() {
		try {
			connection.newBuild().forTasks("test")
					.setStandardOutput(System.out).setStandardError(errorLog)
					.run();
		} finally {
			connection.close();
			connection = null;
		}
	}

}
