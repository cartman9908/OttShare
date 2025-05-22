package seohan.ottshare.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seohan.ottshare.dto.message.MessageRequest;
import seohan.ottshare.dto.message.MessageResponse;
import seohan.ottshare.entity.Message;
import seohan.ottshare.entity.OttShareRoom;
import seohan.ottshare.entity.SharingUser;
import seohan.ottshare.exception.OttShareRoomNotFoundException;
import seohan.ottshare.exception.SharingUserNotFoundException;
import seohan.ottshare.repository.MessageRepository;
import seohan.ottshare.repository.OttShareRoomRepository;
import seohan.ottshare.repository.SharingUserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class RabbitMqService {

    private final RabbitTemplate rabbitTemplate;
    private final SimpMessagingTemplate messagingTemplate;

    private final MessageRepository messageRepository;
    private final SharingUserRepository sharingUserRepository;
    private final OttShareRoomRepository ottShareRoomRepository;

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Transactional
    public MessageResponse createMessage(MessageRequest dto) {
        SharingUser sharingUser = sharingUserRepository.findById(dto.getUserId())
                .orElseThrow(() -> new SharingUserNotFoundException(dto.getUserId()));

        OttShareRoom ottShareRoom = ottShareRoomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new OttShareRoomNotFoundException(dto.getRoomId()));

        return new MessageResponse(ottShareRoom, sharingUser, dto.getMessage());
    }

    @Transactional
    public void sendMessage(MessageResponse dto) {

        Message message = Message.from(dto);
        messageRepository.save(message);

        routingKey = "room." + dto.getOttShareRoom().getId();
        this.rabbitTemplate.convertAndSend(exchangeName, routingKey, dto);
        log.info("message send : {}", dto.getMessage());
    }

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void receiveMessage(MessageResponse dto) {
        log.info("Received message : {}", dto.toString());

        String topic = "/topic/room/" + dto.getOttShareRoom().getId();
        messagingTemplate.convertAndSend(topic, dto);
    }
}