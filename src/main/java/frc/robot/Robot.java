package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.TimedRobot;
// import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.cameraserver.CameraServer;


public class Robot extends TimedRobot {

  // private Timer autoTimer = new Timer();
  private RobotContainer robotContainer;
  @Override
  public void robotInit() {
    CommandScheduler.getInstance().run();
    robotContainer = new RobotContainer();
    CameraServer.startAutomaticCapture(0);
    CameraServer.startAutomaticCapture(1);
  }

  @Override
  public void teleopInit() {
autonomousCommand = robotContainer.getAutonomousCommand();

    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic() {
    robotContainer.teleopPeriodic();
    }

   

  private Command autonomousCommand; {
 
  if (autonomousCommand != null) {
  autonomousCommand.cancel();
}
}

  // @Override
  // public void autonomousInit() {
  //   autonomousCommand = robotContainer.getAutonomousCommand();

  //   if (autonomousCommand != null) {
  //     autonomousCommand.schedule();
  //   }
  // }

}