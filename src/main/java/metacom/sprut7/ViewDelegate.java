/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package metacom.sprut7;

import com.vaadin.data.Container;
import com.vaadin.data.util.sqlcontainer.RowItem;
import com.vaadin.data.util.sqlcontainer.SQLUtil;
import com.vaadin.data.util.sqlcontainer.TemporaryRowId;
import com.vaadin.data.util.sqlcontainer.query.FreeformStatementDelegate;
import com.vaadin.data.util.sqlcontainer.query.OrderBy;
import com.vaadin.data.util.sqlcontainer.query.generator.StatementHelper;
import com.vaadin.data.util.sqlcontainer.query.generator.filter.QueryBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Сергей
 */
public class ViewDelegate  implements FreeformStatementDelegate{
    private List<Container.Filter> filters;
    private List<OrderBy> orderBys;
    private String nameView; // = "GSG_WEB_VIEW_RA_HIST";
//    private List<Updater> updaters = null;
    private Updater updater = null;
    class Updater{
        private String tableName;
        private String idFieldName;
        private List<String> fields_;

        public Updater(String tableName,String idFieldName,List<String> fields_){
            this.tableName = tableName;
            this.idFieldName = idFieldName;
            this.fields_ = fields_;
        }
        public String getTableName(){
            return this.tableName;
        }
        public String getIdFieldName(){
            return this.idFieldName;
        }
        public List<String> getFields(){
            return this.fields_;
        }

    }

    public ViewDelegate(String nameView_){
        nameView = nameView_;
    }

    public void addDataUpdater(String tableName, String idFieldName,List<String> fields_){
        //updaters.add(new Updater(tableName,idFieldName,fields_));
        this.updater = new Updater(tableName,idFieldName,fields_);
    }


    @Override
    public StatementHelper getQueryStatement(int offset, int limit) throws UnsupportedOperationException {
        StatementHelper sh = new StatementHelper();
	StringBuffer query = new StringBuffer("SELECT ");
        if (offset != 0 || limit != 0) {
	    query.append(" FIRST ").append(limit);
	    query.append(" SKIP ").append(offset);
        }
	query.append(" * FROM ".concat(nameView));
	if (filters != null) {
	     query.append(QueryBuilder.getWhereStringForFilters(
	             filters, sh));
	}

	query.append(getOrderByString());
	sh.setQueryString(query.toString());
	return sh;
	        
        
    }

    @Override
    public StatementHelper getCountStatement() throws UnsupportedOperationException {
        StatementHelper sh = new StatementHelper();
        StringBuffer query = new StringBuffer("SELECT COUNT(*) FROM ".concat(nameView));
        if (filters != null) {
            query.append(QueryBuilder.getWhereStringForFilters(
                    filters, sh));
        }
        sh.setQueryString(query.toString());
        return sh;
    }

    @Override
    public StatementHelper getContainsRowQueryStatement(Object... keys) throws UnsupportedOperationException {
        StatementHelper sh = new StatementHelper();
        StringBuffer query = new StringBuffer(
                "SELECT * FROM ".concat(nameView).concat("WHERE ID = ?"));
        sh.addParameterValue(keys[0]);
        sh.setQueryString(query.toString());
        return sh;
    }

    @Override
    public String getQueryString(int offset, int limit) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCountQuery() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setFilters(List<Container.Filter> filters) throws UnsupportedOperationException {
        this.filters = filters;
    }

    @Override
    public void setOrderBy(List<OrderBy> orderBys) throws UnsupportedOperationException {
       this.orderBys = orderBys;
    }

    @Override
    public int storeRow(Connection conn, RowItem row) throws UnsupportedOperationException, SQLException {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Statement statement = conn.createStatement();

        String query = null;
        if (row.getId() instanceof TemporaryRowId) {
            query = getInsertQuery(row);
        } else {
            query = getUpdateQuery(row);
        }

        int retval = statement.executeUpdate(query);
        statement.close();
        return retval;        
    }

    private String getUpdateQuery(RowItem row) {
        String tableName = updater.tableName;
        String idFieldName = updater.getIdFieldName();
        List<String> fields = updater.fields_;
        StringBuffer update = new StringBuffer("UPDATE " + tableName + " SET ");
        String lastField = fields.get(fields.size() - 1);
        for (String field : fields) {
            appendUpdateValue(update, row, field);
            if (field != lastField) {
                update.append(", ");
            }
        }
        update.append(" WHERE " + idFieldName + " = ").append(
                row.getItemProperty(idFieldName));
        return update.toString();
    }

    private void appendUpdateValue(StringBuffer update, RowItem row,
                                   String propId) {
        update.append(propId).append(" = ");
        Object val = row.getItemProperty(propId).getValue();
        if (val != null) {
            if (val instanceof String) {
                update.append("'").append(val).append("'");
            } else if (val instanceof Date) {
                SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                update.append("TO_DATE('").append(df.format(val))
                        .append("', 'dd.mm.yyyy')");
            } else {
                update.append(val);
            }
        } else {
            update.append("NULL");
        }
    }


    private String getInsertQuery(RowItem row) {
        String tableName = updater.tableName;
        String idFieldName = updater.getIdFieldName();
        List<String> fields = updater.fields_;
        StringBuffer insert = new StringBuffer("INSERT INTO " + tableName + "(");
        String lastField = fields.get(fields.size() - 1);
        for (String field : fields) {
            insert.append(field);
            if (field != lastField) {
                insert.append(", ");
            }
        }
        insert.append(") VALUES (");
        for (String field : fields) {
            appendInsertValue(insert, row, field);
            if (field != lastField) {
                insert.append(", ");
            }
        }
        insert.append(")");
        return insert.toString();
    }


    private void appendInsertValue(StringBuffer insert, RowItem row,
                                   String propId) {
        Object val = row.getItemProperty(propId).getValue();
        if (val != null) {
            if (val instanceof String) {
                insert.append("'").append(val).append("'");
            } else if (val instanceof Date) {
                SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                insert.append("TO_DATE('").append(df.format(val))
                        .append("', 'dd.mm.yyyy')");
            } else {
                insert.append(val);
            }
        } else {
            insert.append("NULL");
        }
    }




    @Override
    public boolean removeRow(Connection conn, RowItem row)
        throws UnsupportedOperationException, SQLException {
            PreparedStatement statement = conn
                    .prepareStatement("DELETE FROM people WHERE ID = ?");
            statement.setInt(1, (Integer) row.getItemProperty("ID").getValue());
            int rowsChanged = statement.executeUpdate();
            statement.close();
            return rowsChanged == 1;
    }

    @Override
    public String getContainsRowQueryString(Object... keys) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /*------------------------------------------------------------------ */
    private String getOrderByString() {
        StringBuffer orderBuffer = new StringBuffer("");
        if (orderBys != null && !orderBys.isEmpty()) {
            orderBuffer.append(" ORDER BY ");
            OrderBy lastOrderBy = orderBys.get(orderBys.size() - 1);
            for (OrderBy orderBy : orderBys) {
                orderBuffer.append(SQLUtil.escapeSQL(orderBy.getColumn()));
                if (orderBy.isAscending()) {
                    orderBuffer.append(" ASC");
                } else {
                    orderBuffer.append(" DESC");
                }
                if (orderBy != lastOrderBy) {
                    orderBuffer.append(", ");
                }
            }
        }
        return orderBuffer.toString();
    }    
}
