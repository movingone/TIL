package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import model.Database;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;
import util.IOUtils;

public class RequestHandler extends Thread {
	private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

	private Socket connection;
	// 클라이언트와 서버의 연결

	public RequestHandler(Socket connectionSocket) {
		this.connection = connectionSocket;
	}

	public void run() {
		log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
				connection.getPort());

		try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
			// jdk 7부터 추가된 문법으로 '특정 자원을 활용해 사용하고 close를 자동으로 해줌'
			// InputStream과 OutputStream이 close를 구현하고 있다 (안에 한번 확인해볼것)
			// TODO 클라이언트가 서버로 전송되는 (서버 입장에서 받는, 읽을 수 있는 데이터), 클라이언트한테 전송해야 한는 데이터

			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = br.readLine();

			String url = HttpRequestUtils.getUrl(line);

			Map<String, String> headers = new HashMap<String, String>();

			while (!"".equals(line)) {
				log.debug("header : {}", line);
				line = br.readLine();
				String header_tokens[] = line.split(": ");
				if (header_tokens.length == 2)
					headers.put(header_tokens[0], header_tokens[1]);
			};

			log.debug("content-Length : {}", headers.get("Content-Length"));

			if (url.startsWith("/create")) {
				// GET 방식
				// String queryString = url.substring(index + 1);
				String requestBody = IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length")));
				log.debug("Request Body : {}", requestBody);
				Map<String, String> params = HttpRequestUtils.parseQueryString(requestBody);

				// POST방식일때 회원가입한 정보를 Body에서 읽어 Map으로 담은후 User객체로 값을 넘김
				// 이러한 방식으로 구현하게되면 새로고침할때마다 기존값이 남아있어서, 계속해서 회원가입을 하게됨(중복저장됨)

				User user = new User(params.get("userId"), params.get("password"), params.get("name"), params.get("email"));
				log.debug("User : {}", user);
				url = "/index.html";

				// method를 get에서 post로 바꾸게 되면 header의 시작이 GET에서 POST로 바뀌게 되고
				// GET과 POST구분을 잘 해야 코드를 잘 짤수 있을 것이다
				Database.addUser(user);

				DataOutputStream dos = new DataOutputStream(out);
				response302Header(dos);

			} else if (url.equals("/login")) {
				String requestBody = IOUtils.readData(br, Integer.parseInt(headers.get("Content-Length")));
				log.debug("Request Body : {}", requestBody);
				Map<String, String> params = HttpRequestUtils.parseQueryString(requestBody);

				// POST방식일때 회원가입한 정보를 Body에서 읽어 Map으로 담은후 User객체로 값을 넘김
				// 이러한 방식으로 구현하게되면 새로고침할때마다 기존값이 남아있어서, 계속해서 회원가입을 하게됨(중복저장됨)

				log.debug("userId : {}, password : {}", params.get("userId"), params.get("password"));

				User user = Database.getUser(params.get("userId"));
				if (user == null) {
					log.debug("User Not Found");
					DataOutputStream dos = new DataOutputStream(out);
					response302Header(dos);
				}


				else if (user.getPassword().equals(params.get("password"))) {
					log.debug("login success");
					DataOutputStream dos = new DataOutputStream(out);
					response_CookieHeader(dos, "logined=true");
				} else {
					log.debug("Password Miss");
					DataOutputStream dos = new DataOutputStream(out);
					response302Header(dos);
				}

			} else if (url.endsWith(".css")) {
				DataOutputStream dos = new DataOutputStream(out);
				byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
				response_CSS_Header(dos);
				responseBody(dos, body);
			} else {
				DataOutputStream dos = new DataOutputStream(out);
				byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
				response200Header(dos, body.length);
				responseBody(dos, body);
			}

			if (line == null) return;
			// 아무것도 아닌 null 이 왜 들어올까? 찾아보기


		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}


	private void response_CSS_Header(DataOutputStream dos) {
		try {
			dos.writeBytes("HTTP/1.1 302 Found \r\n");
			dos.writeBytes("Content-Type: text/css;charset=utf-8\r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void response_CookieHeader(DataOutputStream dos, String cookie) {
		try {
			dos.writeBytes("HTTP/1.1 302 Found \r\n");
			dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
			dos.writeBytes("Set-Cookie: " + cookie + "\r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void response302Header(DataOutputStream dos) {
		try {
			dos.writeBytes("HTTP/1.1 302 Found \r\n");
			dos.writeBytes("Location: /index.html\r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
		try {
			dos.writeBytes("HTTP/1.1 200 OK \r\n");
			dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
			dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void responseBody(DataOutputStream dos, byte[] body) {
		try {
			dos.write(body, 0, body.length);
			dos.flush();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
