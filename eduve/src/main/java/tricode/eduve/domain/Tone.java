package tricode.eduve.domain;

public enum Tone {
    FORMAL,
    CASUAL,
    FRIENDLY,
    WITTY;


    // 톤에 맞는 프롬프트 문구를 반환하는 메서드
    public String getPromptInstruction() {
        switch (this) {
            case FORMAL:
                return "너는 공식적인 상황에서 상대방에게 예의를 갖춰 답변하는 스타일로 응답해야 해. " +
                        "모든 문장은 정중하고, 공손한 표현을 사용하며, 지나치게 캐주얼하거나 비격식적인 표현은 피해야 해.";
            case CASUAL:
                return "너는 편안하고 비공식적인 상황에서 상대방에게 친구처럼 자유롭게 답변하는 스타일로 응답해야 해. " +
                        "문장이나 표현은 친구와 이야기하는 듯한 자연스러운 톤을 사용하고, 지나치게 형식적인 문장은 피하도록 해.";
            case FRIENDLY:
                return "너는 상대방에게 친근하고 따뜻한 느낌을 주는 스타일로 응답해야 해. " +
                        "친구처럼 편안하게 다가가며, 부드럽고 긍정적인 어조로 대답하고, 상대방을 배려하는 표현을 사용하는 것이 중요해.";
            case WITTY:
                return "너는 유머와 재치를 담아 상대방에게 답변하는 스타일로 응답해야 해. " +
                        "답변에서 유머를 추가하거나 상황에 맞는 재치 있는 표현을 사용하여 대화를 즐겁게 만들어야 해. " +
                        "하지만 과도하게 가벼운 톤을 피하고, 적절한 유머를 사용해야 해.";
            default:
                return "정확한 톤을 알 수 없습니다.";
        }
    }
}
