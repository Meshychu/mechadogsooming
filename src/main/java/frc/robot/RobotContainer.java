package frc.robot;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.Autos;
import edu.wpi.first.wpilibj.PS4Controller;

public class RobotContainer {

  public RobotContainer() {
     autoChooser.setDefaultOption("Drive and Shoot", new Autos(drive, shooter));
  autoChooser.addOption("Do Nothing", null);

  SmartDashboard.putData("Auto Modes", autoChooser);
  }

  public Command getAutonomousCommand() {
  return autoChooser.getSelected();
}

  private final SendableChooser<Command> autoChooser = new SendableChooser<>();
  private final DriveSubsystem drive = new DriveSubsystem();
  private final ShooterSubsystem shooter = new ShooterSubsystem();
  NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");




  public DriveSubsystem getDrive() {
    return drive;
  }

  private boolean cintake = false;
  private boolean pcIntake = false;

  private boolean crintake = false;
  private boolean pcrIntake = false;

  private final PS4Controller controller =
      new PS4Controller(Constants.ControllerConstants.DRIVER_PORT);

  public void teleopPeriodic() {

    // drive.arcadeDrive(
    //     controller.getLeftY(),
    //     controller.getRightX()
    // );
table.getEntry("pipeline").setNumber(0); // your AprilTag pipeline
table.getEntry("ledMode").setNumber(3);  // force LEDs on
table.getEntry("camMode").setNumber(0);  // vision mode


double tx = table.getEntry("tx").getDouble(0.0);
  double ty = table.getEntry("ty").getDouble(0.0);
  double ta = table.getEntry("ta").getDouble(0.0);
  double tv = table.getEntry("tv").getDouble(0.0);

  SmartDashboard.putNumber("Limelight X", tx);
  SmartDashboard.putNumber("Limelight Y", ty);
  SmartDashboard.putNumber("Limelight Area", ta);
  SmartDashboard.putNumber("Limelight Detection", tv);
  SmartDashboard.putNumber("LeftY", controller.getLeftY());
  SmartDashboard.putNumber("RightY", controller.getRightY());
  
  
  shooter.runShooter(controller.getR2Axis());

  drive.tankDrive(
    controller.getLeftY(),
    controller.getRightY()
  );

  if (controller.getL1Button()) {
    shooter.runClimber(0.5);
  } else if (controller.getR1Button()) {
    shooter.runClimber(-0.5);
  } else {
    shooter.runClimber(0.0);
  }

  boolean tCircleIntake = controller.getCircleButton();

  if (tCircleIntake && !pcIntake) {
    cintake = !cintake;
  }

  pcIntake = tCircleIntake;

  boolean tCrossIntake = controller.getCrossButton();

  if (tCrossIntake && !pcrIntake) {
    crintake = !crintake;
  }

  pcrIntake = tCrossIntake;

  if (cintake == true) {
    shooter.runIntake(1);
  } else if (cintake == false) {
    shooter.runIntake(0.0);
  } 

  if (crintake == true) {
    shooter.runIntake(-1);
  } 

if (controller.getTriangleButton()) {
  shooter.extendActuators();
} else if (controller.getSquareButton()) {
  shooter.retractActuators();
} 

if (controller.getShareButton()) {
  shooter.extendActuators2();
} else if (controller.getOptionsButton()) {
  shooter.retractActuators2();
}

  }
}