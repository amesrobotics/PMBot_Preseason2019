/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

//I HAVE NO IDEA WHY THIS ISN'T ACCEPTING THE com and ctre LIBRARIES 10/1/19
//Looks to be working 10/10/19
//import edu.wpi.first.wpilibj.VictorSPX;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.MecanumDrive;    //Testing Mecanum code from another team on GitHub 10/22/19

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import com.ctre.phoenix.ILoopable;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.*;

/**
 * Add your docs here.
 */
public class MotorController {
    private CANSparkMax m_leadMotor;
    private CANSparkMax m_followMotor;
    public static final double RESET_DELAY_SEC = 0.25d;
    public static final double RETRACT_TIME_SEC = 0.25d;
    private static final int leadDeviceID = 1;
    private static final int followDeviceID = 2;

    /*
    fireVent
       |
    V--+--fireValve---+
    V                 +--Tank--Compressor
    V--+--resetValve--+
       |
    resetVent
    */
    Compressor compressor = new Compressor(0);
    Solenoid fireValve = new Solenoid(1); // F
    Solenoid fireVent = new Solenoid(2); // FV
    Solenoid resetValve = new Solenoid(3); // R
    Solenoid resetVent = new Solenoid(4); // RV

<<<<<<< HEAD
    private class PMState{
        float stateTime;
        boolean fireValve;
        boolean fireVent;
        boolean resetValve;
        boolean resetVent;
        public PMState(float stateTime, boolean f, boolean fv, boolean r, boolean rv)
        {
            this.stateTime = stateTime;
            fireValve = f;
            fireVent = fv;
            resetValve = r;
            resetVent = rv;
        }
=======
    private double x;   //Testing Mecanum code from another team on GitHub 10/22/19
    private double y;   //Testing Mecanum code from another team on GitHub 10/22/19
    private double z;   //Testing Mecanum code from another team on GitHub 10/22/19

    public enum FireState {
        Primed,     // FV open
        PreFire,    // RV open
        Fire,        // F, RV open
        Extended,    // RV open
        PreRetract,  // FV open
        Retract    // R, FV open
>>>>>>> 027c12791429ab2c48de66350172a35a842e9371
    }
    // Pneumatics: false=closed, true=open
    private final PMState pmStandby = new PMState(100, false, false, true, false); // The default state, when not in a firing cycle.
    private int pmSeqIdx = 0; // Index in pmFireSequence that I'm currently on
    private final PMState[] pmFireSequence = {
        new PMState(0.25f, true, false, false, true), // Fire
        new PMState(0.1f, false, false, false, false), // pre Reset
        new PMState(0.25f, false, true, true, false), // Reset
        new PMState(0.1f, false, false, false, false) // pre Standby
    };
    private PMState fireState = pmStandby; // Always use setFireState() to change this!
    /** Returns true if the piston has been fired and cannot shoot until it retracts. Returns false otherwise. */
    //public PMState getFireState() { return fireState; }
    public void setFireState(PMState value) {
        fireState = value;
        timeAtLastStateChange = Timer.getFPGATimestamp();
    }
    private double timeAtLastStateChange = 0;
    public double timeSinceStateChange() {
        return Timer.getFPGATimestamp() - timeAtLastStateChange;
    }

    public void setDriver(double var[]){
       m_leadMotor = new CANSparkMax(leadDeviceID, MotorType.kBrushless);
       m_followMotor = new CANSparkMax(followDeviceID, MotorType.kBrushless);
        
       m_leadMotor.restoreFactoryDefaults();
       m_followMotor.restoreFactoryDefaults();
       //m_followMotor.follow(m_leadMotor);
       m_leadMotor.set(var[0]);
    }
    
    public void fire()
    {
        if(fireState != pmStandby)
            return; // Can't fire before piston has been reset! TODO: give a warning message here?

<<<<<<< HEAD
        // Start the firing sequence by moving off of pmStandby:
        pmSeqIdx = 0;
        setFireState(pmFireSequence[pmSeqIdx]);
=======
        fireValve.set(true);
        resetValve.set(false);
        //setFireState(FireState.Firing);
>>>>>>> 027c12791429ab2c48de66350172a35a842e9371
    }

    /**
     * Called once each autonomousPeriodic() and teleopPeriodic() step by Robot.java.
     */
    public void enabledPeriodic()
    {
<<<<<<< HEAD
        // In in a fire cycle, set solenoids to appropriate states and check for moving to the next step
        if(fireState != pmStandby)
        {
            fireValve.set(fireState.fireValve);
            fireVent.set(fireState.fireVent);
            resetValve.set(fireState.resetValve);
            resetVent.set(fireState.resetVent);

            if(timeSinceStateChange() > fireState.stateTime) // Time to move to the next step?
            {
                pmSeqIdx++;
                if(pmSeqIdx < pmFireSequence.length)
                    setFireState(pmFireSequence[pmSeqIdx]);
                else
                    setFireState(pmStandby);
            }
        }
=======
        /*if(fireState==FireState.Firing && timeSinceStateChange() > RESET_DELAY_SEC)
        {
            fireValve.set(false);
            resetValve.set(true);
            setFireState(FireState.Firing);
        }*/
    }
    public void setX(double x){ //Testing Mecanum code from another team on GitHub 10/22/19
        this.x = x;
>>>>>>> 027c12791429ab2c48de66350172a35a842e9371
    }
    public void setY(double y){ //Testing Mecanum code from another team on GitHub 10/22/19
        this.y = y;
    }
    public void setZ(double z){ //Testing Mecanum code from another team on GitHub 10/22/19
        this.z = z;
    }

}
