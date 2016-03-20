package main;

import java.awt.Color;

import com.pi4j.io.gpio.RaspiPin;

import beans.RGBPin;

public class RGBLed_05 {
	
	static RGBPin rgbPin = new RGBPin(RaspiPin.GPIO_01, RaspiPin.GPIO_02, RaspiPin.GPIO_00);
	
	public static void main(String[] args) {
		
		// Lo shutdownHook viene invocato in caso di uscita regolare,
		// oopure se lo user blocca l'esecuzione con Ctrl^C
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("\n[*] Exiting...\n");
				rgbPin.getGpio().shutdown();
			}
		});
		
		// Stampo l'intestazione.
		makeHeader();
		
		// Mostro alcuni colori...
		showColor(Color.RED);
		showColor(Color.GREEN);
		showColor(Color.BLUE);
		showColor(Color.WHITE);
		showColor(Color.YELLOW);
		showColor(Color.CYAN);
		showColor(Color.PINK);
	}
	
	private static void showColor(Color c) {
		rgbPin.displayColor(c);
		System.out.println("Displayed color: " + rgbPin.getDisplayedColor());
		try {
			// Ciasun colore rimane attivo per 3 sec...
			Thread.sleep(3000);
			rgbPin.turnOffLed();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private static void makeHeader() {
		System.out.println("####");
		System.out.println("### RGB Led - LR [20/03/2016]");
		System.out.println("## Press Ctrl-c to exit");
		System.out.println("#");
	}
}
