package tricode.eduve.global;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;


@RequiredArgsConstructor
@Configuration
public class ChatGptClient {

    @Value("${chatgpt.api-key}")
    private String API_KEY;


    RestTemplate restTemplate = new RestTemplate();

/*
    public String getChatGptResponse(String question, String similarDocuments) {
        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);

        String systemMessage = "너는 학생들에게 모르는 부분을 친절하게 설명해주는 학습 보조 챗봇이야. "
                + "질문에 답할 때는 반드시 제공된 '관련 문서'를 근거로 설명해야 해. "
                + "관련 문서에 없는 내용은 절대 추측하거나 지어내지 마. "
                + "관련 문서에 답이 없으면 '관련 문서에 해당 내용이 없습니다.'라고 정직하게 답변해. "
                + "다음은 관련 문서야: " + similarDocuments;

        // 요청 바디 생성
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "gpt-3.5-turbo");

        JSONArray messages = new JSONArray();
        messages.put(new JSONObject().put("role", "system").put("content", systemMessage));
        messages.put(new JSONObject().put("role", "user").put("content", question));

        requestBody.put("messages", messages);

        // HTTP 요청
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);

        // 응답 반환
        return response.getBody();
    }



    public List<Map<String, Object>> prompt(String question, String similarDocuments) {
        log.debug("[+] 프롬프트를 수행합니다.");

        // [STEP1] 토큰 정보가 포함된 Header를 가져옵니다.
        HttpHeaders headers = chatGPTConfig.httpHeaders();

        String requestBody = "";
        ObjectMapper om = new ObjectMapper();

        String prompt = "너는 학생들에게 모르는 부분을 친절하게 설명해주는 학습 보조 챗봇이야. "
                + "질문에 답할 때는 반드시 제공된 '관련 문서'를 근거로 설명해야 해. "
                + "관련 문서에 없는 내용은 절대 추측하거나 지어내지 마. "
                + "관련 문서에 답이 없으면 '관련 문서에 해당 내용이 없습니다.'라고 정직하게 답변해. "
                + "다음은 관련 문서야: " + similarDocuments;

        // [STEP3] properties의 model을 가져와서 객체에 추가합니다.
        chatGPTDto dto = chatGPTDto.builder()
                .model("gpt-4o")
                .prompt(prompt)
                .temperature(0.8f) //말하기 친절함?을 나타내는 온도인듯?
                .build();

        try {
            // [STEP4] Object -> String 직렬화를 구성합니다.
            requestBody = om.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // [STEP5] 통신을 위한 RestTemplate을 구성합니다.
        HttpEntity requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity response = chatGPTConfig.restTemplate()
                .exchange(
                        "https://api.openai.com/v1/completions",
                        HttpMethod.POST,
                        requestEntity,
                        String.class);

        String responseBody = (String) response.getBody();
        if (responseBody == null) {
            throw new RuntimeException("응답 본문이 비어 있습니다.");
        }

        try {
            // [STEP6] String -> HashMap 역직렬화를 구성합니다.
            List<Map<String, Object>> result = om.readValue(responseBody, new TypeReference<List<Map<String, Object>>>() {});
            return result;

        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 역직렬화 실패", e);
        }
    }

 */




    public ResponseEntity<String> chat(String question, String similarDocuments) {

        HttpHeaders headers = new HttpHeaders(); // HTTP 헤더 생성
        headers.setContentType(MediaType.APPLICATION_JSON); // 요청 본문 타입 설정
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON)); // 수신할 응답 타입 설정
        headers.set("Authorization", "Bearer " + API_KEY); // 인증 헤더에 API 키 추가

        JSONObject messageSystem = new JSONObject(); // 시스템 메시지 JSON 객체 생성
        messageSystem.put("role", "system");  // 역할 설정
        String prompt = "너는 학생들에게 모르는 부분을 친절하게 설명해주는 학습 보조 챗봇이야. "
                + "질문에 답할 때는 반드시 제공된 '관련 문서'를 근거로 설명해야 해. "
                + "관련 문서에 없는 내용은 절대 추측하거나 지어내지 마. "
                + "관련 문서에 답이 없으면 '관련 문서에 해당 내용이 없습니다.'라고 정직하게 답변해. "
                + "다음은 관련 문서야: " + similarDocuments;
        messageSystem.put("content", prompt); // 시스템 메시지 추가

        JSONObject messageUser = new JSONObject(); // 사용자 메시지 JSON 객체 생성
        messageUser.put("role", "user"); // 역할 설정
        messageUser.put("content", question); // 사용자 메시지 추가

        JSONObject requestBody = new JSONObject(); // 요청 본문을 위한 JSON 객체 생성
        requestBody.put("model", "gpt-4o-2024-05-13"); // 사용할 모델 설정
        requestBody.put("messages", new JSONArray(Arrays.asList(messageSystem, messageUser))); // 메시지 배열 추가

        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers); // HTTP 요청 엔티티 생성

        String apiEndpoint = "https://api.openai.com/v1/chat/completions"; // API 엔드포인트 설정
        try {
            // REST API 호출을 통해 응답 받기
            ResponseEntity<String> response = restTemplate.postForEntity(apiEndpoint, request, String.class);

            // 응답 상태 코드 확인
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response;  // 성공적인 응답 반환
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("api 호출 중 오류 발생!"); // 오류 메시지 반환
            }
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("api 호출 중 예외 발생: " + e.getMessage()); // 예외 메시지 반환
        }
    }


    public String analyzeLikedMessages(List<String> likedMessages) {
        // 사용자 선호 스타일 분석을 위한 단일 프롬프트 생성
        String prompt = "다음 메시지들에 기반하여 사용자의 선호 스타일을 분석해 주세요: \n"
                + String.join("\n", likedMessages);

        // HTTP 요청을 위한 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", "Bearer " + API_KEY);

        // 사용자 메시지 설정
        JSONObject messageUser = new JSONObject();
        messageUser.put("role", "user");
        messageUser.put("content", prompt);  // 단순히 사용자 메시지로 프롬프트 사용

        // 요청 본문 생성
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "gpt-4o-2024-05-13");
        requestBody.put("messages", new JSONArray(Arrays.asList(messageUser)));

        // HTTP 요청 엔티티 생성
        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);

        String apiEndpoint = "https://api.openai.com/v1/chat/completions"; // API 엔드포인트

        try {
            // API 호출
            ResponseEntity<String> response = restTemplate.postForEntity(apiEndpoint, request, String.class);

            // 응답 확인
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody();  // 응답 내용 반환
            } else {
                throw new RuntimeException("API 호출 중 오류 발생!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("API 호출 중 예외 발생: " + e.getMessage());
        }
    }

}
