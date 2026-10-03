/*
 * Copyright 2026 FRCSoftware
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package first.robot.opmode;

import first.robot.Robot;
import org.wpilib.command3.Command;
import org.wpilib.command3.button.CommandXboxController;
import org.wpilib.opmode.PeriodicOpMode;
import org.wpilib.opmode.Teleop;

@Teleop
public class MyTeleop extends PeriodicOpMode {
  private final Robot robot;
  private final CommandXboxController driveController = new CommandXboxController(0);

  /** The Robot instance is passed into the opmode via the constructor. */
  public MyTeleop(Robot robot) {
    this.robot = robot;

    configureController();
  }

  @Override
  public void periodic() {
    /* Called periodically (set time interval) while the robot is enabled. */
    robot.drivetrain.arcadeDrive(-driveController.getLeftY(), driveController.getRightX());
  }

  private void configureController() {
    driveController
        .leftBumper()
        .whileTrue(
            Command.noRequirements(
                    coroutine -> {
                      robot.intakeLauncher.setThrottle(0.8);
                      robot.feeder.setThrottle(-1.0);
                      System.out.println("LeftBumper");
                    })
                .named("Left Bumper On"))
        .whileFalse(
            Command.noRequirements(
                    coroutine -> {
                      robot.intakeLauncher.setThrottle(0.0);
                      robot.feeder.setThrottle(0.0);
                      System.out.println("LeftBumperOff");
                    })
                .named("Left Bumper Off"));

    driveController
        .rightBumper()
        .whileTrue(
            Command.noRequirements(
                    coroutine -> {
                      robot.intakeLauncher.setThrottle(0.9);
                      robot.feeder.setThrottle(0.75);
                      System.out.println("RightBumper");
                    })
                .named("Right Bumper On"))
        .whileFalse(
            Command.noRequirements(
                    coroutine -> {
                      robot.intakeLauncher.setThrottle(0.0);
                      robot.feeder.setThrottle(0.0);
                    })
                .named("Left Bumper Off"));

    driveController
        .a()
        .whileTrue(
            Command.noRequirements(
                    coroutine -> {
                      robot.intakeLauncher.setThrottle(-0.8);
                      robot.feeder.setThrottle(1.0);
                    })
                .named("A button"))
        .whileFalse(
            Command.noRequirements(
                    coroutine -> {
                      robot.intakeLauncher.setThrottle(0.0);
                      robot.feeder.setThrottle(0.0);
                    })
                .named("A Button Off"));
  }
}
