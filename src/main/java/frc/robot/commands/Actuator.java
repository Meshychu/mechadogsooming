package frc.robot.commands;

import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;

public class Actuator extends Command {
  private final ShooterSubsystem actuator;
  private final Timer timer = new Timer();
  // private final double distance;
  // private double encoderSetpoint;

public Actuator(ShooterSubsystem actuator, double distance) {
  this.actuator = actuator;
  // this.distance = distance;
  addRequirements(actuator);
}

@Override
public void initialize() {
  timer.reset();
  timer.start();
// encoderSetpoint = drive.getEncoderMeters() + distance;
}

@Override
public void execute() {
  if (timer.get() < 1.0) {
  actuator.extendActuators2();
  } 
}

@Override
public void end(boolean interrupted) {
  actuator.retractActuators2();
}

@Override
public boolean isFinished() {
  // if (drive.getEncoderMeters() >= encoderSetpoint) {
  //   return true;
  // else
  return timer.get() > 10.0;
}
}