package org.usfirst.frc.team4456.robot;

import org.usfirst.frc.team4456.robot.util.Util;

import com.kauailabs.navx_mxp.AHRS;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Class for the wheels on the chassis that drive the robot.
 * @author oom2013
 */
public class Driver
{
	RobotDrive robotDrive;
	private CANTalon talon1, talon2, talon3, talon4;
	private boolean buttonAPressed = false;
	private boolean autoStabilize;
	private float initialGyroVal, destGyroVal;
	private int autoStabilizeCount = 0;
	
	/** 
	 * Takes in robot type and initializes talon controllers depending on the robot type.
	 * @param roboType RobotType to use. MAIN_ PRACTICE_ or BREADBOARD_BOT
	 * @author samega15
	 */
	public Driver(RobotType roboType, float gyroValue)
	{
		autoStabilize = false;
		initialGyroVal = destGyroVal = gyroValue;
		
		if(roboType != null)
		{
			talon1 = new CANTalon(roboType.idRL);
			talon2 = new CANTalon(roboType.idFL);
			talon3 = new CANTalon(roboType.idRR);
			talon4 = new CANTalon(roboType.idFR);
		}
		else
		{
			System.err.println("ERROR: Talon Controllers > RobotType\n"
							+ "Driver.java\n"
							+ "public Driver(RobotType roboType)");
		}
		
		// Sets the RobotDrive object to the talon motors that are assigned by the boolean parameter.
		//order RL FL RR FR
		robotDrive = new RobotDrive(talon1, talon2, talon3, talon4);
		
		SmartDashboard.putNumber("P", .008);
		SmartDashboard.putNumber("I", .0001);
	}
	
	/** 
	 * Constructor checks whether or not we are using the test robot and switches between the test motors and the robot motors
	 * @param useTest
	 * @author oom2013
	 */
	public Driver(boolean useTest)
	{
		if(useTest)
		{
			talon1 = new CANTalon(11);
			talon2 = new CANTalon(10);
			talon3 = new CANTalon(15);
			talon4 = new CANTalon(22);
		}
		else
		{
			talon1 = new CANTalon(14);
			talon2 = new CANTalon(21);
			talon3 = new CANTalon(16);
			talon4 = new CANTalon(12);
			
			/*
			talon1 = new CANTalon(17);
			talon2 = new CANTalon(18);
			talon3 = new CANTalon(20);
			talon4 = new CANTalon(19);
			*/
		}
		
		
		// Sets the RobotDrive object to the talon motors that are assigned by the boolean parameter.
		robotDrive = new RobotDrive(talon1, talon2, talon3, talon4);
	}
	
	
	/**
	 *  Executes the Polar, Cartesian, or Tank method based on the useMechanum and useGyro booleans
	 * @param controller
	 * @param gyroVal - gyro value in degrees
	 * @param robot
	 * @author oom2013
	 */
	public void drive(XBoxController controller, float gyroVal, Robot robot)
	{
		// Set speed control
		updateSpeed(controller, robot);
		
		if(!controller.getA())
		{
			buttonAPressed = false;
		}
		
		
		//drive
		if(robot.useMechanum)
		{
			if(robot.useGyro)
	    	{
	    		this.driveCartesian(controller, gyroVal, robot);
	    	}
	    	else
	    	{
	    		this.drivePolar(controller, robot);
	    	}
		}
		// Tank drive is most likely not needed at all - This method will never be called
		else
		{
			this.driveTank(controller, robot);
		}
	}
	
	public void driveRawPolar(double magnitude, double direction, double rotation, Robot robot)
	{
		double rotAmount;
		double gyroDiff;
		
		if(this.autoStabilize)
		{
			autoStabilizeCount++;
			if(autoStabilizeCount > 100)
				destGyroVal = robot.navx.getYaw();
			
			destGyroVal += rotation * Constants.RS_GYRO_FACTOR_1;
			if(destGyroVal > 180)
				destGyroVal-=360;
			if(destGyroVal < -180)
				destGyroVal+=360;
			
			//gyroDiff = destGyroVal - robot.navx.getYaw();
			gyroDiff = Util.findAngleDiff(robot.navx.getYaw(), destGyroVal);
			
			rotAmount = Math.min(1, Math.pow(gyroDiff, 1) * Constants.RS_GYRO_FACTOR_2);
			
			robotDrive.mecanumDrive_Polar(magnitude,
										  direction,
										  rotAmount*robot.speedFactor);
		}
		else
		{
			robotDrive.mecanumDrive_Polar(magnitude, direction, rotation);
		}
	}
	
	/*
	public void translateYAxisMeters(double displaceAmount, double time, Robot robot)
	{
		PIDSource pidSource = new PIDSource() {
			@Override
			public double pidGet() {
				return robot.navx.getDisplacementY();
			}
		};
		
		PIDOutput pidOutput = new PIDOutput() {
			@Override
			public void pidWrite(double output) {
				robotDrive.tankDrive(output, output);
			}
		};
		
		PIDController pidCtrl = new PIDController(1, 0, 0, pidSource, pidOutput);
		
		pidCtrl.setSetpoint(displaceAmount);
		pidCtrl.setOutputRange(-1.0, 1.0);
		pidCtrl.enable();
		Timer.delay(time);
		pidCtrl.disable();
	}
	*/
	
	/*
	public void rotateInDegreesPIDLib(double rotAmount, double time, Robot robot)
	{
		PIDSource pidSource = new PIDSource() {
			@Override
			public double pidGet()
			{
				return robot.navx.getYaw();
			}
		};
		PIDOutput pidOut = new PIDOutput(){
			@Override
			public void pidWrite(double output){
				driveRawPolar(0, 0, output, robot);
			}
		};
		PIDController pidCtrl = new PIDController(.0056, .000001, 0, pidSource, pidOut){
			@Override
			public synchronized double getError() {
				double error = super.getError();
				if(error > 180)
					error-=360;
				if(error < -180)
					error+=360;
				return error;
			}
		};
		
		pidCtrl.setSetpoint(rotAmount);
		pidCtrl.setOutputRange(-1.0, 1.0);
		pidCtrl.enable();
		Timer.delay(time);
		pidCtrl.disable();
	}
	*/
	
	/*using PID to rotate to a certain value*/
	public void rotateInDegrees(double rotAmount, double time, Robot robot)
	{
		double error = 0;
		double prevError = 0;
		
		double pConstant = .0056;
		double pOutput = 0;
		
		double iConstant = .000001;
		double iOutput = 0;
		
		double dConstant = 0;
		double dOutput = 0;
		
		//integral is a Right Riemann Sum approximation
		double integralOfError = 0; //integral of error(t) w respect to time in seconds
		
		double currentTime = 0;
		double prevTime = 0;
		double deltaTime = 0;
		
		double targetValue = robot.navx.getYaw() + rotAmount;
		double output = 0;
		
		pConstant = SmartDashboard.getNumber("P");
		iConstant = SmartDashboard.getNumber("I");
		
		if(targetValue > 180)
			targetValue-=360;
		if(targetValue < -180)
			targetValue+=360;
		
		Timer timer = new Timer();
		timer.start();
		//will adjust rot for time
		while(!timer.hasPeriodPassed(time))
		{
			prevError = error;
			error = Util.findAngleDiff(targetValue, robot.navx.getYaw());
			
			//time
			prevTime = currentTime;
			currentTime = timer.get();
			deltaTime = currentTime - prevTime;
			
			//P output
			pOutput = pConstant * error;
			
			//I output
			integralOfError += iConstant * (error * deltaTime);
			iOutput = integralOfError;
			
			//D output
			dOutput = dConstant * deltaTime * (error - prevError);
			
			output = pOutput + iOutput + dOutput;
			output = Math.min(Math.max(-1.0, output) , 1.0);
			
			this.driveRawPolar(0, 0, output, robot);
		}
		timer.stop();
	}
	
	public void stop()
	{
		robotDrive.mecanumDrive_Polar(0, 0, 0);
	}
	
	public void driveRawCartesian(double x, double y, double rotation, double gyroAngle)
	{
		robotDrive.mecanumDrive_Cartesian(x, y, rotation, gyroAngle);
	}
	
	public boolean getAutostabilize()
	{
		return autoStabilize;
	}

	public void setAutostabilize(boolean setValue)
	{
		this.autoStabilize = setValue;
	}
	
	private void updateSpeed(XBoxController controller, Robot robot)
	{
		if(controller.getA() && !buttonAPressed)
		{
			buttonAPressed = true;
			
			if(robot.speedFactor == .70)
			{
				robot.speedFactor = 1;
			}
			else
			{
				robot.speedFactor = .70;
			}
		}
	}

	/**
	 *  This will be used if we do not have a gyroscope
	 * @param controller
	 * @author oom2013
	 */
	private void drivePolar(XBoxController controller, Robot robot)
	{
		// Parameters are Magnitude, Direction, Rotation
		// Arguments are the magnitude of the joysticks, the direction of the joysticks, and the value given by the right-stick x-value
		
		
		this.driveRawPolar(Util.lowerSensitivity(controller.getMagnitude(),robot),
							controller.getDirectionDegrees(),
							Util.lowerSensitivity(controller.getAxisRStickX(),robot),
							robot);
							
		/*
		if(this.autoStabilize)
		{
			//will determine rotation value in order to minimize strafe error.
			destGyroVal += controller.getAxisRStickX() * Constants.RS_GYRO_FACTOR_1;
			gyroDiff = robot.navx.getYaw() - destGyroVal;
			rotAmount = Math.min(1, Math.pow(gyroDiff, 3) * Constants.RS_GYRO_FACTOR_2);
			
			robotDrive.mecanumDrive_Polar(Util.lowerSensitivity(controller.getMagnitude(),robot),
										controller.getDirectionDegrees(),
										rotAmount*robot.speedFactor);
			
			SmartDashboard.putNumber("rotAmount", rotAmount);
		}
		else
		{
			robotDrive.mecanumDrive_Polar(Util.lowerSensitivity(controller.getMagnitude(), robot), 
					    			controller.getDirectionDegrees(), 
					    			Util.lowerSensitivity(controller.getAxisRStickX(), robot));
		}
		*/
	}
	
	/**
	 * This will be used if we do have a gyroscope
	 * @param controller
	 * @param gyro
	 * @author oom2013
	 */
	private void driveCartesian(XBoxController controller, float gyroVal, Robot robot)
	{
		// Parameters are X, Y, Rotation, and Gyro Angle
		// Arguments are the values given by the left-stick x-value, left-stick y-value, right-stick x-value, and the angle produced by the gyroscope
		robotDrive.mecanumDrive_Cartesian(Util.lowerSensitivity(controller.getAxisLStickX(), robot),
				Util.lowerSensitivity(controller.getAxisLStickY(), robot),
				-1 * Util.lowerSensitivity(controller.getAxisRStickX(), robot),
    			gyroVal);
	}
	
	/**
	 * This will be used if we are using TankDrive instead of Mechanum,
	 * but will probably not be used
	 * @param controller
	 * @author oom2013
	 */
	private void driveTank(XBoxController controller, Robot robot)
	{
		robotDrive.tankDrive(Util.lowerSensitivity(controller.getAxisLStickY(), robot),
				-1 * Util.lowerSensitivity(controller.getAxisRStickY(), robot));
	}
	
}