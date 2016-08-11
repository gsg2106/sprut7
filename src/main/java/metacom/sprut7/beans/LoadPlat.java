/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7.beans;

import java.util.Date;

/**
 *
 * @author Сергей
 */
public interface LoadPlat{
  public void init(String login, Boolean goest, String tested, String linkRes );
  public Boolean PlatIsFound();
  public Integer getIdplat();
  public String getNameplat();
  public String getAdres();
  public String getState();
  public Integer getYearRaport();
  public Integer getMonthRaport();
  public String getNumDog();
  public String getLogin();
  public String getPassword();
  public Integer getPause();
  public Integer getRaportEnabled();
  public Integer getYea();
  public Integer getMon();
  public Date getDtLock();
  public String getMess();
  public String getPuthNsSchets();
}
