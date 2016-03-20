package beans;

import java.awt.Color;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;
import com.pi4j.wiringpi.SoftPwm;

public class RGBPin {
	
	private final RGB_PinLayout layout;
	private final GpioController gpio = GpioFactory.getInstance();
	private final GpioPinDigitalOutput redPin;
	private final GpioPinDigitalOutput greenPin;
	private final GpioPinDigitalOutput bluePin;
	private Color color;
	
	public RGBPin(Pin redPinName, Pin greenPinName, Pin bluePinName) {
		// Instanzio il layout dei led.
		layout = new RGB_PinLayout(gpio.provisionDigitalOutputPin(redPinName, PinState.LOW),
				gpio.provisionDigitalOutputPin(greenPinName, PinState.LOW),
				gpio.provisionDigitalOutputPin(bluePinName, PinState.LOW));
		
		redPin = layout.getRedPin();
		greenPin = layout.getGreenPin();
		bluePin = layout.getBluePin();
		
		// Setto le condizioni di shutdown dei led (i.e. led spenti)
		redPin.setShutdownOptions(true, PinState.LOW);
		greenPin.setShutdownOptions(true, PinState.LOW);
		bluePin.setShutdownOptions(true, PinState.LOW);
		
		// initialize wiringPi library
        com.pi4j.wiringpi.Gpio.wiringPiSetup();
		SoftPwm.softPwmCreate(redPin.getPin().getAddress(), 0, 100);
		SoftPwm.softPwmCreate(greenPin.getPin().getAddress(), 0, 100);
		SoftPwm.softPwmCreate(bluePin.getPin().getAddress(), 0, 100);
	}
	
	public GpioController getGpio() {
		return gpio;
	}

	public void displayColor(Color c) {
		// RGBComponents contiene le tre componenti R, G e B
		// sottoforma di frazioni (min 0 e max 1)
		final float[] RGBComponents = c.getRGBColorComponents(null);
		// Per poter mostrare colori diversi da R, G e B è necessario variare l'intesità
		// dei tre colori R, G e B. Per fare ciò si ricorre a softPwm. Nota: è necessario convertire
		// le componenti di RGBComponents da float a int (per fare ciò si usaMath.round) e moltiplicare
		// l'int per 100 in quanto la luminosità di softPwm è stata inizializzata per variare da 0 a 100,
		// mentre le componenti di RGBComponents vanno da 0 a 1.
		SoftPwm.softPwmWrite(redPin.getPin().getAddress(), Math.round(RGBComponents[0] * 100));
		SoftPwm.softPwmWrite(greenPin.getPin().getAddress(), Math.round(RGBComponents[1] * 100));
		SoftPwm.softPwmWrite(bluePin.getPin().getAddress(), Math.round(RGBComponents[2] * 100));
		this.color = c;
	}

	public Color getDisplayedColor() {
		return color;
	}
	
	public void turnOffLed() {
		displayColor(Color.BLACK);
	}
	
}
