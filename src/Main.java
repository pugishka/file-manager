import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;

public class Main {
	
//	public static void main(String[] args) throws IOException, 
//	InterruptedException {	
//		
//		// check if Windows
//		boolean isWindows = System.getProperty("os.name")
//				  .toLowerCase().startsWith("windows");
//		
//		// directory to open
//		String home = System.getProperty("user.home");
//		
//		// execute
//		Process process = null;
//		String openHome = null;
//		if (isWindows) {
//			openHome = String.format("cmd.exe /c dir %s", home);
//		} else {
//			openHome = String.format("sh -c ls %s", home);
//		}
//		process = Runtime.getRuntime().exec(openHome);
//		
//		// print output
//		
//		//(inputStream, outputStream)
//		StreamGobbler streamGobbler = 
//		  new StreamGobbler(process.getInputStream(), System.out::println);
//		
//		// new thread
//		Executors.newSingleThreadExecutor().submit(streamGobbler);
//		int exitCode = 0;
//		exitCode = process.waitFor();
//		assert exitCode == 0;
//	}
	
	public static void main(String[] args) {
		
		File home = new File("C:\\Users\\charo\\Documents\\Documents\\UDEM");
		File[] contents = home.listFiles();
		for (File item : contents) {
			if (item.isFile()) {
				System.out.format("File : %s\n", item.getName());
			} else if (item.isDirectory()) {
				System.out.format("Dir : %s\n", item.getName());
			}
		}
	    
		
	}

	
}
