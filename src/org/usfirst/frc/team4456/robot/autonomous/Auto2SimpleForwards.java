package org.usfirst.frc.team4456.robot.autonomous;

import org.usfirst.frc.team4456.robot.Robot;

import edu.wpi.first.wpilibj.Timer;

public class Auto2SimpleForwards implements AutoSequence {
	
	Robot robot;
	Timer timer;
	private float initialDisplacement;
	
	public Auto2SimpleForwards(Robot robot)
	{
		this.robot = robot;
		timer = new Timer();
	}

	@Override
	public void runInit()
	{
		initialDisplacement = robot.navx.getDisplacementY();
	}

	@Override
	public void runPeriodic()
	{
		if(!(Math.abs(robot.navx.getDisplacementY() - initialDisplacement) > 15))
			robot.driver.driveRawPolar(.4, 0, 0, robot); // drive fwd magnitude .4
		else
			robot.driver.driveRawPolar(0, 0, 0, robot);
		
		//TIME BASED -------
		/*
		if(!timer.hasPeriodPassed(2.5))
			robot.driver.driveRawPolar(.4, 0, 0);
		else
			robot.driver.driveRawPolar(0, 0, 0);
		*/

	}

}
