package be.vinci.pae.biz.itemstype.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.vinci.pae.biz.itemstype.interfaces.ItemsTypeDTO;
import be.vinci.pae.biz.itemstype.interfaces.ItemsTypeUCC;
import be.vinci.pae.dal.itemstype.interfaces.ItemsTypeDAO;
import be.vinci.pae.dal.services.interfaces.DALServices;
import be.vinci.pae.exceptions.FatalException;
import be.vinci.pae.utils.ApplicationBinder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ItemsTypeUCCImplTest {

  private final ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinder());

  private final ItemsTypeDAO itemsTypeDAO = locator.getService(ItemsTypeDAO.class);

  private final DALServices dalServices = locator.getService(DALServices.class);

  private final ItemsTypeUCC itemsTypeUCC = locator.getService(ItemsTypeUCC.class);

  private final List<ItemsTypeDTO> itemsTypeDTOLists = new ArrayList<>();

  @BeforeEach
  void setUp() {
    try {
      Mockito.doNothing().when(this.dalServices).start();
      Mockito.doNothing().when(this.dalServices).commit();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private void setGetAllReturnedValue() {
    Mockito.when(this.itemsTypeDAO.getAll()).thenReturn(this.itemsTypeDTOLists);
  }

  private void setErrorDALServiceStart() {
    try {
      Mockito.doThrow(new SQLException()).when(dalServices).start();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  private void setErrorDALServiceCommit() {
    try {
      Mockito.doThrow(new SQLException()).when(dalServices).commit();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @DisplayName("Test get all items types working as expected")
  @Test
  void testGetAllWorkingAsExpected() {
    this.setGetAllReturnedValue();
    assertEquals(this.itemsTypeDTOLists, this.itemsTypeUCC.getAll());
  }

  @DisplayName("Test get all items types with start throwing sql exception")
  @Test
  void testGetAll() {
    this.setErrorDALServiceStart();
    assertThrows(FatalException.class, this.itemsTypeUCC::getAll);
  }

  @DisplayName("Test get all items types with commit throwing sql exception")
  @Test
  void getAll() {
    this.setErrorDALServiceCommit();
    assertThrows(FatalException.class, this.itemsTypeUCC::getAll);
  }

  @Test
  void exists() {
  }

  @Test
  void addItemsType() {
  }
}