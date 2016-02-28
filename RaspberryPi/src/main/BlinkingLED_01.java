package main;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

/**
 * 
 * @author Luca Raimondi [27/02/2016]
 * 
 * This class (hopefully) perform a simple state
 * control of a GPIO pin on the Raspberry Pi.
 * 
 * The circuit used to test the code is:
 * 1) Generator (3.3 V) - Setting GPIO 0 as a output;
 * 2) 220 Ohm resistor;
 * 3) Led;
 * 4) Ground (i.e. 0 V pin)
 * In fact, using the Ohm's law: I=V/R=3.3/220=15mA (that is the current required by LEDs to emit light).
 * 
 */

public class BlinkingLED_01 {
	
	// Creating a gpio controller...
	final static GpioController gpio = GpioFactory.getInstance();
	// Provisioning gpio pin #00 as an output pin and turn on
	// so that the led at the beginning is off.
	final static GpioPinDigitalOutput ledPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "led_1", PinState.LOW);
	
	public static void main(String[] args) throws InterruptedException {
		// Setting shutdown state for the ledPin
		ledPin.setShutdownOptions(true, PinState.LOW);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("\n[*] Program interrupted. Exiting...");
				gpio.shutdown();
			}
		});
		System.out.println("#--- GPIO control started ---#");
		System.out.println("[+] Led will start pulsating...");
		System.out.println("[*] Press Ctrl-C to abort the program...");
		System.out.println("#--------------------------------------#");
		while (true) {
			ledPin.pulse(1000, true);
			Thread.sleep(3000);
		}
	}
}