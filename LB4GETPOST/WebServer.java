package Lab4GETPOST;
public class WebServer {
	private StringBuilder fullDiary = new StringBuilder();
    public WebServer() {
        System.out.println("Webserver Started");
        try (ServerSocket serverSocket = new ServerSocket(80)) {
            while (true) {
                System.out.println("Http server initiated");
                Socket remote = serverSocket.accept();
                System.out.println("Connection made");
                new Thread(new ClientHandler(remote)).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
	
	public String writeInDiary(String entry) {
		fullDiary.append(entry);
	}
	
	public void readDiary() {
		return fullDiary;
	}

    public static void main(String args[]) {
        new WebServer();
    }
}