package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.cameraserver.CameraServer;

public class Robot extends TimedRobot {

  private RobotContainer robotContainer;
  private Command autonomousCommand;

  @Override
  public void robotInit() {
    robotContainer = new RobotContainer();
    CameraServer.startAutomaticCapture(0);
    CameraServer.startAutomaticCapture(1);
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    autonomousCommand = robotContainer.getAutonomousCommand();

    if (autonomousCommand != null) {
      autonomousCommand.schedule();
    }
  }

  @Override
  public void teleopInit() {
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    robotContainer.teleopPeriodic();
  }
}