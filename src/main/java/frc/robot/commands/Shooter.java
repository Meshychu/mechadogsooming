package frc.robot.commands;

import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;


public class Shooter extends Command {
  private final ShooterSubsystem shooterMotor;
  private final Timer timer = new Timer();
  // private final double distance;
  // private double encoderSetpoint;

public Shooter(ShooterSubsystem shoot) {
  this.shooterMotor = shoot;
  // this.distance = distance;
  addRequirements(shoot);
}

@Override
public void initialize() {
  timer.reset();
  timer.start();
// encoderSetpoint = drive.getEncoderMeters() + distance;
}

@Override
public void execute() {
  shooterMotor.setShooterSpeed(0.7);
}

@Override
public void end(boolean interrupted) {
  shooterMotor.setShooterSpeed(0.0);
}

@Override
public boolean isFinished() {
  if (timer.get() > 5.0) {
    return true;
  } else
  return false;
}
}

// actually connect to the shooter