package KU.GraduationProject.BasicServer.service;

import KU.GraduationProject.BasicServer.domain.entity.project.imageFile;
import KU.GraduationProject.BasicServer.domain.repository.uploadImageFileInfoRepository;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.TimeUnit;

@Service
public class imageProcessingService {

    @Value("${file.upload-dir}")
    private String directoryPath;

    @Autowired
    private uploadImageFileInfoRepository imageFileRepository;

    private final String imageProcessingServerUrl = "http://localhost:12345";

    public String getCoordinate(long imageFileId) {

        TcpClient tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 600)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(600, TimeUnit.SECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(600, TimeUnit.SECONDS));
                });

        String fileName = imageFileRepository.findByImageFileId(imageFileId).get().getFileName();

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("url", directoryPath + "/" + fileName);

        String response = WebClient.create()
                .post()
                .uri(imageProcessingServerUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();

        return response;


    }
}
