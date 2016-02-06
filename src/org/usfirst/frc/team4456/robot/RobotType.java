package org.usfirst.frc.team4456.robot;

/**
 * enum for all robot types. Makes it easier to switch from the 3 different robots we are using.
 * PRACTICE_BOT is for the practice 6000 robot
 * MAIN_BOT is for the main 4456 robot
 * BREADBOARD_BOT is for the 5000 breadboard
 * @author samega15
 *
 */
public enum RobotType
{
	//old 14 21 16 12
	PRACTICE_BOT_REV(21, 14, 12, 16, 6000, "PRACTICE_REV"),
	PRACTICE_BOT(16, 12, 14, 21, 6000, "PRACTICE"), //11, 22
	MAIN_BOT(17, 18, 20, 19, 4456, "MAIN"),
	BREADBOARD_BOT(11,10,15,22, 5000, "BREADBOARD");
	
	//CAN ids for motors.
	public final int idRL, idFL, idRR, idFR;
	public final int teamNum;
	public final String robotTypeName;
	
	private RobotType(int idRL, int idFL, int idRR, int idFR, int teamNum, String roboName)
	{
		this.idRL = idRL;
		this.idFL = idFL;
		this.idRR = idRR;
		this.idFR = idFR;
		this.teamNum = teamNum;
		this.robotTypeName = roboName + "_BOT";
	}
	
	public String toString()
	{
		return "Robot: " + robotTypeName +
				"\nRobot TeamNum: " + teamNum +
				"\nMotorIDs\n" +
				"RL FL RR FR\n" +
				idRL + " " + idFL + " " + idRR + " " + idFR;
	}
}
