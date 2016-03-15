package beans;

import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class LedControlledByButtonSync implements LedControlledByButton {

	@Override
	public void listener() {
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
				/*
				 * Quando il bottone è premuto si chiude il circuito con la messa a terra e la tensione diventa 0,
				 * viceversa quando è sollevato c'e' nel circuito una tensione di 3.3 V.
				 */
				if (event.getState().equals(PinState.LOW)) {
					/*
					 * La parte del synchronized è hacky...in pratica per l'implementazione Breathing Led
					 * pochè l'accensione e spegnimento del led richiede tempo, è stato necessario sincronizzare 
					 * accensione e spegnimento per evitare overlapping, tuttavia il semplice utilizzo di synchronized
					 * come key word dei metodi non è sufficiente in quanto entrambi accedono alla risorsa ledPin che
					 * è public...allora è stato necessario sincronizzare tale risorsa (vedi il link:
					 * http://stackoverflow.com/questions/11073841/why-doesnt-this-synchronized-method-work-as-expected)
					 */
					try {
						System.out.println("[*] Button [" + event.getPin().getName() + "] pressed.");
						synchronized(ledPin) {
							turnPinToHigh();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					try {
						System.out.println("[*] Button [" + event.getPin().getName() + "] released.");
						synchronized(ledPin) {
							turnPinToLow();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		while (true) {
		}
	}
	void turnPinToHigh() throws InterruptedException {
	}
	void turnPinToLow() throws InterruptedException {
	}

}
