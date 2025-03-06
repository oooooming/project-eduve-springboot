package tricode.eduve.global;

import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ChatGptClient {

    private static final String API_KEY = "";  // OpenAI API 키를 입력
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    private final RestTemplate restTemplate;

    public ChatGptClient() {
        this.restTemplate = new RestTemplate();
    }

    public String getChatGptResponse(String question) {
        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);

        // 요청 바디 설정
        String requestBody = "{\n" +
                "  \"model\": \"gpt-3.5-turbo\",\n" +
                "  \"messages\": [\n" +
                "    {\"role\": \"user\", \"content\": \"" + question + "\"}\n" +
                "  ]\n" +
                "}";

        // HTTP 요청
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);

        // 응답 반환
        return response.getBody();
    }
}
