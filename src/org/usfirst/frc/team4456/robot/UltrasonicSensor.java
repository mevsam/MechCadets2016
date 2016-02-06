package org.usfirst.frc.team4456.robot;

import edu.wpi.first.wpilibj.AnalogInput;

public class UltrasonicSensor
{
	private AnalogInput ultrasonicSensor;
	
	public UltrasonicSensor(int channel)
	{
		ultrasonicSensor = new AnalogInput(channel);
	}
	
	public double getValueInches()
	{
		return (ultrasonicSensor.getAverageVoltage()) * (Constants.ULTRASONIC_FACTOR_VOLTS);
	}
}