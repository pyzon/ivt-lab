package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockPrimaryTorpedoStore;
  private TorpedoStore mockSecondaryTorpedoStore;

  @BeforeEach
  public void init(){
    mockPrimaryTorpedoStore = mock(TorpedoStore.class);
    mockSecondaryTorpedoStore = mock(TorpedoStore.class);
    ship = new GT4500(mockPrimaryTorpedoStore, mockSecondaryTorpedoStore);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockPrimaryTorpedoStore.getTorpedoCount()).thenReturn(10);
    when(mockSecondaryTorpedoStore.getTorpedoCount()).thenReturn(10);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(mockPrimaryTorpedoStore, times(1)).isEmpty();
    verify(mockPrimaryTorpedoStore, times(1)).fire(1);
    verify(mockSecondaryTorpedoStore, times(0)).isEmpty();
    verify(mockSecondaryTorpedoStore, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockPrimaryTorpedoStore.getTorpedoCount()).thenReturn(10);
    when(mockSecondaryTorpedoStore.getTorpedoCount()).thenReturn(10);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(mockPrimaryTorpedoStore, times(1)).isEmpty();
    verify(mockPrimaryTorpedoStore, times(1)).fire(1);
    verify(mockSecondaryTorpedoStore, times(1)).isEmpty();
    verify(mockSecondaryTorpedoStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Once_PrimaryEmpty_Success() {
    // Arrange
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(true);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockPrimaryTorpedoStore.getTorpedoCount()).thenReturn(0);
    when(mockSecondaryTorpedoStore.getTorpedoCount()).thenReturn(10);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(mockPrimaryTorpedoStore, times(1)).isEmpty();
    verify(mockPrimaryTorpedoStore, times(0)).fire(1);
    verify(mockSecondaryTorpedoStore, times(1)).isEmpty();
    verify(mockSecondaryTorpedoStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Once_BothFull_BothFailure() {
    // Arrange
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(false);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(false);
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockPrimaryTorpedoStore.getTorpedoCount()).thenReturn(10);
    when(mockSecondaryTorpedoStore.getTorpedoCount()).thenReturn(10);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(mockPrimaryTorpedoStore, times(1)).isEmpty();
    verify(mockPrimaryTorpedoStore, times(1)).fire(1);
    verify(mockSecondaryTorpedoStore, times(0)).isEmpty();
    verify(mockSecondaryTorpedoStore, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Twice_BothFull_Success() {
    // Arrange
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockPrimaryTorpedoStore.getTorpedoCount()).thenReturn(10);
    when(mockSecondaryTorpedoStore.getTorpedoCount()).thenReturn(10);

    // Act
    boolean resultFirst = ship.fireTorpedo(FiringMode.SINGLE);
    boolean resultSecond = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, resultFirst);
    assertEquals(true, resultSecond);
    verify(mockPrimaryTorpedoStore, times(1)).isEmpty();
    verify(mockPrimaryTorpedoStore, times(1)).fire(1);
    verify(mockSecondaryTorpedoStore, times(1)).isEmpty();
    verify(mockSecondaryTorpedoStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Twice_SecondaryEmpty_Success() {
    // Arrange
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(true);
    when(mockPrimaryTorpedoStore.getTorpedoCount()).thenReturn(10);
    when(mockSecondaryTorpedoStore.getTorpedoCount()).thenReturn(0);

    // Act
    boolean resultFirst = ship.fireTorpedo(FiringMode.SINGLE);
    boolean resultSecond = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, resultFirst);
    assertEquals(true, resultSecond);
    verify(mockPrimaryTorpedoStore, times(2)).isEmpty();
    verify(mockPrimaryTorpedoStore, times(2)).fire(1);
    verify(mockSecondaryTorpedoStore, times(1)).isEmpty();
    verify(mockSecondaryTorpedoStore, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_PrimaryEmpty_Success() {
    // Arrange
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(true);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockPrimaryTorpedoStore.getTorpedoCount()).thenReturn(0);
    when(mockSecondaryTorpedoStore.getTorpedoCount()).thenReturn(10);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(mockPrimaryTorpedoStore, times(1)).isEmpty();
    verify(mockPrimaryTorpedoStore, times(0)).fire(1);
    verify(mockSecondaryTorpedoStore, times(1)).isEmpty();
    verify(mockSecondaryTorpedoStore, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_BothFull_BothFailure() {
    // Arrange
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(false);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(false);
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockPrimaryTorpedoStore.getTorpedoCount()).thenReturn(10);
    when(mockSecondaryTorpedoStore.getTorpedoCount()).thenReturn(10);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(mockPrimaryTorpedoStore, times(1)).isEmpty();
    verify(mockPrimaryTorpedoStore, times(1)).fire(1);
    verify(mockSecondaryTorpedoStore, times(1)).isEmpty();
    verify(mockSecondaryTorpedoStore, times(1)).fire(1);
  }
  
  @Test
  public void fireTorpedo_Single_Once_BothEmpty() {
    // Arrange
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(true);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(true);
    when(mockPrimaryTorpedoStore.getTorpedoCount()).thenReturn(0);
    when(mockSecondaryTorpedoStore.getTorpedoCount()).thenReturn(0);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(mockPrimaryTorpedoStore, times(1)).isEmpty();
    verify(mockPrimaryTorpedoStore, times(0)).fire(1);
    verify(mockSecondaryTorpedoStore, times(1)).isEmpty();
    verify(mockSecondaryTorpedoStore, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Twice_FirstOneSecondEmpty() {
    // Arrange
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(true);
    when(mockPrimaryTorpedoStore.getTorpedoCount()).thenReturn(1);
    when(mockSecondaryTorpedoStore.getTorpedoCount()).thenReturn(0);

    // Act
    boolean resultFirst = ship.fireTorpedo(FiringMode.SINGLE);
    
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(true);
    when(mockPrimaryTorpedoStore.getTorpedoCount()).thenReturn(0);

    boolean resultSecond = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, resultFirst);
    assertEquals(false, resultSecond);
    verify(mockPrimaryTorpedoStore, times(2)).isEmpty();
    verify(mockPrimaryTorpedoStore, times(1)).fire(1);
    verify(mockSecondaryTorpedoStore, times(1)).isEmpty();
    verify(mockSecondaryTorpedoStore, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_SecondEmpty() {
    // Arrange
    when(mockPrimaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockSecondaryTorpedoStore.fire(1)).thenReturn(true);
    when(mockPrimaryTorpedoStore.isEmpty()).thenReturn(false);
    when(mockSecondaryTorpedoStore.isEmpty()).thenReturn(true);
    when(mockPrimaryTorpedoStore.getTorpedoCount()).thenReturn(10);
    when(mockSecondaryTorpedoStore.getTorpedoCount()).thenReturn(0);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(mockPrimaryTorpedoStore, times(1)).isEmpty();
    verify(mockPrimaryTorpedoStore, times(1)).fire(1);
    verify(mockSecondaryTorpedoStore, times(1)).isEmpty();
    verify(mockSecondaryTorpedoStore, times(0)).fire(1);
  }

}
