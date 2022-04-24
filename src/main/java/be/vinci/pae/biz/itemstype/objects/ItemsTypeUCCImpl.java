package be.vinci.pae.biz.itemstype.objects;

import be.vinci.pae.biz.itemstype.interfaces.ItemsTypeDTO;
import be.vinci.pae.biz.itemstype.interfaces.ItemsTypeUCC;
import be.vinci.pae.dal.itemstype.interfaces.ItemsTypeDAO;
import be.vinci.pae.dal.services.interfaces.DALServices;
import jakarta.inject.Inject;
import java.sql.SQLException;
import java.util.List;

public class ItemsTypeUCCImpl implements ItemsTypeUCC {

  @Inject
  private ItemsTypeDAO itemsTypeDAO;
  @Inject
  private DALServices dalServices;

  @Override
  public List<ItemsTypeDTO> getAll() throws SQLException {
    try {
      this.dalServices.start();
      List<ItemsTypeDTO> itemsTypeDTOList = this.itemsTypeDAO.getAll();
      this.dalServices.commit();
      return itemsTypeDTOList;
    } catch (SQLException e) {
      this.dalServices.rollback();
      throw e;
    }
  }

  @Override
  public boolean exists(ItemsTypeDTO itemsTypeDTO) throws SQLException {
    try {
      this.dalServices.start();
      boolean exists = this.itemsTypeDAO.exists(itemsTypeDTO);
      this.dalServices.commit();
      return exists;
    } catch (SQLException e) {
      this.dalServices.rollback();
      throw e;
    }
  }

  @Override
  public boolean addItemsType(ItemsTypeDTO itemsTypeDTO) throws SQLException {
    try {
      this.dalServices.start();
      boolean added = this.itemsTypeDAO.addItemsType(itemsTypeDTO);
      this.dalServices.commit();
      return added;
    } catch (SQLException e) {
      this.dalServices.rollback();
      throw e;
    }
  }
}