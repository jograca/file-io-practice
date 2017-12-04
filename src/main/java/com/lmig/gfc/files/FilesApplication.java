package com.lmig.gfc.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class FilesApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(FilesApplication.class, args);

		Person person = new Person();

		person.setName("Jon");
		person.setHeight(60);
		person.setHairColor("Brown");
		person.setAge(32);
		person.setMarried(false);

		ObjectMapper mapper = new ObjectMapper();

		try (FileOutputStream out = new FileOutputStream("person.json")) {
			mapper.writeValue(out, person);
		}

		try (FileInputStream in = new FileInputStream("tg.json")) {
			Person tg = mapper.readValue(in, Person.class);

			System.out.println(tg.getName());
			System.out.println(tg.getAge());
			System.out.println(tg.getHairColor());
			System.out.println(tg.getHeight());
			System.out.println(tg.isMarried());
		}

		FileInputStream fis = null;
		FileOutputStream fos = null;

		try {
			fis = new FileInputStream("junk.txt");
			fos = new FileOutputStream("junk.copy.txt");
			int i = fis.read();
			while (i != -1) {
				fos.write(i);
				fos.write(' ');
				i = fis.read();
			}
		} finally {
			if (fis != null) {
				fis.close();
			}
			if (fos != null) {
				fos.close();
			}
		}

		Path filePath = Paths.get("junk.txt");
		Path outpath = Paths.get("junk.out.txt");

		try (BufferedReader br = Files.newBufferedReader(filePath);
				BufferedWriter bw = Files.newBufferedWriter(outpath);) {

			String line = br.readLine();
			while (line != null) {
				bw.write(line.toUpperCase());
				bw.newLine();
				line = br.readLine();
			}
		}
	}
}
