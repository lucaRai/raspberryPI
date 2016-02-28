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
 */

public class BlinkingLED_01 {
	
	// Creating a gpio controller...
	final static GpioController gpio = GpioFactory.getInstance();
	// Provisioning gpio pin #00 as an output pin and turn on
	// so that the led at the beginning is off.
	final static GpioPinDigitalOutput ledPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "led_1", PinState.HIGH);
	
	public static void main(String[] args) {
		try {
		System.out.println("#--- GPIO control started ---#");
		// Setting shutdown state for the ledPin
		ledPin.setShutdownOptions(true, PinState.HIGH);
		System.out.println("[+] Led will start pulsating with 1 sec phase...");
		System.out.println("[*] Press Ctrl-C to abort the program...");
		while (true) {
			ledPin.pulse(1000, true);
			Thread.sleep(1000);
		}
		// Stop all gpio activity/threads by shutting down the GPIO controller.
		} catch (InterruptedException e) {
			System.out.println("[*] Ctrl-C pushed...");
			gpio.shutdown();
			System.out.println("[+] Program aborted (the led should be off)...");
		}
	}
}