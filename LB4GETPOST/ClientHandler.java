package Lab4GETPOST;
public class ClientHandler implements Runnable {

    private final Socket socket;
	private StringBuilder diary;

    public ClientHandler(Socket socket) {
        this.socket = socket;
		diary = new StringBuilder();
    }
	
	public void handleRequest(Socket socket) {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));) {
            String headerLine = in.readLine();
            StringTokenizer tokenizer = new StringTokenizer(headerLine);
            String httpMethod = tokenizer.nextToken();
			switch(httpMethod):
				case "GET":
					sendResponse(socket, 200, diary.toString());
					break;
				case "POST":
					String newEntry = diaryPen.next();
					diary.append(newEntry).append("\n");
					sendResponse(socket, 200, "Line added");
					break;
				case default:
					sendResponse(socket, 405, "Not Supported");
					break;
				
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public void sendResponse(Socket socket, 
        int statusCode, String responseString) {
        String statusLine;
        String serverHeader = "Server: WebServer\r\n";
        String contentTypeHeader = "Content-Type: text/html\r\n";
        try (DataOutputStream out = 
                new DataOutputStream(socket.getOutputStream());) {
            if (statusCode == 200) {
				statusLine = "HTTP/1.0 200 OK" + "\r\n";
				String contentLengthHeader = "Content-Length: " 
					+ responseString.length() + "\r\n";

				out.writeBytes(statusLine);
				out.writeBytes(serverHeader);
				out.writeBytes(contentTypeHeader);
				out.writeBytes(contentLengthHeader);
				out.writeBytes("\r\n");
				out.writeBytes(responseString);
			} else if (statusCode == 405) {
				statusLine = "HTTP/1.0 405 Method Not Allowed" + "\r\n";
				out.writeBytes(statusLine);
				out.writeBytes("\r\n");
			} else {
				statusLine = "HTTP/1.0 404 Not Found" + "\r\n";
				out.writeBytes(statusLine);
				out.writeBytes("\r\n");
			}
        } catch (IOException ex) {
            // Handle exception
        }
		finally {
			out.close();
    }
	
    @Override
    public void run() {
        System.out.println("\nClientHandler Started for " + 
            this.socket);
        handleRequest(this.socket);
        System.out.println("ClientHandler Terminated for " 
            + this.socket + "\n");
    }

}