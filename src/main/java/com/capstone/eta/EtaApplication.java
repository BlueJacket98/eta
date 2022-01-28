package com.capstone.eta;

import com.capstone.eta.util.spring.ApplicationContextProvider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ApplicationContextProvider.class)
public class EtaApplication {

	public static void main(String[] args) {
		System.out.println("=========================Welcome to Microsoft ETA project================================");
		System.out.println("     __  ________ ____________   _______________       ____               _           __ ");
		System.out.println("    /  |/  / ___// ____/_  __/  / ____/_  __/   |     / __ \\_________    (_)__  _____/ /_");
		System.out.println("   / /|_/ /\\__ \\/ /_    / /    / __/   / / / /| |    / /_/ / ___/ __ \\  / / _ \\/ ___/ __/");
		System.out.println("  / /  / /___/ / __/   / /    / /___  / / / ___ |   / ____/ /  / /_/ / / /  __/ /__/ /_  ");
		System.out.println(" /_/  /_//____/_/     /_/    /_____/ /_/ /_/  |_|  /_/   /_/   \\____/_/ /\\___/\\___/\\__/  ");
		System.out.println("                                                                   /___/                 ");
		System.out.println("=========================================================================================");
		SpringApplication.run(EtaApplication.class, args);
	}

}
