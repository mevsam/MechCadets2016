package org.usfirst.frc.team4456.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CANTalon.ControlMode;
import edu.wpi.first.wpilibj.CANTalon.FeedbackDevice;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import org.usfirst.frc.team4456.robot.util.*;

/**
 * TRASH CAN
 * Class for the winch on the ladder device that picks up the trash can.
 * CONTROLS:
 * dpad up down raise/lower
 * dpad left right nudge down/up
 * A close, B open
 * @author samega15
 */
public class Ladder
{
	private CANTalon talon;
	private boolean buttonXPress = false, buttonYPress = false;
	private int currentTargetIndex;
	
	private DoubleSolenoid piston1, piston2;
	private boolean gripIsOpen;
	private boolean buttonBPressed = false;
	
	//PID_VALUES
	private double pValueUp = .5,
				   pValueDn = .25;
	
	/**
	 * Constructor for WinchLadder
	 * This will construct a new talon motor object with the appropriate pid settings
	 * @param idTalon Talon motor id
	 * @author samega15
	 */
	public Ladder(int idTalon, int id1_1, int id1_2, int id2_1, int id2_2)
	{
		talon = new CANTalon(idTalon);
		talon.changeControlMode(ControlMode.Position);
		talon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		talon.setPID(pValueDn, 0.000001, 0);
		talon.setPosition(0);
		talon.set(talon.get()); //talon1 will not move
		
		piston1 = new DoubleSolenoid(id1_1, id1_2);
		piston2 = new DoubleSolenoid(id2_1, id2_2);
	}
	
	public int getCurrentTargetIndex()
	{
		return currentTargetIndex;
	}
	
	public void setIndex(int index)
	{
		talon.set(Constants.WINCH_LADDER_POSITIONS[index]);
	}
	
	/**
	 * Periodic cycle function for Ladder
	 * @param controller xboxController
	 * @author samega15
	 */
	public void cycle(XBoxController controller, Robot robot)
	{
		
		//DPad_Down lowerLadder
		/*
		if(controller.getX() && !buttonXPress)
		{
			buttonXPress = true;
			this.lowerLadder();
		}
		else if(!controller.getX() && buttonXPress)
		{
			buttonXPress = false;
		}
		
		//DPad_Up raiseLadder
		if(controller.getY() && !buttonYPress)
		{
			buttonYPress = true;
			this.raiseLadder();
		}
		else if(!controller.getY() && buttonYPress)
		{
			buttonYPress = false;
		}
		*/
		
		//dpadLeft nudgeDown
		if(controller.getX())
		{
			double newSetPoint = talon.getSetpoint() - Constants.LADDER_NUDGE_FACTOR;
			//set limit
			if (robot.limitModeEnabled)
				newSetPoint = Util.max(newSetPoint, Constants.WINCH_LADDER_POSITIONS[0]);
			talon.set(newSetPoint);
		}
		
		//dpadRight nudgeUp
		if(controller.getY())
		{
			double newSetPoint = talon.getSetpoint() + Constants.LADDER_NUDGE_FACTOR;
			//set limit
			if (robot.limitModeEnabled)
				newSetPoint = Util.min(newSetPoint, Constants.WINCH_LADDER_POSITIONS[Constants.WINCH_LADDER_POSITIONS.length-1]);
			talon.set(newSetPoint);
		}
		
		//TOGGLE GRIP OPEN CLOSE
		if(controller.getB() && !buttonBPressed)
		{
			toggleGripOpenClosed();
			buttonBPressed = true;
			System.out.println("Action triggered");
		}
		if(!controller.getB())
		{
			buttonBPressed = false;
		}
	}
	
	/** 
	 * Returns what the current winch position is at
	 */
	public double getWinchPosition()
	{
		return talon.get();
	}
	
	
// ----------------------------------------------------------------------
//AUX FUNCTIONS ---------------------------------------------------------
// ----------------------------------------------------------------------
	
	private void toggleGripOpenClosed()
	{
		if (gripIsOpen)
		{
			close();
		}
		else
		{
			open();
		}
	}
	
	/**
	 * Extend the pistons to close
	 * @author samega15
	 */
	public void close()
	{
		piston1.set(Value.kReverse);
		piston2.set(Value.kReverse);
		gripIsOpen = false;
	}
	public void open()
	{
		piston1.set(Value.kForward);
		piston2.set(Value.kForward);
		gripIsOpen = true;
	}
	
	private void raiseLadderMax()
	{
		talon.set(Constants.WINCH_LADDER_POSITIONS[Constants.WINCH_LADDER_POSITIONS.length-1]);
		this.currentTargetIndex = Constants.WINCH_LADDER_POSITIONS.length-1;
	}
	
	private void raiseLadder()
	{
		int closestIndex = findClosestPosition();
		int targetIndex;
		if(closestIndex >= Constants.WINCH_LADDER_POSITIONS.length-1)
		{
			targetIndex = Constants.WINCH_LADDER_POSITIONS.length - 1;
		}
		else
		{
			targetIndex = closestIndex + 1;
		}
		talon.set(Constants.WINCH_LADDER_POSITIONS[targetIndex]);
		this.talon.setP(this.pValueUp);
		this.currentTargetIndex = targetIndex;
	}
	
	private void lowerLadder()
	{
		int closestIndex = findClosestPosition();
		int targetIndex;
		if(closestIndex <= 0)
		{
			targetIndex = 0;
		}
		else
		{
			targetIndex = closestIndex-1;
		}
		talon.set(Constants.WINCH_LADDER_POSITIONS[targetIndex]);
		this.talon.setP(this.pValueDn);
		this.currentTargetIndex = targetIndex;
	}
	
	private void lowerWinchMin()
	{
		talon.set(Constants.WINCH_LADDER_POSITIONS[0]);
		this.currentTargetIndex = 0;
	}
	
	private int findClosestPosition()
	{
		double currentPos = talon.get();
		double closestDistance = 0;
		int closestIndex = 0;
		double highestPos = Constants.WINCH_LADDER_POSITIONS[Constants.WINCH_LADDER_POSITIONS.length-1];
		double lowestPos = Constants.WINCH_LADDER_POSITIONS[0];
		if(currentPos > highestPos)
		{
			return Constants.WINCH_LADDER_POSITIONS.length-1;
		}
		else if(currentPos < lowestPos)
		{
			return 0;
		}
		else
		{
			for(int i = 0; i < Constants.WINCH_LADDER_POSITIONS.length; i++)
			{
				double distance = Math.abs(currentPos - Constants.WINCH_LADDER_POSITIONS[i]);
				if(distance < closestDistance || i == 0)
				{
					closestDistance = distance;
					closestIndex = i;
				}
			}
		}
		return closestIndex;
	}
}