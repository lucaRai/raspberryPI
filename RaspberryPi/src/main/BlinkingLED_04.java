package main;

import com.pi4j.io.gpio.GpioPinDigitalOutput;

import beans.BreathingBlinkingLedControlledByButton;
import beans.SteadyBlinkingLedControlledByButton;

public class BlinkingLED_04 {

	static SteadyBlinkingLedControlledByButton bL = new SteadyBlinkingLedControlledByButton();
	static GpioPinDigitalOutput lP = bL.getDigitalOutput();
	
	public static void main(String[] args) {
		
		String steadyFlag = "-s";
		String breathingFlag = "-b";
		String selectedMode = "";
		
		try {
			if (args.length != 1) {
				String usage = "Usage: sudo java -jar <jar name> " + steadyFlag + "|" + breathingFlag;
				throw new IllegalArgumentException(usage);
			}
			
			if (args[0].equalsIgnoreCase(breathingFlag)) {
				BreathingBlinkingLedControlledByButton bL = new BreathingBlinkingLedControlledByButton();
				selectedMode = "Breathing Led";
				makeHeader(selectedMode);
				bL.listener();
			} else if (args[0].equalsIgnoreCase(steadyFlag)) {
				SteadyBlinkingLedControlledByButton sL = new SteadyBlinkingLedControlledByButton();
				selectedMode = "Steady Led";
				makeHeader(selectedMode);
				sL.listener();
			} else {
				throw new IllegalArgumentException("Wrong option. Use: "+ steadyFlag + " or " + breathingFlag + " instead...");
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	private static void makeHeader(String mode) {
		System.out.println("#####");
		System.out.println("#### Blinking Led - LR [09/03/2016]");
		System.out.println("### Selected: " + mode);
		System.out.println("## Press Ctrl-c to exit");
		System.out.println("#");
	}
}
