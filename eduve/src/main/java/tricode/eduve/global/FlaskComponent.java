package tricode.eduve.global;


import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


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

    public String extractTopic(String userMessage) {
        String flaskApiUrl = "http://localhost:5000/extractTopic";  // Flask API URL (로컬에서 Flask 실행 중이라면 localhost 사용)

        ResponseEntity<Map> response = restTemplate.postForEntity(flaskApiUrl, Map.of("message", userMessage), Map.class);
        return (String) response.getBody().get("topic");
    }

    public double calculateSimilarity(String lastTopic, String newTopic) {
        String flaskApiUrl = "http://localhost:5000/calculateSimilarity";  // Flask API URL (로컬에서 Flask 실행 중이라면 localhost 사용)

        ResponseEntity<Map> response = restTemplate.postForEntity(flaskApiUrl, Map.of("topic1", lastTopic, "topic2", newTopic), Map.class);
        return (double) response.getBody().get("similarity");
    }
}
