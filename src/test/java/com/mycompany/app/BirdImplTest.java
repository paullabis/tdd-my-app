package com.mycompany.app;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author paul.labis.
 */
public class BirdImplTest {

  @Test
  public final void testFly_FlySuccessful() {
    final Parrot parrot = new Bird();

    final String output = parrot.fly();

    assertEquals(output, "fly...", "Expect correct out string.");
  }

}
