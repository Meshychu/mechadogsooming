package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class Autos extends SequentialCommandGroup {

  public Autos(DriveSubsystem drive, ShooterSubsystem shooter) {

    addCommands(

      new DriveFwdCmd(drive),
      new WaitCommand(0.5),

      // Step 1: retract actuators
      new InstantCommand(() -> shooter.retractActuators2(), shooter),

      // Step 2: run shooter + timed actuator extension
      new ParallelCommandGroup(

        // Shooter spins
        new Shooter(shooter).withTimeout(2.5),

        // Delay then extend actuator
        new SequentialCommandGroup(
          new WaitCommand(0.5),
          new InstantCommand(() -> shooter.extendActuators2(), shooter)
        )
      ),

      // Step 3: allow shot to complete
      new WaitCommand(1.0),

      // Step 4: stop everything
      new InstantCommand(() -> {
        shooter.setShooterSpeed(0.0);
        shooter.retractActuators2();
      }, shooter)

    );
  }
}