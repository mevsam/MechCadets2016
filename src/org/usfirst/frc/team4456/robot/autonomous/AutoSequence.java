package org.usfirst.frc.team4456.robot.autonomous;

public interface AutoSequence
{
	/**
	 * This method should get called in the initial autonomous stage.
	 * runs initial autonomous code
	 */
	public void runInit();
	
	/**
	 * This method should get called in the periodc autonomous stage.
	 * runs periodic autonomous code
	 */
	public void runPeriodic();
}
