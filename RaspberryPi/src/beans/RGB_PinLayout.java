package beans;

import com.pi4j.io.gpio.GpioPinDigitalOutput;

public class RGB_PinLayout implements PinLayout {
	
	private final GpioPinDigitalOutput redPin;
	private final GpioPinDigitalOutput greenPin;
	private final GpioPinDigitalOutput bluePin;
	
	public RGB_PinLayout(GpioPinDigitalOutput redPin, GpioPinDigitalOutput greenPin, GpioPinDigitalOutput bluePin) {
		this.redPin = redPin;
		this.greenPin = greenPin;
		this.bluePin = bluePin;
	}

	public GpioPinDigitalOutput getRedPin() {
		return redPin;
	}

	public GpioPinDigitalOutput getGreenPin() {
		return greenPin;
	}

	public GpioPinDigitalOutput getBluePin() {
		return bluePin;
	}
	
}
