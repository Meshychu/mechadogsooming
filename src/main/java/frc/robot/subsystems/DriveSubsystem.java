package frc.robot.subsystems;

import frc.robot.Constants;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
// import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.ResetMode;
import com.revrobotics.PersistMode;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {

  private final SparkMax leftLead =
      new SparkMax(Constants.DriveConstants.LEFT_LEAD, MotorType.kBrushed);

  private final SparkMax leftFollow =
      new SparkMax(Constants.DriveConstants.LEFT_FOLLOW, MotorType.kBrushed);

  private final SparkMax rightLead =
      new SparkMax(Constants.DriveConstants.RIGHT_LEAD, MotorType.kBrushed);

  private final SparkMax rightFollow =
      new SparkMax(Constants.DriveConstants.RIGHT_FOLLOW, MotorType.kBrushed);

  private final DifferentialDrive drive = new DifferentialDrive(leftLead, rightLead);

  private final SlewRateLimiter leftLimiter = new SlewRateLimiter(10.0);
  private final SlewRateLimiter rightLimiter = new SlewRateLimiter(10.0);

  // private final RelativeEncoder leftEncoder = leftLead.getEncoder();
  // private final RelativeEncoder rightEncoder = rightLead.getEncoder();
  // add gyro logic instead

  public DriveSubsystem() {

    SparkMaxConfig base = new SparkMaxConfig();
    base.voltageCompensation(12.0);
    base.smartCurrentLimit(60);

    SparkMaxConfig leftLeadCfg = new SparkMaxConfig();
    leftLeadCfg.apply(base);
    leftLeadCfg.inverted(false);
    leftLead.configure(leftLeadCfg, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    SparkMaxConfig rightLeadCfg = new SparkMaxConfig();
    rightLeadCfg.apply(base);
    rightLeadCfg.inverted(true);
    rightLead.configure(rightLeadCfg, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    SparkMaxConfig leftFollowCfg = new SparkMaxConfig();
    leftFollowCfg.apply(base);
    leftFollowCfg.follow(leftLead);
    leftFollow.configure(leftFollowCfg, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    SparkMaxConfig rightFollowCfg = new SparkMaxConfig();
    rightFollowCfg.apply(base);
    rightFollowCfg.follow(rightLead);
    rightFollow.configure(rightFollowCfg, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    drive.setDeadband(0.0);
  }

  // public void arcadeDrive(double fwd, double rot) {

  //   fwd = MathUtil.applyDeadband(fwd, Constants.DriveConstants.FWD_DEADBAND);
  //   rot = MathUtil.applyDeadband(rot, Constants.DriveConstants.ROT_DEADBAND);

  //   fwd = fwdLimiter.calculate(fwd);
  //   rot = rotLimiter.calculate(rot);

  //   drive.arcadeDrive(fwd, rot);
  // }

  public void tankDrive(double left, double right) {

    left = MathUtil.applyDeadband(left, 0.05);
    right = MathUtil.applyDeadband(right, 0.05);

    left = leftLimiter.calculate(left);
    right = rightLimiter.calculate(right);

    drive.tankDrive(left, right);
  }

  public void stop() {
    drive.stopMotor();
  }

}