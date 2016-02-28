package main;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class LedAndButton {

	// Creating a gpio controller...
	final static GpioController gpio = GpioFactory.getInstance();
	final static GpioPinDigitalOutput ledPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, PinState.LOW);
	final static GpioPinDigitalInput buttonPin = gpio.provisionDigitalInputPin(RaspiPin.GPIO_06, PinPullResistance.PULL_UP);
	
	public static void main(String[] args) {
		// Setting shutdown state for the ledPin
		ledPin.setShutdownOptions(true, PinState.LOW);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("\n[*] Program interrupted. Exiting...");
				gpio.shutdown();
			}
		});
		// Creating and registering a gpio pin listener
		buttonPin.addListener(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				System.out.println("[*] " + event.getPin() + " changed to: " + event.getState());
				if (event.getState().equals(PinState.LOW)) {
					ledPin.high();
				} else {
					ledPin.low();
				}
			}
		});
		System.out.println("#--------- GPIO control started --------#");
		System.out.println("[+] Push the button to turn on the LED...");
		System.out.println("[*] Press Ctrl-C to abort the program...");
		System.out.println("#---------------------------------------#");
		while (true) {
		}
	}
}
