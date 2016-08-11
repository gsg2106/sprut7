package metacom.sprut7.beans;

import metacom.sprut7.domain.GsgWebArea;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 01.08.16
 * Time: 16:48
 * To change this template use File | Settings | File Templates.
 */
public interface IUpdatebleArea {
    public void init(String linkRes, GsgWebArea item);
    public void update(GsgWebArea item);
}
