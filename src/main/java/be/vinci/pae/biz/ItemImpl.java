package be.vinci.pae.biz;

import java.util.Objects;

public class ItemImpl implements Item {

  private int id;
  private String item_description;
  private int id_item_type;
  private int id_member;
  private String photo;
  private String title;
  private String offers_status;

  public ItemImpl() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getItem_description() {
    return item_description;
  }

  public void setItem_description(String item_description) {
    this.item_description = item_description;
  }

  public int getId_item_type() {
    return id_item_type;
  }

  public void setId_item_type(int id_item_type) {
    this.id_item_type = id_item_type;
  }

  public int getId_member() {
    return id_member;
  }

  public void setId_member(int id_member) {
    this.id_member = id_member;
  }

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getOffers_status() {
    return offers_status;
  }

  public void setOffers_status(String offers_status) {
    this.offers_status = offers_status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ItemImpl item = (ItemImpl) o;
    return id == item.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Item{"
        + "id=" + id
        + ", item_description='" + item_description + '\''
        + ", id_item_type='" + id_item_type + '\''
        + ", id_member='" + id_member + '\''
        + ", photo='" + photo + '\''
        + ", title=" + title
        + ", offer_status='" + offers_status + '\''
        + '}';
  }


}
