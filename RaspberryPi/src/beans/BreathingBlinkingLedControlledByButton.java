package beans;

import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.wiringpi.SoftPwm;

public class BreathingBlinkingLedControlledByButton extends LedControlledByButtonSync {   
	private int wiringPiNum = ledPin.getPin().getAddress();
	public BreathingBlinkingLedControlledByButton() {
        com.pi4j.wiringpi.Gpio.wiringPiSetup();
    	SoftPwm.softPwmCreate(wiringPiNum, 0, 100);
    }
	@Override
	public void turnPinToHigh() throws InterruptedException {
		System.out.println("UP - started");
		for (int i=0; i<=100; i++) {
			SoftPwm.softPwmWrite(wiringPiNum, i);
			Thread.sleep(100);
		}
		System.out.println("UP - finished");
	}

	@Override
	public void turnPinToLow() throws InterruptedException {
		System.out.println("DOWN - started");
		for (int i=100; i>=0; i--) {
			SoftPwm.softPwmWrite(wiringPiNum, i);
			Thread.sleep(100);
		}
		System.out.println("DOWN - finished");
	}
	
	public GpioPinDigitalOutput getDigitalOutput() {
		return ledPin;
	}
	public GpioPinDigitalInput getDigitalIntput() {
		return buttonPin;
	}
}
