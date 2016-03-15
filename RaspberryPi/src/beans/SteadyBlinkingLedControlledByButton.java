package beans;

import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;

public class SteadyBlinkingLedControlledByButton extends LedControlledByButtonSync {

	@Override
	public void turnPinToHigh() {
		ledPin.high();
	}

	@Override
	public void turnPinToLow() {
		ledPin.low();
	}
	
	public GpioPinDigitalOutput getDigitalOutput() {
		return ledPin;
	}
	public GpioPinDigitalInput getDigitalIntput() {
		return buttonPin;
	}
}
