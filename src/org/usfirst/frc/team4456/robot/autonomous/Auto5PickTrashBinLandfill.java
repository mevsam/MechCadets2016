package org.usfirst.frc.team4456.robot.autonomous;

import org.usfirst.frc.team4456.robot.*;

import edu.wpi.first.wpilibj.Timer;

public class Auto5PickTrashBinLandfill implements AutoSequence
{
	private Robot robot;
	private Timer timer;
	
	public Auto5PickTrashBinLandfill(Robot robot)
	{
		this.robot = robot;
		this.timer = new Timer();
	}
	
	@Override
	public void runInit()
	{
		int ladderPosLength = Constants.WINCH_LADDER_POSITIONS.length;
		
		Timer timer = new Timer();
		
		System.out.println("Running Auto");
		
		//open grabber
		robot.ladder.open();
		
		//bring ladder down
		robot.ladder.setIndex(2); // not sure about this index
		
		//move robot-back
		timer.start();
		while(timer.hasPeriodPassed(.3))
			robot.driver.driveRawPolar(.3, 180, 0, robot);
		robot.driver.driveRawPolar(0, 0, 0, robot);
		timer.stop();
		timer.reset();
		
		//grab trashcan & raise ladder
		robot.ladder.close();
		Timer.delay(1.0);
		robot.ladder.setIndex(ladderPosLength - 2);
		Timer.delay(1.0);
		
		//move robot-fwd
		timer.start();
		while(timer.hasPeriodPassed(1.9))
			robot.driver.driveRawPolar(.3, 0, 0, robot);
		robot.driver.driveRawPolar(0, 0, 0, robot);
		timer.stop();
		timer.reset();
	}

	@Override
	public void runPeriodic()
	{
		// TODO Auto-generated method stub

	}

}
