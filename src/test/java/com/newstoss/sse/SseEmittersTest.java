package com.newstoss.sse;

import com.newstoss.news.adapter.in.web.sse.SseEmitters;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@ActiveProfiles("test")
class SseEmittersTest {

    @Autowired
    private SseEmitters sseEmitters;

    private UUID testMemberId;

    @BeforeEach
    void setUp() {
        testMemberId = UUID.randomUUID();
    }

    @Test
    @Order(1)
    void emitter_추가_및_정상등록() {
        sseEmitters.add(testMemberId);
        assertThat(sseEmitters.getEmitterCount()).isEqualTo(1);
    }

    @Test
    @Order(3)
    void 모든_emitter에_sendAll_전송() {
        sseEmitters.add(testMemberId);
        sseEmitters.sendAll("테스트 메시지");
        assertThat(sseEmitters.getEmitterCount()).isEqualTo(2);
    }

    @Test
    @Order(2)
    void emitter_send중_IOException_발생시_제거됨() throws Exception {
        UUID memberId = UUID.randomUUID();
        sseEmitters.add(memberId); // 정상 등록 먼저

        // 예외 발생용 커스텀 emitter 생성
        SseEmitter brokenEmitter = new SseEmitter() {
            @Override
            public void send(SseEventBuilder builder) throws IOException {
                throw new IOException("Broken pipe");
            }
        };

        // 리플렉션으로 내부 Map 접근 및 emitter 강제 교체
        Field field = SseEmitters.class.getDeclaredField("emitters");
        field.setAccessible(true);
        @SuppressWarnings("unchecked")
        Map<UUID, SseEmitter> emitterMap = (Map<UUID, SseEmitter>) field.get(sseEmitters);
        emitterMap.put(memberId, brokenEmitter); // 덮어쓰기

        // 전송 및 제거 확인
        sseEmitters.sendAll("데이터");
        Thread.sleep(100); // async 제거 시간 확보

        assertThat(sseEmitters.getEmitterCount()).isEqualTo(1); // 제거됐는지 확인
    }

}

