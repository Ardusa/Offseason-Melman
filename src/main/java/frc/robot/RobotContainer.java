package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {
  private final XboxController xDrive = new XboxController(0);

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {}
}
