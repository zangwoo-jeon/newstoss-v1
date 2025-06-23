//package com.newstoss.stock.application.V2;
//
//import com.newstoss.stock.adapter.outbound.redis.KisApiStreamProducer;
//import com.newstoss.stock.application.sse.EmitterRepository;
//import com.newstoss.stock.entity.FxEncoder;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class FxToRedisService {
//
//    private final KisApiStreamProducer producer;
//    private final EmitterRepository emitterRepository;
//    private final FxEncoder encoder;
//
//    @Scheduled(fixedDelay = 30_000)
//    public void enqueueFxIfClientsConnected() {
//        if (emitterRepository.findAllEmitter().isEmpty()) {
//            log.info("fx Clients : No emitters found");
//            return;
//        }
//        List<List<String>> allFx = encoder.getAllFx();
//        for (List<String> fx : allFx) {
//            String type = fx.get(0);
//            String code = fx.get(1);
//            producer.sendFxRequest(type, code);
//        }
//    }
//}
