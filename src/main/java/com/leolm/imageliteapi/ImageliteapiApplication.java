package com.leolm.imageliteapi;

import com.leolm.imageliteapi.domain.entity.Image;
import com.leolm.imageliteapi.domain.enums.ImageExtension;
import com.leolm.imageliteapi.infra.repository.ImageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@Slf4j
public class ImageliteapiApplication {

	@Bean
	public CommandLineRunner commandLineRunner(ImageRepository imageRepository){
		return args -> {
			Image image  = new Image()
					.builder()
					.extension(ImageExtension.PNG)
					.name("myImage")
					.tags("teste")
					.size(1000L)
					.build();
			imageRepository.save(image);
			log.info("Imagem salva!");
		};
	}
	public static void main(String[] args) {
		SpringApplication.run(ImageliteapiApplication.class, args);
	}

}
