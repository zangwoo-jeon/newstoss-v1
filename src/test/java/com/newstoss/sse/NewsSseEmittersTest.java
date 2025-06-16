package com.newstoss.sse;

import com.newstoss.news.adapter.in.web.sse.NewsSseEmitters;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
class NewsSseEmittersTest {

    @Autowired
    private NewsSseEmitters newsSseEmitters;


    @Test
    @Order(1)
    void emitter_추가_및_정상등록() {
        newsSseEmitters.add();
        assertThat(newsSseEmitters.getEmitterCount()).isEqualTo(1);
    }

    @Test
    @Order(2)
    void emitter_send중_IOException_발생시_제거됨() throws Exception {
        // 정상 emitter 추가
        newsSseEmitters.add();
        SseEmitter brokenEmitter = new SseEmitter() {
            @Override
            public void send(SseEventBuilder builder) throws IOException {
                throw new IOException("Broken pipe");
            }
        };

        // 내부 emitter 리스트에 접근해 고장난 emitter 추가
        Field field = NewsSseEmitters.class.getDeclaredField("emitters");
        field.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<SseEmitter> emitterList = (List<SseEmitter>) field.get(newsSseEmitters);

        emitterList.add(brokenEmitter); // 강제 삽입

        newsSseEmitters.sendAll("데이터");
        Thread.sleep(100); // async 제거 대기

        assertThat(newsSseEmitters.getEmitterCount()).isEqualTo(2); // 정상 emitter 1개만 남아야 함
    }
    @Test
    @Order(3)
    void 모든_emitter에_sendAll_전송() {
        newsSseEmitters.add(); // 새로운 emitter 등록

        newsSseEmitters.sendAll("테스트 메시지");

        // 전송 후, 정상 emitter는 그대로 유지
        assertThat(newsSseEmitters.getEmitterCount()).isEqualTo(3);
    }
}
