package metacom.sprut7.beans;

import metacom.sprut7.domain.GsgWebArea;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 01.08.16
 * Time: 14:39
 * Обновляемфй класс зависит от плательщика/площадки года и месяца
 */
public interface IUpdatableAreaSeazon {
        public void init(String linkRes, GsgWebArea item, Integer year);
        public void update(GsgWebArea item, Integer year);
}
