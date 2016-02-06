package org.usfirst.frc.team4456.robot;

import java.util.Iterator;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class AutonomousCommand2 extends Command
{
	Robot robot;
	boolean isFinished = false;
	double initialDisplacement;
	int hookPositionsLength = Constants.HOOK_LOADER_POSITIONS.length;
	
	Timer timer = new Timer();
	
	public AutonomousCommand2(Robot robot)
	{
		this.robot = robot;
	}
	
	//method is called 1st time the command runs
	@Override
	protected void initialize()
	{
		timer.start();
		System.out.println("Running AutoCommand2");
		initialDisplacement = robot.navx.getDisplacementY();
		robot.hooks.setIndex(hookPositionsLength - 2);
		Timer.delay(1);
	}
	
	//periodically called until command finishes
	@Override
	protected void execute()
	{		
		robot.driver.driveRawPolar(.4, 180, 0, robot);
	}
	
	//returns true if the command is finished running
	@Override
	protected boolean isFinished()
	{
		return timer.hasPeriodPassed(4);
	}
	
	//called when command ends w/o interruption
	@Override
	protected void end()
	{
		robot.driver.driveRawPolar(0, 0, 0, robot);
		robot.hooks.setIndex(Constants.HOOK_LOADER_POSITIONS.length - 1); // set hooks back down to the lowest level
	}
	
	//called when command is interupted
	@Override
	protected void interrupted()
	{
		robot.driver.driveRawPolar(0, 0, 0, robot);
	}

}
