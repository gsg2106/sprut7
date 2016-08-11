package metacom.sprut7.beans;

import com.vaadin.ui.*;
import metacom.sprut7.domain.GsgWebArea;
import org.springframework.context.annotation.Scope;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 09.08.16
 * Time: 14:05
 * To change this template use File | Settings | File Templates.
 */
@Scope("prototype")
public class TabSheetMainLayout extends VerticalLayout{
    private TableEditParent tableEditParent;
    private HorizontalLayout toolbar;
    public void init(final TableEditParent tableEditParent){
        this.tableEditParent = tableEditParent;
        final TableEditParent finalTableEditParent = tableEditParent;
        toolbar = new HorizontalLayout();
        toolbar.setMargin(true);
        Button calcButton = new Button("Подготовить", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                tableEditParent.prepareTable();
            }
        });
        Button editButton = new Button("Редактировать",new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                finalTableEditParent.edit();
            }
        });
        Button clearButton = new Button("Очистить", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                tableEditParent.clearTable();
            }
        });
        Button sinchronizeButton = new Button("Согласовать", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                tableEditParent.sinchrinizeTable();
            }
        });
        toolbar.addComponent(calcButton);
        toolbar.addComponent(editButton);
        toolbar.addComponent(clearButton);
        toolbar.addComponent(sinchronizeButton);
        addComponent(toolbar);
        addComponent(tableEditParent);

    }
}
