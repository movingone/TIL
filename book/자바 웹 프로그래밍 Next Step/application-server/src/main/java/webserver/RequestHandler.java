package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpRequestUtils;

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

            if (line == null) return;
            // 아무것도 아닌 null 이 왜 들어올까? 찾아보기



//            while (!"".equals(line)) {
//                log.debug("header : {}", line);
//                line = br.readLine();
//            };

            DataOutputStream dos = new DataOutputStream(out);

            byte[] body = Files.readAllBytes(new File("C:\\Users\\ai060\\OneDrive\\바탕 화면\\TIL\\book\\자바 웹 프로그래밍 Next Step\\jwp-basic\\webapp\\index.html").toPath());
            // 처음 상대경로로 할려 하니 html 폴더가 저 밖에 있어 제대로 안되었다. 그래서 절대경로 썼다(
            response200Header(dos, body.length);
            responseBody(dos, body);
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
