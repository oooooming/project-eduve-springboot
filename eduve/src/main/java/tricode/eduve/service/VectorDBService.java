package tricode.eduve.service;

import io.milvus.v2.service.collection.request.LoadCollectionReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import io.milvus.v2.client.ConnectConfig;
import io.milvus.v2.client.MilvusClientV2;
import io.milvus.v2.service.collection.request.GetLoadStateReq;
import io.milvus.v2.service.collection.request.CreateCollectionReq;
import io.milvus.v2.common.DataType;
import io.milvus.v2.service.collection.request.AddFieldReq;

@Service
@Slf4j
public class VectorDBService {

    private final MilvusClientV2 client;
    private final String CLUSTER_ENDPOINT = "http://localhost:19530"; //Milvus 서버 엔트포인트(로컬)

    public VectorDBService(){
        ConnectConfig connectConfig = ConnectConfig.builder()
                .uri(CLUSTER_ENDPOINT)
                .build();

        // Milvus 통신 클라이언트 객체
        this.client = new MilvusClientV2(connectConfig);
    }

    public void createCollection(){
        // 컬렉션 스키마 생성
        CreateCollectionReq.CollectionSchema schema = client.createSchema();
        // Primary Key 필드
        schema.addField(AddFieldReq.builder()
                .fieldName("id")
                .dataType(DataType.Int64)
                .isPrimaryKey(true)
                .autoID(true)
                .build());
        // 벡터데이터 필드
        schema.addField(AddFieldReq.builder()
                .fieldName("vector")
                .dataType(DataType.FloatVector)
                .dimension(1536)  // openAI text-embedding-ada-002의 차원 수
                .build());
        // fileId 필드
        schema.addField(AddFieldReq.builder()
                .fieldName("file_id")
                .dataType(DataType.Int64)
                .build());

        // 컬렌션 생성
        CreateCollectionReq createCollectionReq = CreateCollectionReq.builder()
                .collectionName("eduve_collection") // 컬렉션 이름
                .collectionSchema(schema)
                .build();

        client.createCollection(createCollectionReq);

        // Thread.sleep(5000); 컬렌션 생성 후 약간 대기(option)

        // 컬렉션 로드
        client.loadCollection(LoadCollectionReq.builder()
                .collectionName("eduve_collection")
                .build());
        //로드 상태 확인
        GetLoadStateReq quickSetupLoadStateReq = GetLoadStateReq.builder()
                .collectionName("eduve_collection")
                .build();
        // true: 로드완료 / false: 실패
        Boolean res = client.getLoadState(quickSetupLoadStateReq);
        System.out.println(res);
    }
}
