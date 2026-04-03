package frc.robot.subsystems;

import frc.robot.Constants;

// import com.revrobotics.RelativeEncoder;
// import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
// import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.ResetMode;
import com.revrobotics.PersistMode;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
// import edu.wpi.first.wpilibj.Timer;
// import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

public class ShooterSubsystem {

  private final SparkMax shooterMotor =
      new SparkMax(Constants.ShooterConstants.SHOOTER_MOTOR, MotorType.kBrushless);

  // private final SparkMax positionMotor =
  //     new SparkMax(Constants.ShooterConstants.POSITION_MOTOR, MotorType.kBrushless);
    
  private final SparkMax climberMotor =
      new SparkMax(Constants.ShooterConstants.CLIMBER_MOTOR, MotorType.kBrushless);

  private final SparkMax intakeMotor =
      new SparkMax(Constants.ShooterConstants.INTAKE_MOTOR, MotorType.kBrushless);

  // private final RelativeEncoder encoder = positionMotor.getEncoder();
  // private final SparkClosedLoopController pid = positionMotor.getClosedLoopController();

  // private final PWMSparkMax actuator =
  //     new PWMSparkMax(Constants.ActuatorConstants.PWM_PORT);

  // private boolean positiveDirection = true;
  // private double holdPosition = 0;

  // private boolean actuatorExtended = false;
  // private boolean actuatorRunning = false;
  // private double actuatorCommand = 0;

  // private final Timer actuatorTimer = new Timer();

  public ShooterSubsystem() {

    SparkMaxConfig config = new SparkMaxConfig();
    config.smartCurrentLimit(40);
    config.idleMode(IdleMode.kBrake);
    config.closedLoop.pid(0.08, 0, 0, ClosedLoopSlot.kSlot0);


    SparkMaxConfig base = new SparkMaxConfig();
    base.voltageCompensation(12.0);
    base.smartCurrentLimit(60);

    SparkMaxConfig climberCfg = new SparkMaxConfig();
    climberCfg.apply(base);
    climberCfg.inverted(false);
    climberMotor.configure(climberCfg, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

      SparkMaxConfig intakeCfg = new SparkMaxConfig();
      intakeCfg.apply(base);
      intakeCfg.inverted(false);
      intakeMotor.configure(intakeCfg, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  public void runShooter(double trigger) {

    double output = 0;

    if (trigger >= 0.05) {
      output = Math.ceil(trigger * 10.0) / 10.0;
      output = MathUtil.clamp(output, 0.5, 1.0);
    }

    shooterMotor.set(output);
  }

  public void runClimber(double speed) {
    climberMotor.set(speed);
  }

  public void runIntake(double speed) {
    intakeMotor.set(speed);
  }

// Use TWO consistent PWM ports
private final PWMSparkMax actuator1 = new PWMSparkMax(1);
private final PWMSparkMax actuator2 = new PWMSparkMax(0);
;

// Extend both
public void extendActuators() {
  actuator1.set(1.0);
}

public void extendActuators2() {
  actuator2.set(1.0);
}

// Retract both
public void retractActuators() {
  actuator1.set(-1.0);
}

public void retractActuators2() {
  actuator2.set(-1.0);
}

// Stop both (VERY IMPORTANT)
// public void stopActuators() {
//   actuator1.set(-0.8);
//   actuator2.set(-0.8);
// }
  private boolean intakeOn = false;

public void toggleIntake() {
    intakeOn = !intakeOn;
    runIntake(intakeOn ? 1 : 0);
}

}