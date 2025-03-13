package tricode.eduve.global;


import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class FlaskComponent {

    private final RestTemplate restTemplate;

    public FlaskComponent() {
        this.restTemplate = new RestTemplate();
    }


    // 유사도 검색 flask API 호출
    public String findSimilarDocuments(String question) {
        String flaskApiUrl = "http://localhost:5000/search";  // Flask API URL (로컬에서 Flask 실행 중이라면 localhost 사용)

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 요청 바디 설정 (JSON 포맷)
        String requestBody = "{ \"query\": \"" + question + "\" }";

        // HTTP 요청 엔티티 생성
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // Flask API 호출 (POST 요청)
        ResponseEntity<String> response = restTemplate.exchange(flaskApiUrl, HttpMethod.POST, entity, String.class);

        // 응답 데이터 추출
        return response.getBody();
    }
}
